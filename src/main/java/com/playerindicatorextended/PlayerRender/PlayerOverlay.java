import com.playerindicatorextended.PlayerRender.PlayerRenderDecision;
import com.playerindicatorextended.PlayerRender.PlayerRenderDecisionService;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;

public class PlayerOverlay extends Overlay
{
    private final Client client;
    private final PlayerRenderDecisionService renderDecisionService;

    @Inject
    public PlayerOverlay(Client client, PlayerRenderDecisionService renderDecisionService)
    {
        this.client = client;
        this.renderDecisionService = renderDecisionService;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        for (PlayerRenderDecision decision : renderDecisionService.getRenderDecisions().values())
        {
            Player player = decision.getPlayer();
            LocalPoint localLocation = player.getLocalLocation();

            if (decision.isRenderOutline())
            {
                OverlayUtil.renderActorOutline(graphics, player, 2, decision.getOutlineColor());
            }

            if (decision.isRenderName())
            {
                String name = player.getName();
                Point textLocation = player.getCanvasTextLocation(graphics, name, 40);
                if (textLocation != null)
                {
                    OverlayUtil.renderTextLocation(graphics, textLocation, name, decision.getNameColor());
                }
            }

            if (decision.isRenderMinimap())
            {
                Point minimapLocation = player.getMinimapLocation();
                if (minimapLocation != null)
                {
                    graphics.setColor(decision.getMinimapColor());
                    graphics.fillOval(minimapLocation.x, minimapLocation.y, 6, 6);
                }
            }
        }

        return null;
    }
}
