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
import java.util.*;

@Singleton
@Slf4j
public class FriendsHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;

    @Inject
    public FriendsHighlighter(Client client, PlayerIndicatorExtendedConfig config)
    {
        this.client = client;
        this.config = config;
    }


    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(!config.highlightFriends()){
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
            if(player.isFriend()){
                PlayerRenderProperties decision = PlayerRenderProperties.builder()
                        .player(player)
                        .renderColor(config.highlightFriendsColor())
                        .renderNameLocation(config.friendsPlayerNameLocation())
                        .renderOutline(config.friendsPlayerOutline())
                        .renderMinimap(config.friendsPlayerMinimapAnimation())
                        .renderTile(config.friendsPlayerTile())
                        .renderHull(config.friendsPlayerHull())
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
