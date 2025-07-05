package com.playerindicatorextended.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HighlightSetting {
    DISABLED("Disabled"),
    ENABLED("Enabled"),
    PVP("PVP");

    private final String id;
}
