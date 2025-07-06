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
    public HighlighterType getHighlighterType() {
        return HighlighterType.OWN_PLAYER;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if (player == null) {
            return null;
        }

        if (!player.equals(localPlayer)) {
            return null;
        }

        if (config.highlightOwnPlayer() == HighlightSetting.DISABLED) {
            return null;
        }

        if (config.highlightOwnPlayer() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightOwnPlayerColor())
                .renderNameLocation(config.ownPlayerNameLocation())
                .renderOutline(config.ownPlayerOutline())
                .renderMinimap(config.ownPlayerMinimapAnimation())
                .renderTile(config.ownPlayerTile())
                .renderHull(config.ownPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .renderCombatLevel(config.ownPlayerCombatLevel())
                .build();
    }
}
