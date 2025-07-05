package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.Highlighters.BaseHighlighter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Player;

import javax.inject.Singleton;
import java.util.*;

@Slf4j
@Singleton
public class PlayerRenderPropertiesService
{
    private final List<BaseHighlighter> highlighters = new ArrayList<>();

    public void registerHighlighter(BaseHighlighter highlighter)
    {
        log.debug("registering highlighter {}", highlighter);
        highlighters.add(highlighter);
    }

    public List<PlayerRenderProperties> getRenderDecisions()
    {
        Map<Player, PlayerRenderProperties> decisionMap = new HashMap<>();

        for (BaseHighlighter highlighter : highlighters)
        {
            for (PlayerRenderProperties decision : highlighter.getRenderDecisions())
            {
                decisionMap.merge(decision.getPlayer(), decision, this::mergeDecisions);
            }
        }

        List<PlayerRenderProperties> decisions = new ArrayList<>();
        for (Map.Entry<Player, PlayerRenderProperties> entry : decisionMap.entrySet()){
            PlayerRenderProperties decision = entry.getValue();
            decisions.add(decision);
        }

        return decisions;
    }

    private PlayerRenderProperties mergeDecisions(PlayerRenderProperties a, PlayerRenderProperties b)
    {
        return a.getPriority() >= b.getPriority() ? a : b;
    }
}
