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

@Singleton
@Slf4j
public class ClanMemberHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public ClanMemberHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
    }


    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.CLAN_MEMBERS;
    }

    @Override
    public int getPriority() {
        return config.priorityClan();
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if (config.highlightClan() == HighlightSetting.DISABLED) {
            return null;
        }

        if (config.highlightClan() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)) {
            return null;
        }

        if (player == null || player.getName() == null || player.getName().equals(localPlayer.getName())) {
            return null;
        }

        if (!player.isClanMember()) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightClanColor())
                .renderNameLocation(config.clanPlayerNameLocation())
                .renderOutline(config.clanPlayerOutline())
                .renderMinimap(config.clanPlayerMinimapAnimation())
                .renderTile(config.clanPlayerTile())
                .renderHull(config.clanPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.clanChatRank())
                .renderCombatLevel(config.clanPlayerCombatLevel())
                .build();
    }
}
