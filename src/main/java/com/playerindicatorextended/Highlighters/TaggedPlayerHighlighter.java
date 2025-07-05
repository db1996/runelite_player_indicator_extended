package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.enums.HighlighterType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.WorldView;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
@Slf4j
public class TaggedPlayerHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;

    @Getter
    private final Set<String> taggedNames;


    @Inject
    public TaggedPlayerHighlighter(Client client, PlayerIndicatorExtendedConfig config)
    {
        this.client = client;
        this.config = config;
        this.taggedNames = new HashSet<>();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged e)
    {
        log.info("onConfigChanged");
        if(!"playerindicatorextended".equals(e.getGroup()))
            return;

        this.taggedNames.clear();

        String saved = config.taggedPlayersList();
        if (saved != null)
        {
            for (String name : Text.fromCSV(saved))
            {
                this.taggedNames.add(name.trim());
            }
        }
    }


    @Override
    public List<PlayerRenderProperties> getRenderDecisions()
    {
        if(!config.highlightTagged()){
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
            String playerName = player.getName().toLowerCase();

            if (taggedNames.contains(playerName))
            {
                PlayerRenderProperties decision = PlayerRenderProperties.builder()
                        .player(player)
                        .renderColor(config.highlightTaggedColor())
                        .renderNameLocation(config.taggedPlayerNameLocation())
                        .renderOutline(config.taggedPlayerOutline())
                        .renderMinimap(config.taggedPlayerMinimapAnimation())
                        .renderTile(config.taggedPlayerTile())
                        .renderHull(config.taggedPlayerHull())
                        .priority(HighlighterType.TAGGED.getPriority())
                        .renderClanChatRank(config.clanChatRank())
                        .renderFriendsChatRank(config.friendsChatRank())
                        .build();

                result.add(decision);
            }
        }

        return result;
    }

    public void tagPlayer(String name)
    {
        taggedNames.add(name.toLowerCase());
    }

    public void untagPlayer(String name)
    {
        taggedNames.remove(name.toLowerCase());
    }

    public boolean isTagged(String name)
    {
        return taggedNames.contains(name.toLowerCase());
    }
}
