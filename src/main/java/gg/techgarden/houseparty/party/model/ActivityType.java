package gg.techgarden.houseparty.party.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {
    COMMENT("comment"),
    REACTION("reaction"),
    REPLY("reply"),
    STATUS("status");

    private final String type;
}
