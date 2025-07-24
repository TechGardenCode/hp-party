package gg.techgarden.houseparty.party.service;

import gg.techgarden.houseparty.party.model.ActivityType;
import gg.techgarden.houseparty.party.persistence.entity.Activity;
import gg.techgarden.houseparty.party.persistence.entity.UserInfo;
import gg.techgarden.houseparty.party.persistence.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final PartyService partyService;
    private final UserService userService;

    private final ActivityRepository activityRepository;

    public Page<Activity> getActivitiesByPartyId(UUID partyId, Pageable pageable) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        if (!partyService.canUserEditParty(partyId, userId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "User does not have permission to view activities for this party");
        }

        return activityRepository.findAllByPartyIdAndUserIdAndTypeAndParentIdIsNullOrderByCreatedAtDesc(partyId, userId, ActivityType.COMMENT, pageable);
    }

    public Activity createActivity(Activity activity) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        canUserAddActivity(activity.getPartyId(), userId);

        activity.setUser(userInfo);
        return activityRepository.save(activity);
    }

    public Activity updateActivity(UUID id, Activity activity) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        if (!activityRepository.existsByIdAndUserId(id, userId)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        activity.setId(id);
        activity.setUser(userInfo);
        return activityRepository.save(activity);
    }

    public Activity getActivityById(UUID id) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        return activityRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteActivity(UUID id) {
        UserInfo userInfo = userService.getProfile();
        UUID userId = userInfo.getId();

        if (!activityRepository.existsByIdAndUserId(id, userId)) {
            throw new RuntimeException("Activity not found with id: " + id);
        }
        activityRepository.deleteById(id);
    }

    void canUserAddActivity(UUID partyId, UUID userId) {
        if (!partyService.canUserEditParty(partyId, userId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "User does not have permission to add activity to this party");
        }
    }
}
