package gg.techgarden.houseparty.party.controller;

import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping("/create/{partyId}")
    public Invite createInviteByEmail(@PathVariable UUID partyId, @RequestParam(required = false) String email, @RequestParam(required = false) String username) {
        return inviteService.createInviteByEmailOrUsername(partyId, email, username);
    }
}
