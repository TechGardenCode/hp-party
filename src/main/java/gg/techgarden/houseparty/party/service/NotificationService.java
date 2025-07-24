package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.persistence.entity.Notification;
import gg.techgarden.houseparty.party.persistence.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final NotificationRepository notificationRepository;

    public Page<Notification> getCurrentUserNotifications(Pageable pageable) {
        UUID userId = userService.getProfile().getId();
        return getNotificationsByUserId(userId, pageable);
    }

    public Page<Notification> getCurrentUserUnreadNotifications(Pageable pageable) {
        UUID userId = userService.getProfile().getId();
        return getUnreadNotificationsByUserId(userId, pageable);
    }

    Page<Notification> getUnreadNotificationsByUserId(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndReadFalseOrderByCreatedAtDesc(userId,  pageable);
    }

    Page<Notification> getNotificationsByUserId(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(UUID id, Notification notification) {
        if (!notificationRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        notification.setId(id);
        return notificationRepository.save(notification);
    }

    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void markNotificationAsRead(UUID id) {
        Notification notification = getNotificationById(id);
        notification.setRead(true);
        updateNotification(id, notification);
    }
}
