package gg.techgarden.houseparty.party.controller;

import gg.techgarden.houseparty.party.persistence.entity.Notification;
import gg.techgarden.houseparty.party.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/all")
    public Page<Notification> getCurrentUserNotifications(Pageable pageable) {
        return notificationService.getCurrentUserNotifications(pageable);
    }

    @GetMapping("/unread")
    public Page<Notification> getUnreadNotifications(Pageable pageable) {
        return notificationService.getCurrentUserUnreadNotifications(pageable);
    }

    @PostMapping("/{id}/read")
    public void markNotificationAsRead(@PathVariable UUID id) {
        notificationService.markNotificationAsRead(id);
    }

}
