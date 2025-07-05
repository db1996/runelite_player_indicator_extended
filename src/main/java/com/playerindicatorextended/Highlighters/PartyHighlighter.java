package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
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

    @Inject
    public PartyHighlighter(Client client, PlayerIndicatorExtendedConfig config, PartyService partyService)
    {
        this.client = client;
        this.config = config;
        this.partyService = partyService;
    }


    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(!config.highlightParty()){
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

            if (partyService.isInParty() && partyService.getMemberByDisplayName(player.getName()) != null){
                PlayerRenderProperties decision = PlayerRenderProperties.builder()
                        .player(player)
                        .renderColor(config.highlightPartyColor())
                        .renderNameLocation(config.partyPlayerNameLocation())
                        .renderOutline(config.partyPlayerOutline())
                        .renderMinimap(config.partyPlayerMinimapAnimation())
                        .renderTile(config.partyPlayerTile())
                        .renderHull(config.partyPlayerHull())
                        .priority(HighlighterType.PARTY.getPriority())
                        .renderClanChatRank(config.clanChatRank())
                        .renderFriendsChatRank(config.friendsChatRank())
                        .build();
                result.add(decision);
            }
        }

        return result;
    }
}
