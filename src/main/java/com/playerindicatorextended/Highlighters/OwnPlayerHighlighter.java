package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.enums.HighlighterType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class OwnPlayerHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;

    @Inject
    public OwnPlayerHighlighter(Client client, PlayerIndicatorExtendedConfig config)
    {
        this.client = client;
        this.config = config;
    }

    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(!config.highlightOwnPlayer()){
            return Collections.emptyList();
        }

        Player localPlayer = client.getLocalPlayer();
        if (localPlayer == null)
        {
            return Collections.emptyList();
        }

        PlayerRenderProperties decision = PlayerRenderProperties.builder()
                .player(localPlayer)
                .renderColor(config.highlightOwnPlayerColor())
                .renderNameLocation(config.ownPlayerPlayerNameLocation())
                .renderOutline(config.ownPlayerPlayerOutline())
                .renderMinimap(config.ownPlayerPlayerMinimapAnimation())
                .renderTile(config.ownPlayerPlayerTile())
                .renderHull(config.ownPlayerPlayerHull())
                .priority(HighlighterType.OWN_PLAYER.getPriority())
                .renderClanChatRank(true)
                .build();

        return Collections.singletonList(decision);
    }
}
