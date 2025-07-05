package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.PlayerRender.PlayerRenderPropertiesService;
import com.playerindicatorextended.enums.HighlightSetting;
import com.playerindicatorextended.enums.HighlighterType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Ignore;
import net.runelite.api.Player;
import net.runelite.api.WorldView;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class IgnoreHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public IgnoreHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.IGNORED;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if (config.highlightIgnore() == HighlightSetting.DISABLED) {
            return null;
        }

        if (config.highlightIgnore() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)) {
            return null;
        }

        if (player == null || player.getName() == null || player.getName().equals(localPlayer.getName())) {
            return null;
        }

        if (!isIgnored(player)) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightIgnoreColor())
                .renderNameLocation(config.ignoredPlayerNameLocation())
                .renderOutline(config.ignoredPlayerOutline())
                .renderMinimap(config.ignoredPlayerMinimapAnimation())
                .renderTile(config.ignoredPlayerTile())
                .renderHull(config.ignoredPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .build();
    }

    private boolean isIgnored(Player player)
    {
        String name = player.getName();
        if(name == null || client.getIgnoreContainer() == null){
            return false;
        }

        Ignore onIgnoreList = client.getIgnoreContainer().findByName(name);
        return (onIgnoreList != null);
    }
}
