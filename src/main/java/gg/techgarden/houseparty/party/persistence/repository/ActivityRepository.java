package gg.techgarden.houseparty.party.persistence.repository;

import gg.techgarden.houseparty.party.model.ActivityType;
import gg.techgarden.houseparty.party.persistence.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    Optional<Activity> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByIdAndUserId(UUID id, UUID userId);

    Page<Activity> findAllByPartyIdAndUserIdAndTypeAndParentIdIsNullOrderByCreatedAtDesc(UUID partyId, UUID userId, ActivityType type, Pageable pageable);

}
