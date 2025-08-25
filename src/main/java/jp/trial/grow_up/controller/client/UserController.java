package jp.trial.grow_up.controller.client;

import jp.trial.grow_up.domain.client.User;
import jp.trial.grow_up.dto.auth.SignupResponseDTO;
import jp.trial.grow_up.dto.client.ResponseUserProfileDTO;
import jp.trial.grow_up.service.client.UserService;
import jp.trial.grow_up.util.ApiResponse;
import jp.trial.grow_up.util.convert.UserConvert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //自分の情報を習得(jwtからemailを取得→ 情報取得)
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ResponseUserProfileDTO>> getMyProfile(Authentication authentication) {
        ApiResponse<ResponseUserProfileDTO> res = new ApiResponse<ResponseUserProfileDTO>();
        String email = authentication.getName();
        //
        User currentUser = this.userService.getUserByEmail(email);
        if(currentUser == null){
            // ユーザーが見つからなかった場合の処理 (念のため)
            res.setStatus("error");
            res.setMessage("ユーザーが見つかりませんでした");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
        ResponseUserProfileDTO resData = UserConvert.convertToResponseUserProfileDTO(currentUser);
        res.setData(resData);
        res.setStatus("success");
        res.setMessage("プロフィールの取得に成功しました");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ApiResponse<List<ResponseUserProfileDTO>>> handleGetRecommendedUsers(){
        ApiResponse<List<ResponseUserProfileDTO>> res = new ApiResponse<>();
        List<ResponseUserProfileDTO> userData = this.userService.getRecommendedUsers();
        if(userData.size() > 0){
            res.setStatus("success");
            res.setMessage("オススメユーザー一覧を取得しました");
            res.setData(userData);
            return ResponseEntity.ok(res);
        }
        res.setStatus("error");
        res.setMessage("オススメユーザー一覧の取得に失敗しました");
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
    }

    //自分の情報を更新する　Put /api/users/me

    //自分のアバターを更新　Post /api/users/me/avatar

    //自分のバックグラウンド画像を更新　Post /api/users/me/bgImage

    //自分のスキル更新　Post /api/users/me/skills

    //自分のスキル削除　Delete /api/users/me/skills





    //他のユーザーの公開プロフィール情報を取得する
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<ResponseUserProfileDTO>> getOtherUserProfile(Authentication authentication,@PathVariable UUID userId) {
        ApiResponse<ResponseUserProfileDTO> res = new ApiResponse<>();
        Optional<User> currentUser = this.userService.getUserById(userId);
        if(currentUser.isEmpty()){
            res.setStatus("error");
            res.setMessage("ユーザーが見つかりませんでした");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        ResponseUserProfileDTO resData = UserConvert.convertToResponseUserProfileDTO(currentUser.get());
        res.setStatus("success");
        res.setMessage("プロフィールの取得に成功しました");
        res.setData(resData);

        return ResponseEntity.ok(res);

    }

    //フォローする　Post /api/users/{userId}/follow

    //フォロー削除　Delete /api/users/{userId}/follow

    //フォロワー一覧取得　Get /api/users/{userId}/followers

    //フォロー中一覧取得　Get /api/users/{userId}/following

}