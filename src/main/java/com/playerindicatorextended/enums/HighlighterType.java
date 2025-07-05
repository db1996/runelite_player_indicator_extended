package com.playerindicatorextended.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HighlighterType {
    OWN_PLAYER(100),
    PARTY(2),
    FRIENDS_CHAT(3),
    TEAM_MEMBERS(4),
    CLAN_MEMBERS(5),
    FRIENDS(6),
    ATTACKABLE(7),
    IGNORED(8),
    TAGGED (9),
    OTHER(1);

    private final int priority;
}

