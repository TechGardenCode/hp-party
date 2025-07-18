package gg.techgarden.houseparty.party.controller;


import gg.techgarden.houseparty.party.model.common.UserInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/profile")
    public UserInfoDto getProfile(JwtAuthenticationToken auth) {
        return UserInfoDto.builder()
            .id(auth.getToken().getClaimAsString(StandardClaimNames.SUB))
            .username(auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
            .firstName(auth.getToken().getClaimAsString(StandardClaimNames.GIVEN_NAME))
            .lastName(auth.getToken().getClaimAsString(StandardClaimNames.FAMILY_NAME))
            .name(auth.getToken().getClaimAsString(StandardClaimNames.NAME))
            .email(auth.getToken().getClaimAsString(StandardClaimNames.EMAIL))
            .roles(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .build();
    }
}
