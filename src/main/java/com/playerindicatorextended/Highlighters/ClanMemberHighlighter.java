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
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(config.highlightClan() == HighlightSetting.DISABLED){
            return Collections.emptyList();
        }

        if(config.highlightClan() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
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
            if(player.isClanMember()){
                PlayerRenderProperties decision = PlayerRenderProperties.builder()
                        .player(player)
                        .renderColor(config.highlightClanColor())
                        .renderNameLocation(config.clanPlayerNameLocation())
                        .renderOutline(config.clanPlayerOutline())
                        .renderMinimap(config.clanPlayerMinimapAnimation())
                        .renderTile(config.clanPlayerTile())
                        .renderHull(config.clanPlayerHull())
                        .priority(HighlighterType.CLAN_MEMBERS.getPriority())
                        .renderClanChatRank(config.clanChatRank())
                        .renderFriendsChatRank(config.clanChatRank())
                        .build();
                result.add(decision);
            }

        }

        return result;
    }
}
