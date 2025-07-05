package com.playerindicatorextended;

import com.google.inject.Provides;
import com.playerindicatorextended.Highlighters.BaseHighlighter;
import com.playerindicatorextended.Highlighters.OwnPlayerHighlighter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name="Player Indicator Extended",
        description="Plugin with multiple highlight categories + Tag Player",
        tags={"pvp","combat","tools"},
        enabledByDefault=false
)
public class PlayerIndicatorExtendedPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private Client client;
    @Inject
    private ClientThread clientThread;
    @Inject
    private EventBus eventBus;
    @Inject
    private PlayerIndicatorExtendedConfig config;

    private boolean overlaysAdded=false;
    private Overlay highlightOverlayUnder;
    private Overlay highlightOverlayAbove;

//  Highlighters
    private final List<BaseHighlighter> highlighters = new ArrayList<>();

    @Inject
    private OwnPlayerHighlighter ownPlayerHighlighter;

    @Provides
    PlayerIndicatorExtendedConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PlayerIndicatorExtendedConfig.class);
    }



    private void registerTrackers(){
        for (BaseHighlighter h : highlighters)
        {
            eventBus.register(h);
        }
    }

    private void unRegisterTrackers(){
        for (BaseHighlighter h : highlighters)
        {
            eventBus.register(h);
        }
    }

    @Override
    protected void startUp()
    {
        log.info("Player indicator extended plugin started");
        registerTrackers();
        highlighters.add(ownPlayerHighlighter);

        highlightOverlayUnder = new Overlay()
        {
            @Override
            public Dimension render(Graphics2D graphics)
            {
                if (!isAnyHighlightEnabled()) return null;
                for (BaseHighlighter h : highlighters)
                {
                    h.renderNormal(graphics);
                }
                return null;
            }
        };
        highlightOverlayUnder.setPosition(OverlayPosition.DYNAMIC);
        highlightOverlayUnder.setLayer(OverlayLayer.UNDER_WIDGETS);
        highlightOverlayUnder.setPriority(OverlayPriority.LOW);

        highlightOverlayAbove = new Overlay()
        {
            @Override
            public Dimension render(Graphics2D graphics)
            {
                if (!isAnyHighlightEnabled()) return null;
                for (BaseHighlighter h : highlighters)
                {
                    h.renderMinimap(graphics);
                }
                return null;
            }
        };
        highlightOverlayAbove.setPosition(OverlayPosition.DYNAMIC);
        highlightOverlayAbove.setLayer(OverlayLayer.ABOVE_WIDGETS);
        highlightOverlayAbove.setPriority(OverlayPriority.HIGHEST);

        updateOverlayState();
    }

    @Override
    protected void shutDown()
    {
        log.info("Player Indicator Extended stopped");
        unRegisterTrackers();
        if (overlaysAdded)
        {
            overlayManager.remove(highlightOverlayUnder);
            overlayManager.remove(highlightOverlayAbove);
            overlaysAdded = false;
        }

        highlighters.clear();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged e)
    {
        if(!"playerindicatorsextended".equals(e.getGroup()))
            return;

        clientThread.invokeLater(this::updateOverlayState);
    }

    private void updateOverlayState()
    {
        boolean any = isAnyHighlightEnabled();
        if(any && !overlaysAdded)
        {
            overlayManager.add(highlightOverlayUnder);
            overlayManager.add(highlightOverlayAbove);
            overlaysAdded=true;
        }
        else if(!any && overlaysAdded)
        {
            overlayManager.remove(highlightOverlayUnder);
            overlayManager.remove(highlightOverlayAbove);
            overlaysAdded=false;
        }
    }

    private boolean isAnyHighlightEnabled()
    {
        return config.highlightOwnPlayer()
                || config.highlightParty()
                || config.highlightFriends()
                || config.highlightFriendsChat()
                || config.highlightTeam()
                || config.highlightClan()
                || config.highlightAttackable()
                || config.highlightIgnore()
                || config.highlightTagged()
                || config.highlightOthers();
    }
}
