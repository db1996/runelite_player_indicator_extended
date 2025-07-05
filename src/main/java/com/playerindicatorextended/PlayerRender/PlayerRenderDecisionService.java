package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.Highlighters.BaseHighlighterNew;
import net.runelite.api.Player;
import java.util.*;

public class PlayerRenderDecisionService
{
    private final List<BaseHighlighterNew> highlighters = new ArrayList<>();

    public void registerHighlighter(BaseHighlighterNew highlighter)
    {
        highlighters.add(highlighter);
    }

    public Map<Player, PlayerRenderDecision> getRenderDecisions()
    {
        Map<Player, PlayerRenderDecision> decisionMap = new HashMap<>();

        for (BaseHighlighterNew highlighter : highlighters)
        {
            for (PlayerRenderDecision decision : highlighter.getRenderDecisions())
            {
                decisionMap.merge(decision.getPlayer(), decision, this::mergeDecisions);
            }
        }

        return decisionMap;
    }

    private PlayerRenderDecision mergeDecisions(PlayerRenderDecision a, PlayerRenderDecision b)
    {
        return a.getPriority() >= b.getPriority() ? a : b;
    }
}
