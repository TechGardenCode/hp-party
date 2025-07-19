package gg.techgarden.houseparty.party.controller;


import gg.techgarden.houseparty.party.model.common.UserInfoDto;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public UserInfo getProfile() {
        return userService.getProfile();
    }
}
