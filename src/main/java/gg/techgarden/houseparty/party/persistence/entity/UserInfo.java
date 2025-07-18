package gg.techgarden.houseparty.party.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserInfo {
    @Id
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private List<String> roles;
}
