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
public class TeamHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public TeamHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.TEAM_MEMBERS;
    }

    @Override
    public int getPriority() {
        return config.priorityTeam();
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if(config.highlightTeam() == HighlightSetting.DISABLED){
            return null;
        }

        if (player == null || player.getName() == null) {
            return null;
        }

        if(config.highlightTeam() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
            return null;
        }

        int playerTeam = player.getTeam();
        int localTeam = localPlayer.getTeam();

        if (playerTeam <= 0 || playerTeam != localTeam) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightTeamColor())
                .renderNameLocation(config.teamPlayerNameLocation())
                .renderOutline(config.teamPlayerOutline())
                .renderMinimap(config.teamPlayerMinimapAnimation())
                .renderTile(config.teamPlayerTile())
                .renderHull(config.teamPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .renderCombatLevel(config.teamPlayerCombatLevel())
                .build();
    }

}
