package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.constants.NotificationConstants;
import gg.techgarden.houseparty.party.model.InviteStatus;
import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.Notification;
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
    private final NotificationService notificationService;

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
        createInviteNotification(invite);
        return createInvite(invite);
    }

    public Invite createInviteByEmail(String email, UUID partyId) {
        UserInfo userInfo = userService.findByEmail(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Invite invite = Invite.builder()
                .userId(userInfo.getId())
                .partyId(partyId)
                .invitedById(UserSessionUtil.getCurrentUserId().orElseThrow())
                .status(InviteStatus.PENDING)
                .build();
        createInviteNotification(invite);
        return createInvite(invite);
    }

    public Invite createInvite(Invite invite) {
        return inviteRepository.save(invite);
    }

    void createInviteNotification(Invite invite) {
        UserInfo userInfo = userService.getProfile();
        Notification notification = Notification.builder()
                .userId(invite.getUserId())
                .title(NotificationConstants.TITLE_PARTY_INVITE)
                .message("You have been invited to a party by " + userInfo.getUsername())
                .actionUrl("/event/detail/" + invite.getPartyId())
                .build();
        notificationService.createNotification(notification);
    }

    public boolean canUserEditInvite(UUID partyId, UUID userId) {
        return inviteRepository.existsByPartyIdAndUserId(partyId, userId);
    }
}
