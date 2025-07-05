package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.Highlighters.BaseHighlighter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.WorldView;
import net.runelite.api.gameval.VarbitID;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Slf4j
@Singleton
public class PlayerRenderPropertiesService
{
    private final List<BaseHighlighter> highlighters = new ArrayList<>();
    private final Client client;

    @Inject
    public PlayerRenderPropertiesService(Client client) {
        this.client = client;
    }

    public void registerHighlighter(BaseHighlighter highlighter)
    {
        log.debug("registering highlighter {}", highlighter);
        highlighters.add(highlighter);
        resortHighlighters();
    }

    public void resortHighlighters()
    {
        highlighters.sort(Comparator.comparingInt(BaseHighlighter::getPriority).reversed());
    }

    public List<PlayerRenderProperties> getRenderDecisions()
    {
        WorldView worldView = client.getTopLevelWorldView();
        if(worldView == null){
            return Collections.emptyList();
        }

        Player localPlayer = client.getLocalPlayer();
        if(localPlayer == null){
            return Collections.emptyList();
        }

        List<PlayerRenderProperties> decisions = new ArrayList<>();
        for (Player player : worldView.players()) {
            if (player == null || player.getName() == null) {
                continue;
            }

            for (BaseHighlighter highlighter : highlighters) {
                if(highlighter == null)
                    continue;

                PlayerRenderProperties properties = highlighter.getRenderProperties(player, localPlayer);
                if (properties != null) {
                    decisions.add(properties);
                    break;
                }
            }
        }

        return decisions;
    }

    private PlayerRenderProperties mergeDecisions(PlayerRenderProperties a, PlayerRenderProperties b)
    {
        return a.getPriority() >= b.getPriority() ? a : b;
    }

    public boolean isPvp(Client client){
        return (client.getVarbitValue(VarbitID.INSIDE_WILDERNESS) == 1 || client.getVarbitValue(VarbitID.PVP_AREA_CLIENT) == 1);
    }
}
