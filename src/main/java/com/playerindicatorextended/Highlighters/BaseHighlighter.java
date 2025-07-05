package com.playerindicatorextended.Highlighters;


import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.enums.HighlighterType;
import net.runelite.api.Player;

import java.util.List;

public abstract class BaseHighlighter
{
    public abstract HighlighterType getHighlighterType();
    public abstract int getPriority();
    public abstract List<PlayerRenderProperties> getRenderDecisions();
    public abstract PlayerRenderProperties getRenderProperties(Player player, Player localPlayer);
}
