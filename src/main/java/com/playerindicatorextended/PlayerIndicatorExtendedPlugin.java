package com.playerindicatorextended;

import javax.inject.Inject;

import com.google.inject.Provides;
import com.playerindicatorextended.Highlighters.OwnPlayerHighlighter;
import com.playerindicatorextended.PlayerRender.PlayerMinimapOverlay;
import com.playerindicatorextended.PlayerRender.PlayerSceneOverlay;
import com.playerindicatorextended.PlayerRender.PlayerRenderPropertiesService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name="Player Indicator Extended"
)
public class PlayerIndicatorExtendedPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private OverlayManager overlayManager;
    @Inject private PlayerRenderPropertiesService renderDecisionService;
    @Inject private PlayerSceneOverlay playerSceneOverlay;
    @Inject private PlayerMinimapOverlay  playerMinimapOverlay;
    @Inject private OwnPlayerHighlighter ownPlayerHighlighter;

    @Override
    protected void startUp()
    {
        log.info("PlayerIndicatorExtendedPlugin started");
        renderDecisionService.registerHighlighter(ownPlayerHighlighter);
        overlayManager.add(playerSceneOverlay);
        overlayManager.add(playerMinimapOverlay);
    }

    @Override
    protected void shutDown()
    {
        log.info("PlayerIndicatorExtendedPlugin stopped");
        overlayManager.remove(playerSceneOverlay);
        overlayManager.remove(playerMinimapOverlay);
    }

    @Provides
    PlayerIndicatorExtendedConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PlayerIndicatorExtendedConfig.class);
    }
}
