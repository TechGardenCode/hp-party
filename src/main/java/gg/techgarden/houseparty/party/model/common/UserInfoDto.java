package gg.techgarden.houseparty.party.model.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private List<String> roles;
}
