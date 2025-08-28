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

    //get sum users
    @GetMapping("/sum/users")
    public long getSumOfUsers(){
        return userService.getSumOfUsers();
    }

    //get sum workshops
    @GetMapping("/sum/workshops")
    public long getSumOfWorks(){
        return workshopService.getSumOfWorks();
    }

    //get sum skills
    @GetMapping("/sum/skills")
    public long getSumOfSkills(){
        return skillService.getSumOfSkills();
    }
    //create user

    //全てのユーザー一覧を取得
//    @GetMapping("/users")
//    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
//
//    }

    //get a user

    //update user

    //delete user

    //get all workshops

    //get a workshop

    //update a workshop

    //delete a workshop

    //get all skills

    //get a skill

    //update a skill

    //delete a skill






}
