package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.model.UserParty;
import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final PartyRepository partyRepository;
    private final UserService userService;

    public Page<UserParty> findUpcomingEventsByUserId(Pageable pageable) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        Page<Object[]> partyInvite = partyRepository.findUpcomingEventsByUserId(userId, pageable);
        return partyInvite.map(obj -> {
            Party party = (Party) obj[0];
            Invite invite = (Invite) obj[1];
            return UserParty.builder().party(party).invite(invite).build();
        });

    }

    public UserParty getEventDetailById(UUID id) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        Object[][] partyInvite = partyRepository.findEventDetailById(id, userId)
                .orElseThrow();

        if (partyInvite.length != 1 || partyInvite[0].length != 2) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        Party party = (Party) partyInvite[0][0];
        Invite invite = (Invite) partyInvite[0][1];
        return UserParty.builder().party(party).invite(invite).build();
    }

    public Page<UserParty> findPendingEventsByUserId(Pageable pageable) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        Page<Object[]> partyInvite = partyRepository.findPendingEventsByUserId(userId, pageable);
        return partyInvite.map(obj -> {
            Party party = (Party) obj[0];
            Invite invite = (Invite) obj[1];
            return UserParty.builder().party(party).invite(invite).build();
        });
    }

    public Page<UserParty> findPastEventsByUserId(Pageable pageable) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        Page<Object[]> partyInvite = partyRepository.findPastEventsByUserId(userId, pageable);
        return partyInvite.map(obj -> {
            Party party = (Party) obj[0];
            Invite invite = (Invite) obj[1];
            return UserParty.builder().party(party).invite(invite).build();
        });
    }

    public Map<String, Integer> getEventStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("upcoming", partyRepository.countUpcomingEventsByUserId(userService.getProfile().getId()));
        stats.put("pending", partyRepository.countPendingEventsByUserId(userService.getProfile().getId()));
        stats.put("past", partyRepository.countPastEventsByUserId(userService.getProfile().getId()));
        return stats;
    }
}
