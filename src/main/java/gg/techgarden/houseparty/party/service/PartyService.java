package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.model.InviteStatus;
import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.PartyRepository;
import gg.techgarden.houseparty.party.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final InviteService inviteService;

    private final PartyRepository partyRepository;

    public Party createParty(Party party) {
        UUID userId = UserSessionUtil.getCurrentUserId()
                .orElseThrow();
        party.setCreatedBy(UserInfo.builder().id(userId).build());
        party = partyRepository.save(party);
        Invite invite = Invite.builder()
                .partyId(party.getId())
                .userId(userId)
                .invitedById(userId)
                .status(InviteStatus.ACCEPTED)
                .build();
        inviteService.createInvite(invite);
        return party;
    }

    public Party getPartyById(UUID id) {
        return partyRepository.findByIdAndCreatedById(id, UserSessionUtil.getCurrentUserId().orElseThrow())
                .orElseThrow(() -> new RuntimeException("Party not found with id: " + id));
    }

    public void deleteParty(UUID id) {
        if (!partyRepository.existsByIdAndCreatedById(id, UserSessionUtil.getCurrentUserId().orElseThrow())) {
            throw new RuntimeException("Party not found with id: " + id);
        }
        partyRepository.deleteById(id);
    }

    public Party updateParty(UUID id, Party party) {
        if (!partyRepository.existsByIdAndCreatedById(id, UserSessionUtil.getCurrentUserId().orElseThrow())) {
            throw new RuntimeException("Party not found with id: " + id);
        }
        party.setId(id);
        return partyRepository.save(party);
    }
}
