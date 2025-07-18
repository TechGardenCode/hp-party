package gg.techgarden.houseparty.party.persistence.repository;

import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByUsername(String username);
}
