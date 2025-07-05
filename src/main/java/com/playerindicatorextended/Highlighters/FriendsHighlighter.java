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
