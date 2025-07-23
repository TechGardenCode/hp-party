package gg.techgarden.houseparty.party.persistence.repository;

import gg.techgarden.houseparty.party.persistence.entity.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PartyRepository extends JpaRepository<Party, UUID> {
    Optional<Party> findByIdAndCreatedById(UUID id, UUID createdBy);
    boolean existsByIdAndCreatedById(UUID id, UUID createdBy);

    @Query("SELECT p, i FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE (" +
            "   (i.userId = :userId OR p.createdBy.id = :userId) " +
            "   AND i.status <> 'DECLINED' " +
            "   AND i.userId <> i.invitedById " +
            ") " +
            "AND p.startDateTime > CURRENT_TIMESTAMP")
    Page<Object[]> findUpcomingEventsByUserId(UUID userId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE (" +
            "   (i.userId = :userId OR p.createdBy.id = :userId) " +
            "   AND i.status <> 'DECLINED' " +
            "   AND i.userId <> i.invitedById " +
            ") " +
            "AND p.startDateTime > CURRENT_TIMESTAMP")
    int countUpcomingEventsByUserId(UUID userId);

    @Query("SELECT p, i FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE (" +
            "   (i.userId = :userId OR p.createdBy.id = :userId) " +
            "   AND i.status = 'PENDING' " +
            "   AND i.userId <> i.invitedById " +
            ") " +
            "AND p.startDateTime > CURRENT_TIMESTAMP")
    Page<Object[]> findPendingEventsByUserId(UUID userId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE (" +
            "   (i.userId = :userId OR p.createdBy.id = :userId) " +
            "   AND i.status = 'PENDING' " +
            "   AND i.userId <> i.invitedById " +
            ") " +
            "AND p.startDateTime > CURRENT_TIMESTAMP")
    int countPendingEventsByUserId(UUID userId);

    @Query("SELECT p, i FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE (" +
            "   (i.userId = :userId OR p.createdBy.id = :userId) " +
            "   AND i.status <> 'DECLINED' " +
            "   AND i.userId <> i.invitedById " +
            ") " +
            "AND p.startDateTime < CURRENT_TIMESTAMP")
    Page<Object[]> findPastEventsByUserId(UUID userId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE (" +
            "   (i.userId = :userId OR p.createdBy.id = :userId) " +
            "   AND i.status <> 'DECLINED' " +
            "   AND i.userId <> i.invitedById " +
            ") " +
            "AND p.startDateTime < CURRENT_TIMESTAMP")
    int countPastEventsByUserId(UUID userId);

    @Query("SELECT p, i FROM Party p " +
            "LEFT JOIN Invite i ON p.id = i.partyId " +
            "WHERE p.id = :id AND i.userId = :userId")
    Optional<Object[][]> findEventDetailById(UUID id, UUID userId);
}
