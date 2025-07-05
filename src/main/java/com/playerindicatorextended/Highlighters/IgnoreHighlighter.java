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
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(config.highlightIgnore() == HighlightSetting.DISABLED){
            return Collections.emptyList();
        }

        if(config.highlightIgnore() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
            return Collections.emptyList();
        }

        Player localPlayer = client.getLocalPlayer();
        if(localPlayer == null){
            return Collections.emptyList();
        }

        WorldView worldView = client.getTopLevelWorldView();
        if(worldView == null){
            return Collections.emptyList();
        }

        List<PlayerRenderProperties> result = new ArrayList<>();
        for (Player player : worldView.players()) {
            if (player == null || player.getName() == null || player.equals(localPlayer))
            {
                continue;
            }

            if (isIgnored(player)){
                PlayerRenderProperties decision = PlayerRenderProperties.builder()
                        .player(player)
                        .renderColor(config.highlightIgnoreColor())
                        .renderNameLocation(config.ignoredPlayerNameLocation())
                        .renderOutline(config.ignoredPlayerOutline())
                        .renderMinimap(config.ignoredPlayerMinimapAnimation())
                        .renderTile(config.ignoredPlayerTile())
                        .renderHull(config.ignoredPlayerHull())
                        .priority(HighlighterType.TEAM_MEMBERS.getPriority())
                        .renderClanChatRank(config.clanChatRank())
                        .renderFriendsChatRank(config.friendsChatRank())
                        .build();
                result.add(decision);
            }
        }

        return result;
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
