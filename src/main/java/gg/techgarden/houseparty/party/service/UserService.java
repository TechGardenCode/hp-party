package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.UserInfoRepository;
import gg.techgarden.houseparty.party.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserInfoRepository userInfoRepository;

    public UserInfo getProfile() {
        UserInfo userInfo = UserSessionUtil.getCurrentUserInfo().orElseThrow();
        if (!userInfoRepository.existsById(userInfo.getId())) {
            try {
                userInfoRepository.save(userInfo);
            } catch (DataIntegrityViolationException e) {
                log.debug("Unable to save user info: {}", e.getMessage(), e);
            }
        }
        return userInfo;
    }

    public Optional<UserInfo> findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    public Optional<UserInfo> findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }
}
