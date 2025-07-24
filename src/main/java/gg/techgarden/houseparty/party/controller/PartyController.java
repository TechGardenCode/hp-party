package gg.techgarden.houseparty.party.controller;

import gg.techgarden.houseparty.party.persistence.entity.Activity;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.service.ActivityService;
import gg.techgarden.houseparty.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;
    private final ActivityService activityService;

    @GetMapping("/{id}")
    public Party getPartyById(@PathVariable UUID id) {
        return partyService.getPartyById(id);
    }

    @PostMapping
    public Party createParty(@RequestBody Party party) {
        return partyService.createParty(party);
    }

    @PutMapping("/{id}")
    public Party updateParty(@PathVariable UUID id, @RequestBody Party party) {
        return partyService.updateParty(id, party);
    }

    @DeleteMapping("/{id}")
    public void deleteParty(@PathVariable UUID id) {
        partyService.deleteParty(id);
    }

    @GetMapping("/{id}/activity")
    public Page<Activity> getActivitiesByPartyId(@PathVariable UUID id, Pageable pageable) {
        return activityService.getActivitiesByPartyId(id, pageable);
    }
}
