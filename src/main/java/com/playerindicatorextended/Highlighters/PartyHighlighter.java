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
import net.runelite.client.party.PartyService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class PartyHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;
    private final PartyService partyService;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Inject
    public PartyHighlighter(Client client, PlayerIndicatorExtendedConfig config, PartyService partyService, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.partyService = partyService;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.PARTY;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if(config.highlightParty() == HighlightSetting.DISABLED){
            return null;
        }

        if (player == null || player.getName() == null) {
            return null;
        }

        if(config.highlightTagged() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
            return null;
        }

        if (!partyService.isInParty()) {
            return null;
        }

        if (partyService.getMemberByDisplayName(player.getName()) == null) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightPartyColor())
                .renderNameLocation(config.partyPlayerNameLocation())
                .renderOutline(config.partyPlayerOutline())
                .renderMinimap(config.partyPlayerMinimapAnimation())
                .renderTile(config.partyPlayerTile())
                .renderHull(config.partyPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .build();
    }

}
