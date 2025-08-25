package jp.trial.grow_up.controller.auth;

import jp.trial.grow_up.domain.client.User;
import jp.trial.grow_up.dto.auth.LoginRequestDTO;
import jp.trial.grow_up.dto.auth.SignupRequestDTO;
import jp.trial.grow_up.dto.auth.SignupResponseDTO;
import jp.trial.grow_up.service.client.UserService;
import jp.trial.grow_up.config.JwtUtil;
import jp.trial.grow_up.util.ApiResponse;
import jp.trial.grow_up.util.convert.UserConvert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    //di dc
   private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //ユーザー新規登録

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SignupResponseDTO>> registerUser(@RequestBody SignupRequestDTO signupRequestDTO) {

        ApiResponse<SignupResponseDTO> res = new ApiResponse<>();

        if (!this.userService.userExists(signupRequestDTO.getEmail())) {
            User convertedUser = UserConvert.convertFromSignupRequestDTO(signupRequestDTO);

            // パスワードのハッシュ化
            convertedUser.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));

            User savedUser = this.userService.handleSaveUser(convertedUser);

            String token = jwtUtil.generateToken(savedUser);
            SignupResponseDTO rsData = UserConvert.convertToSignupUserDTO(savedUser);
            rsData.setToken(token);
            rsData.setRole(savedUser.getRole().name());

            res.setStatus("success");
            res.setMessage("ユーザー登録が完了しました");
            res.setData(rsData);

            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }

        res.setStatus("error");
        res.setMessage("このメールアドレスは既に登録されています");
        res.setData(null);

        return ResponseEntity.badRequest().body(res);
    }



    // ログインロジック
    @PostMapping("/login")
    public  ResponseEntity<ApiResponse<SignupResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO){
            ApiResponse<SignupResponseDTO> res = new ApiResponse<>();
            User existingUser = this.userService.getUserByEmail(loginRequestDTO.getEmail());

            if(existingUser == null || !passwordEncoder.matches(loginRequestDTO.getPassword(),existingUser.getPassword())){
                res.setStatus("error");
                res.setMessage("メールアドレスまたはパスワードが間違っています");
                res.setData(null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
            }
            //認証済　→ jwt発行
            String token = jwtUtil.generateToken(existingUser);
            SignupResponseDTO userData =  UserConvert.convertToSignupUserDTO(existingUser);
            userData.setToken(token);
            userData.setRole(existingUser.getRole().name());
            res.setStatus("success");
            res.setMessage("ログイン成功");
            res.setData(userData);

            return ResponseEntity.ok(res);
    }

    //test jwt

//    @GetMapping("/test")
//    public ResponseEntity<?> getProfile(Authentication authentication) {
//        String email = authentication.getName();
//        // email でユーザー情報を返す
//        return  ResponseEntity.ok(this.userService.getUserByEmail(email));
//    }
}
