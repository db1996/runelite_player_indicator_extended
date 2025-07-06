package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.PlayerRender.PlayerRenderPropertiesService;
import com.playerindicatorextended.enums.HighlightSetting;
import com.playerindicatorextended.enums.HighlighterType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.WorldView;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Singleton
@Slf4j
public class OthersHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public OthersHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.OTHER;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
    }


    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if (config.highlightOthers() == HighlightSetting.DISABLED) {
            return null;
        }

        if (config.highlightOthers() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)) {
            return null;
        }

        if (player == null || player.getName() == null || Objects.equals(player.getName(), localPlayer.getName())) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightOthersColor())
                .renderNameLocation(config.othersPlayerNameLocation())
                .renderOutline(config.othersPlayerOutline())
                .renderMinimap(config.othersPlayerMinimapAnimation())
                .renderTile(config.othersPlayerTile())
                .renderHull(config.othersPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .renderCombatLevel(config.othersPlayerCombatLevel())
                .build();
    }

}
