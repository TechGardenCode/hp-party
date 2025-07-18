package gg.techgarden.houseparty.party.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InviteStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    MAYBE("maybe"),
    REJECTED("rejected");

    private final String status;
}
