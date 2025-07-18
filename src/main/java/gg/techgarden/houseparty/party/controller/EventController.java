package gg.techgarden.houseparty.party.controller;

import gg.techgarden.houseparty.party.model.UserParty;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import gg.techgarden.houseparty.party.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/upcoming")
    public Page<UserParty> getUpcomingEvents(Pageable pageable) {
        // all events that you are invited to (as a host, guest) and NOT declined
        return eventService.findUpcomingEventsByUserId(pageable);
    }

    @GetMapping("/pending")
    public List<Party> getPendingEvents() {
        // all events that you are invited to (as a host, guest) and NOT accepted
        return List.of();
    }

    @GetMapping("/past")
    public List<Party> getPastEvents() {
        // all events that you are invited to (as a host, guest) and have already happened
        return List.of();
    }

    @GetMapping("/hosting")
    public List<Party> getHostingEvents() {
        // all events that you are hosting (as a host)
        return List.of();
    }

    @GetMapping("/detail/{id}")
    public UserParty getEventDetailById(@PathVariable UUID id) {
        return eventService.getEventDetailById(id);
    }
}
