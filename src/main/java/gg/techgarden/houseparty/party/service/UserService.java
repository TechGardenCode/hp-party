package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.UserInfoRepository;
import gg.techgarden.houseparty.party.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserInfoRepository userInfoRepository;

    public UserInfo getProfile() {
        UserInfo userInfo = UserSessionUtil.getCurrentUserInfo().orElseThrow();
        if (!userInfoRepository.existsById(userInfo.getId())) {
            userInfoRepository.save(userInfo);
        }
        return userInfo;
    }
}
