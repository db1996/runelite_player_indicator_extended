package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
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

    @Inject
    public TeamHighlighter(Client client, PlayerIndicatorExtendedConfig config)
    {
        this.client = client;
        this.config = config;
    }


    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(!config.highlightTeam()){
            return Collections.emptyList();
        }

        WorldView worldView = client.getTopLevelWorldView();
        if(worldView == null){
            return Collections.emptyList();
        }

        List<PlayerRenderProperties> result = new ArrayList<>();
        for (Player player : worldView.players()) {
            if (player == null || player.getName() == null)
            {
                return null;
            }

            if (player.getTeam() > 0 && client.getLocalPlayer().getTeam() == player.getTeam()){
                PlayerRenderProperties decision = PlayerRenderProperties.builder()
                        .player(player)
                        .renderColor(config.highlightTeamColor())
                        .renderNameLocation(config.teamPlayerNameLocation())
                        .renderOutline(config.teamPlayerOutline())
                        .renderMinimap(config.teamPlayerMinimapAnimation())
                        .renderTile(config.teamPlayerTile())
                        .renderHull(config.teamPlayerHull())
                        .priority(HighlighterType.FRIENDS.getPriority())
                        .renderClanChatRank(config.clanChatRank())
                        .renderFriendsChatRank(config.friendsChatRank())
                        .build();
                result.add(decision);
            }
        }

        return result;
    }
}
