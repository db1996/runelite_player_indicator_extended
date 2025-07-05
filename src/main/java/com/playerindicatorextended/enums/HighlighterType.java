package com.playerindicatorextended.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HighlighterType {
    OWN_PLAYER(100),
    PARTY(20),
    FRIENDS(30),
    FRIENDS_CHAT(31),
    TEAM_MEMBERS(50),
    CLAN_MEMBERS(40),
    IGNORED(90),
    TAGGED (95),
    OTHER(1);

    private final int priority;
}

