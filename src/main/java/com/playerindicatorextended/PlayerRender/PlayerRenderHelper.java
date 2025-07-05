package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.enums.FontEnum;
import com.playerindicatorextended.enums.MinimapAnimation;
import com.playerindicatorextended.enums.NameLocation;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.ui.overlay.components.TextComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
@Singleton
public class PlayerRenderHelper {
    private final Client client;
    private final ModelOutlineRenderer modelOutlineRenderer;
    private final PlayerIndicatorExtendedConfig config;

    @Inject
    public PlayerRenderHelper(Client client, ModelOutlineRenderer renderer, PlayerIndicatorExtendedConfig config) {
        this.client = client;
        this.modelOutlineRenderer = renderer;
        this.config = config;
    }

    protected void drawTile(Graphics2D g, Player p, Color color) {
        LocalPoint lp = p.getLocalLocation();

        if (lp == null)
            return;

        Polygon poly = Perspective.getCanvasTilePoly(client, lp);
        if (poly == null)
            return;

        Stroke old = g.getStroke();
        g.setStroke(new BasicStroke(config.tileThickness()));
        g.setColor(color);
        g.draw(poly);
        g.setStroke(old);
    }

    protected void drawOutline(Player p, Color color) {
        if (p.getModel() == null) return;
        modelOutlineRenderer.drawOutline(
                p,
                config.outlineThickness(),
                color,
                255
        );
    }

    protected void drawHull(Graphics2D g, Player p, Color color) {
        Shape hull = p.getConvexHull();
        if (hull == null)
            return;
        Stroke old = g.getStroke();
        g.setStroke(new BasicStroke(config.hullThickness()));
        g.setColor(color);
        g.draw(hull);
        g.setStroke(old);
    }

    protected void drawName(Graphics2D g, Player p, String nameText, NameLocation nameLoc, Color nameColor, BufferedImage icon) {
        if (nameLoc != NameLocation.DISABLED)
            drawText(g, p, nameText, nameLoc, nameColor, icon);
    }

    protected void drawLabel(Graphics2D g, Player p, String labelText, NameLocation labelLoc, Color labelColor) {
        if (labelLoc != NameLocation.DISABLED)
            drawText(g, p, labelText, labelLoc, labelColor, null);
    }

    private void drawText(
            Graphics2D g,
            Player p,
            String text,
            NameLocation loc,
            Color color,
            BufferedImage icon) {
        if (loc == NameLocation.DISABLED || text == null || text.isEmpty())
            return;

        int base;
        switch (loc) {
            case ABOVE_HEAD:
                base = p.getLogicalHeight() + 60;
                break;
            case CENTER_OF_MODEL:
                base = p.getLogicalHeight() / 2;
                break;
            case UNDER_MODEL:
                base = 0;
                break;
            default:
                return;
        }

        net.runelite.api.Point pt = p.getCanvasTextLocation(g, text, base);
        if (pt == null)
            return;

        int dx = pt.getX();
        int dy = pt.getY();
        if (loc == NameLocation.UNDER_MODEL) {
            dy += 20;
        }

        // Adjust text position if icon is present
        int iconWidth = icon != null ? icon.getWidth() + 3 : 0;

        // Draw icon if present
        if (icon != null) {
            int iconY = dy - icon.getHeight(); // top-align with text
            int iconX = dx - iconWidth;
            g.drawImage(icon, iconX, iconY, null);
        }

        // Draw text using RuneLite's TextComponent
        TextComponent textComponent = new TextComponent();
        textComponent.setText(text);
        textComponent.setColor(color);
        textComponent.setPosition(new java.awt.Point(dx, dy));
        textComponent.render(g);
    }

    // Minimap
    protected void drawMinimapDot(Graphics2D g, Player p, Color color, MinimapAnimation anim) {
        if (anim == MinimapAnimation.NONE)
            return;

        Point mm = p.getMinimapLocation();
        if (mm == null)
            return;

        final int baseSize = config.minimapCircleSize();

        // Speed is in milliseconds now (configurable)
        final long speed = Math.max(config.minimapAnimSpeed(), 1);

        final int x = mm.getX();
        final int y = mm.getY();

        // progress from 0.0 to 1.0, looping every 'speed' milliseconds
        double progress = (System.currentTimeMillis() % speed) / (double) speed;

        switch (anim) {
            case STATIC:
                fillCircle(g, x, y, baseSize, color);
                break;

            case PULSE: {
                double scale = 1 + 0.5 * Math.sin(progress * 2 * Math.PI);
                int radius = (int) (baseSize * scale);
                fillCircle(g, x, y, radius, color);
                break;
            }

            case BLINK: {
                if (progress < 0.5) {
                    fillCircle(g, x, y, baseSize, color);
                }
                break;
            }

            case SONAR: {
                final int maxGrowth = 20; // max radius increase independent of base size
                int radius = baseSize + (int) (progress * maxGrowth);
                drawCircle(g, x, y, radius, color);
                break;
            }

            case WAVE: {
                double wave = Math.sin(progress * 2 * Math.PI);
                int radius = (int) (baseSize + wave * baseSize);
                if (radius < 2) radius = 2;
                fillCircle(g, x, y, radius, color);
                break;
            }

            default:
                break;
        }
    }

    public void fillCircle(Graphics2D g, int cx, int cy, int size, Color c) {
        g.setColor(c);
        g.fillOval(cx - size / 2, cy - size / 2, size, size);
    }

    public void drawCircle(Graphics2D g, int cx, int cy, int size, Color c) {
        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 120));
        g.drawOval(cx - size / 2, cy - size / 2, size, size);
    }

}
