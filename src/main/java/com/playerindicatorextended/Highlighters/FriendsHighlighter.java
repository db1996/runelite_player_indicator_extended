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
import java.util.*;

@Singleton
@Slf4j
public class FriendsHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public FriendsHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.FRIENDS;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
    }

    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(config.highlightFriends() == HighlightSetting.DISABLED){
            return Collections.emptyList();
        }

        if(config.highlightFriends() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
            return Collections.emptyList();
        }

        Player localPlayer = client.getLocalPlayer();
        if(localPlayer == null){
            log.info("Player not found");
            return Collections.emptyList();
        }

        WorldView worldView = client.getTopLevelWorldView();
        if(worldView == null){
            return Collections.emptyList();
        }

        List<PlayerRenderProperties> result = new ArrayList<>();
        for (Player player : worldView.players()) {
            if (player == null || player.getName() == null || Objects.equals(player.getName(), localPlayer.getName()))
            {
                continue;
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
                        .priority(this.getPriority())
                        .renderClanChatRank(config.clanChatRank())
                        .renderFriendsChatRank(config.friendsChatRank())
                        .build();
                result.add(decision);
            }

        }

        return result;
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if (config.highlightFriends() == HighlightSetting.DISABLED) {
            return null;
        }

        if (config.highlightFriends() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)) {
            return null;
        }

        if (player == null || player.getName() == null || Objects.equals(player.getName(), localPlayer.getName())) {
            return null;
        }

        if (!player.isFriend()) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightFriendsColor())
                .renderNameLocation(config.friendsPlayerNameLocation())
                .renderOutline(config.friendsPlayerOutline())
                .renderMinimap(config.friendsPlayerMinimapAnimation())
                .renderTile(config.friendsPlayerTile())
                .renderHull(config.friendsPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .build();
    }

}
