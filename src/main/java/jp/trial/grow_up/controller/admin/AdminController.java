package jp.trial.grow_up.controller.admin;

import jp.trial.grow_up.domain.client.User;
import jp.trial.grow_up.domain.client.Workshop;
import jp.trial.grow_up.service.client.SkillService;
import jp.trial.grow_up.service.client.UserService;
import jp.trial.grow_up.service.client.WorkshopService;
import jp.trial.grow_up.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final SkillService skillService;
    private final UserService userService;
    private final WorkshopService workshopService;

    public AdminController(SkillService skillService, UserService userService, WorkshopService workshopService) {
        this.skillService = skillService;
        this.userService = userService;
        this.workshopService = workshopService;
    }

    //全てのユーザー一覧を取得
//    @GetMapping("/users")
//    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
//
//    }
}
