package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.enums.HighlighterType;
import com.playerindicatorextended.enums.MinimapAnimation;
import com.playerindicatorextended.enums.NameLocation;
import lombok.Builder;
import lombok.Value;
import net.runelite.api.Player;

import java.awt.*;

@Value
@Builder
public class PlayerRenderProperties
{
    Player player;
    HighlighterType renderHighlighterType;
    Color renderColor;
    int priority; // Higher priority overrides lower

    NameLocation renderNameLocation;
    MinimapAnimation renderMinimap;
    NameLocation renderLabelLocation;
    String renderLabel;
    boolean renderOutline;
    boolean renderHull;
    boolean renderTile;
    boolean renderFriendsChatRank;
    boolean renderClanChatRank;
    boolean renderCombatLevel;
}