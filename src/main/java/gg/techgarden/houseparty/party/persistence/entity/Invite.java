package gg.techgarden.houseparty.party.persistence.entity;

import gg.techgarden.houseparty.party.model.InviteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Invite {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID partyId;
    private UUID userId;
    private UUID invitedById;
    @Enumerated(EnumType.STRING)
    private InviteStatus status;
}
