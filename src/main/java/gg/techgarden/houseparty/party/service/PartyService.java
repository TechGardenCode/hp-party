package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.constants.NotificationConstants;
import gg.techgarden.houseparty.party.model.InviteStatus;
import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.Notification;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final InviteService inviteService;

    private final PartyRepository partyRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    public Party createParty(Party party) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();
        
        party.setCreatedBy(UserInfo.builder().id(userId).build());
        party = partyRepository.save(party);
        Invite invite = Invite.builder()
                .partyId(party.getId())
                .userId(userId)
                .invitedById(userId)
                .status(InviteStatus.ACCEPTED)
                .build();
        inviteService.createInvite(invite);
        Notification notification = Notification.builder()
                .userId(userId)
                .title(NotificationConstants.TITLE_PARTY_CREATED)
                .message("Your party " + party.getTitle() + " has been created successfully.")
                .actionUrl("/event/detail/" + party.getId())
                .build();
        notificationService.createNotification(notification);
        return party;
    }

    public Party getPartyById(UUID id) {
        
        return partyRepository.findByIdAndCreatedById(id, userService.getProfile().getId())
                .orElseThrow(() -> new RuntimeException("Party not found with id: " + id));
    }

    public void deleteParty(UUID id) {
        if (!partyRepository.existsByIdAndCreatedById(id, userService.getProfile().getId())) {
            throw new RuntimeException("Party not found with id: " + id);
        }
        partyRepository.deleteById(id);
    }

    public Party updateParty(UUID id, Party party) {
        if (!partyRepository.existsByIdAndCreatedById(id, userService.getProfile().getId())) {
            throw new RuntimeException("Party not found with id: " + id);
        }
        party.setId(id);
        return partyRepository.save(party);
    }
}
