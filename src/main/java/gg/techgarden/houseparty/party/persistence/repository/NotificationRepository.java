package gg.techgarden.houseparty.party.persistence.repository;

import gg.techgarden.houseparty.party.persistence.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findAllByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    Page<Notification> findAllByUserIdAndReadFalseOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
