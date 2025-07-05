package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.PlayerRender.PlayerRenderPropertiesService;
import com.playerindicatorextended.enums.HighlightSetting;
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
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public OwnPlayerHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService  playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;

    }

    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(config.highlightOwnPlayer() == HighlightSetting.DISABLED){
            return Collections.emptyList();
        }

        if(config.highlightOwnPlayer() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
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
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .build();

        return Collections.singletonList(decision);
    }
}
