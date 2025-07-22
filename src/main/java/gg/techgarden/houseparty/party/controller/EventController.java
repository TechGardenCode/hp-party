package gg.techgarden.houseparty.party.controller;

import gg.techgarden.houseparty.party.model.UserParty;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/stats")
    public Map<String, Integer> getEventStats() {
        return eventService.getEventStats();
    }

    @GetMapping("/upcoming")
    public Page<UserParty> getUpcomingEvents(Pageable pageable) {
        return eventService.findUpcomingEventsByUserId(pageable);
    }

    @GetMapping("/pending")
    public Page<UserParty> getPendingEvents(Pageable pageable) {
        return eventService.findPendingEventsByUserId(pageable);
    }

    @GetMapping("/past")
    public Page<UserParty> getPastEvents(Pageable pageable) {
        return eventService.findPastEventsByUserId(pageable);
    }

    @GetMapping("/hosting")
    public List<Party> getHostingEvents() {
        return List.of();
    }

    @GetMapping("/detail/{id}")
    public UserParty getEventDetailById(@PathVariable UUID id) {
        return eventService.getEventDetailById(id);
    }
}
