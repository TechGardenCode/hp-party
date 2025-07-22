package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.model.InviteStatus;
import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.InviteRepository;
import gg.techgarden.houseparty.party.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserService userService;

    public Invite createInviteByEmailOrUsername(UUID partyId, String email, String username) {
        if (email != null && !email.isEmpty()) {
            return createInviteByEmail(email, partyId);
        } else if (username != null && !username.isEmpty()) {
            return createInviteByUsername(username, partyId);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Either email or username must be provided");
        }
    }

    public Invite createInviteByUsername(String username, UUID partyId) {
        UserInfo userInfo = userService.findByUsername(username).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Invite invite = Invite.builder()
                .userId(userInfo.getId())
                .partyId(partyId)
                .invitedById(UserSessionUtil.getCurrentUserId().orElseThrow())
                .status(InviteStatus.PENDING)
                .build();
        return inviteRepository.save(invite);
    }

    public Invite createInviteByEmail(String email, UUID partyId) {
        UserInfo userInfo = userService.findByEmail(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Invite invite = Invite.builder()
                .userId(userInfo.getId())
                .partyId(partyId)
                .invitedById(UserSessionUtil.getCurrentUserId().orElseThrow())
                .status(InviteStatus.PENDING)
                .build();
        return inviteRepository.save(invite);
    }

    public Invite createInvite(Invite invite) {
        return inviteRepository.save(invite);
    }
}
