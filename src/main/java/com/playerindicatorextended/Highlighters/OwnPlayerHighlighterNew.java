package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.Highlighters.BaseHighlighterNew;
import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderDecision;
import net.runelite.api.Client;
import net.runelite.api.Player;
import javax.inject.Inject;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class OwnPlayerHighlighterNew extends BaseHighlighterNew
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;

    @Inject
    public OwnPlayerHighlighterNew(Client client, PlayerIndicatorExtendedConfig config)
    {
        this.client = client;
        this.config = config;
    }

    @Override
    public List<PlayerRenderDecision> getRenderDecisions()
    {
        if(!config.highlightOwnPlayer()){
            return Collections.emptyList();
        }

        Player localPlayer = client.getLocalPlayer();
        if (localPlayer == null)
        {
            return Collections.emptyList();
        }

        PlayerRenderDecision decision = PlayerRenderDecision.builder()
                .player(localPlayer)
                .renderName(true)
                .nameColor(Color.WHITE)
                .renderOutline(true)
                .outlineColor(Color.CYAN)
                .renderMinimap(true)
                .minimapColor(Color.CYAN)
                .priority(100) // High priority for self
                .build();

        return Collections.singletonList(decision);
    }
}
