package gg.techgarden.houseparty.party.persistence.repository;

import gg.techgarden.houseparty.party.persistence.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InviteRepository extends JpaRepository<Invite, UUID> {
    boolean existsByPartyIdAndUserId(UUID partyId, UUID userId);
}
