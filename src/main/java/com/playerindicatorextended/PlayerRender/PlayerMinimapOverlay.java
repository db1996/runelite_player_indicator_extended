package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.enums.MinimapAnimation;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

@Singleton
@Slf4j
public class PlayerMinimapOverlay extends Overlay
{
    private final Client client;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;
    private final PlayerRenderHelper renderHelper;

    @Inject
    public PlayerMinimapOverlay(
            Client client,
            PlayerRenderPropertiesService renderDecisionService,
            PlayerRenderHelper renderHelper
    ) {
        this.client = client;
        this.playerRenderPropertiesService = renderDecisionService;
        this.renderHelper = renderHelper;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS); // ✅ minimap rendering
        setPriority(3);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        for (PlayerRenderProperties decision : playerRenderPropertiesService.getRenderDecisions())
        {
            Player player = decision.getPlayer();
            if (player == null) continue;

            if (decision.getRenderMinimap() != MinimapAnimation.NONE)
            {
                renderHelper.drawMinimapDot(g, player, decision.getRenderColor(), decision.getRenderMinimap());
            }
        }

        return null;
    }
}
