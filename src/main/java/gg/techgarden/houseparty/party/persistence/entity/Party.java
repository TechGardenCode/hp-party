package gg.techgarden.houseparty.party.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Party {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    private LocalDateTime startDateTime;
    private String imageUrl;
    private String hostNickname;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserInfo createdBy;
    private String location;
    private int spots;
    private String description;
}
