package com.playerindicatorextended.PlayerRender;

import lombok.Builder;
import lombok.Value;
import net.runelite.api.Player;

import java.awt.*;

@Value
@Builder
public class PlayerRenderDecision
{
    Player player;
    boolean renderName;
    Color nameColor;
    boolean renderOutline;
    Color outlineColor;
    boolean renderMinimap;
    Color minimapColor;
    int priority; // Higher priority overrides lower
}