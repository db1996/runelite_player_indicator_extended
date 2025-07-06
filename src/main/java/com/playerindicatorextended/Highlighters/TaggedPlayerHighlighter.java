package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.PlayerRender.PlayerRenderPropertiesService;
import com.playerindicatorextended.enums.HighlightSetting;
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
    private final PlayerRenderPropertiesService playerRenderPropertiesService;

    @Getter
    private final Set<String> taggedNames;


    @Inject
    public TaggedPlayerHighlighter(Client client, PlayerIndicatorExtendedConfig config, PlayerRenderPropertiesService playerRenderPropertiesService)
    {
        this.client = client;
        this.config = config;
        this.playerRenderPropertiesService = playerRenderPropertiesService;
        this.taggedNames = new HashSet<>();
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.TAGGED;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
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
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if(config.highlightTagged() == HighlightSetting.DISABLED){
            return null;
        }

        if (player == null || player.getName() == null) {
            return null;
        }

        if(config.highlightTagged() == HighlightSetting.PVP && !playerRenderPropertiesService.isPvp(client)){
            return null;
        }

        String playerName = player.getName().toLowerCase();

        if (!taggedNames.contains(playerName)) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightTaggedColor())
                .renderNameLocation(config.taggedPlayerNameLocation())
                .renderOutline(config.taggedPlayerOutline())
                .renderMinimap(config.taggedPlayerMinimapAnimation())
                .renderTile(config.taggedPlayerTile())
                .renderHull(config.taggedPlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .renderCombatLevel(config.taggedPlayerCombatLevel())
                .build();
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
