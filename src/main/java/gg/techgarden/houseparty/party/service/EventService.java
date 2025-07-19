package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.model.UserParty;
import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.PartyRepository;
import gg.techgarden.houseparty.party.persistence.repository.UserInfoRepository;
import gg.techgarden.houseparty.party.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final PartyRepository partyRepository;
    private final UserInfoRepository userInfoRepository;

    public Page<UserParty> findUpcomingEventsByUserId(Pageable pageable) {
        UUID userId = UserSessionUtil.getCurrentUserId()
                .orElseThrow();
        if (!userInfoRepository.existsById(userId)) {
            UserInfo userInfo = UserSessionUtil.getCurrentUserInfo().orElseThrow();
            userInfoRepository.save(userInfo);
        }
        Page<Object[]> partyInvite = partyRepository.findUpcomingEventsByUserId(UserSessionUtil.getCurrentUserId().orElseThrow(), pageable);
        return partyInvite.map(obj -> {
            Party party = (Party) obj[0];
            Invite invite = (Invite) obj[1];
            return UserParty.builder().party(party).invite(invite).build();
        });

    }

    public UserParty getEventDetailById(UUID id) {
        UUID userId = UserSessionUtil.getCurrentUserId()
                .orElseThrow();
        if (!userInfoRepository.existsById(userId)) {
            UserInfo userInfo = UserSessionUtil.getCurrentUserInfo().orElseThrow();
            userInfoRepository.save(userInfo);
        }
        Object[][] partyInvite = partyRepository.findEventDetailById(id, userId)
                .orElseThrow();

        if (partyInvite.length != 1 || partyInvite[0].length != 2) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        Party party = (Party) partyInvite[0][0];
        Invite invite = (Invite) partyInvite[0][1];
        return UserParty.builder().party(party).invite(invite).build();
    }
}
