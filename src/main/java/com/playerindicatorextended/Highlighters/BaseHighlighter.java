package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.enums.FontEnum;
import com.playerindicatorextended.enums.HighlighterType;
import com.playerindicatorextended.enums.MinimapAnimation;
import com.playerindicatorextended.enums.NameLocation;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.clan.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.ChatIconManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import javax.inject.Provider;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static net.runelite.api.FriendsChatRank.UNRANKED;

@Slf4j
public abstract class BaseHighlighter
{
    public abstract HighlighterType getType();

    protected final Client client;
    protected final PlayerIndicatorExtendedConfig config;
    protected final ModelOutlineRenderer modelOutlineRenderer;
    protected final Provider<MenuManager>  menuManagerProvider;
    protected final ChatIconManager chatIconManager;

    private static final int SAME_LOCATION_OFFSET=40;

    @Inject
    public BaseHighlighter(
        Client client,
        PlayerIndicatorExtendedConfig config,
        ModelOutlineRenderer modelOutlineRenderer,
        Provider<MenuManager> menuManagerProvider,
        ChatIconManager chatIconManager
    ) {
        this.client = client;
        this.config = config;
        this.modelOutlineRenderer = modelOutlineRenderer;
        this.menuManagerProvider = menuManagerProvider;
        this.chatIconManager = chatIconManager;
    }

    public abstract void renderNormal(Graphics2D g);
    public abstract void renderMinimap(Graphics2D g);

    protected void drawTile(Graphics2D g, Player p, Color color)
    {
        LocalPoint lp= p.getLocalLocation();
        if(lp==null)return;

        Polygon poly= Perspective.getCanvasTilePoly(client, lp);
        if(poly==null)return;

        Stroke old= g.getStroke();
        g.setStroke(new BasicStroke(config.tileThickness()));
        g.setColor(color);
        g.draw(poly);
        g.setStroke(old);
    }

    protected void drawOutline(Player p, Color color)
    {
        if(p.getModel()==null)return;
        modelOutlineRenderer.drawOutline(
                p,
                config.outlineThickness(),
                color,
                255
        );
    }

    protected void drawHull(Graphics2D g, Player p, Color color)
    {
        Shape hull= p.getConvexHull();
        if(hull==null)return;
        Stroke old= g.getStroke();
        g.setStroke(new BasicStroke(config.hullThickness()));
        g.setColor(color);
        g.draw(hull);
        g.setStroke(old);
    }

    protected void drawName(Graphics2D g, Player p, String nameText, NameLocation nameLoc, Color nameColor)
    {
        if (nameLoc != NameLocation.DISABLED)
            drawText(g, p, nameText, nameLoc, getNameFont(), nameColor, 0);
    }

    protected void drawLabel(Graphics2D g, Player p, String labelText, NameLocation labelLoc, Color labelColor)
    {
        if (labelLoc != NameLocation.DISABLED)
            drawText(g, p, labelText, labelLoc, getLabelFont(), labelColor, 0);
    }

    private void drawText(
        Graphics2D g,
        Player p,
        String text,
        NameLocation loc,
        Font font,
        Color color,
        int offsetY)
    {
        if(loc== NameLocation.DISABLED)
            return;
        if(text==null||text.isEmpty())
            return;

        int base;
        switch(loc)
        {
            case ABOVE_HEAD:
                base = p.getLogicalHeight()+60;
                break;
            case CENTER_OF_MODEL:
                base = p.getLogicalHeight()/2;
                break;
            case UNDER_MODEL:
                base = 0;
                break;
            default:
                return;
        }

        Point pt = p.getCanvasTextLocation(g,text,base);
        if(pt==null)
            return;

        int dx= pt.getX();
        int dy= pt.getY()+ offsetY;
        if(loc== NameLocation.UNDER_MODEL)
        {
            dy+=20;
        }

        g.setColor(new Color(0,0,0,130));
        g.drawString(text,dx+1,dy+1);

        g.setColor(color);
        g.drawString(text,dx,dy);
    }

    // Minimap
    protected void drawMinimapDot(Graphics2D g, Player p, Color color, MinimapAnimation anim)
    {
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

        switch (anim)
        {
            case STATIC:
                fillCircle(g, x, y, baseSize, color);
                break;

            case PULSE:
            {
                double scale = 1 + 0.5 * Math.sin(progress * 2 * Math.PI);
                int radius = (int)(baseSize * scale);
                fillCircle(g, x, y, radius, color);
                break;
            }

            case BLINK:
            {
                if (progress < 0.5)
                {
                    fillCircle(g, x, y, baseSize, color);
                }
                break;
            }

            case SONAR:
            {
                final int maxGrowth = 20; // max radius increase independent of base size
                int radius = baseSize + (int)(progress * maxGrowth);
                drawCircle(g, x, y, radius, color);
                break;
            }

            case WAVE:
            {
                double wave = Math.sin(progress * 2 * Math.PI);
                int radius = (int)(baseSize + wave * baseSize);
                if (radius < 2) radius = 2;
                fillCircle(g, x, y, radius, color);
                break;
            }

            default:
                break;
        }
    }

    public void fillCircle(Graphics2D g,int cx,int cy,int size,Color c)
    {
        g.setColor(c);
        g.fillOval(cx-size/2, cy-size/2, size,size);
    }
    public void drawCircle(Graphics2D g,int cx,int cy,int size,Color c)
    {
        g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),120));
        g.drawOval(cx-size/2, cy-size/2, size,size);
    }

    public Font getNameFont()
    {
        return buildFont(
                config.nameFont() ,
                config.nameBold(),
                config.nameItalic(),
                config.nameUnderline()
        );
    }

    // Label
    public Font getLabelFont()
    {
        return buildFont(
                config.labelFont(),
                config.labelBold(),
                config.labelItalic(),
                config.labelUnderline()
        );
    }

    private Font buildFont(FontEnum f, boolean bold, boolean italic, boolean underline)
    {
        int style= Font.PLAIN;
        if(bold && italic) style= Font.BOLD|Font.ITALIC;
        else if(bold) style= Font.BOLD;
        else if(italic) style= Font.ITALIC;

        Font base = new Font(f.getId(), style, 12);

        if(underline)
        {
            Map<TextAttribute,Object> map=new HashMap<>();
            map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            base= base.deriveFont(map);
        }
        return base;
    }
    protected void renderPlayer(Graphics2D g, Player player, Color color,
        boolean drawOutline, boolean drawHull, boolean drawTile,
        boolean drawName, String nameTxt, NameLocation nameLoc,
        boolean drawLabel, String labelTxt, NameLocation labelLoc,
        boolean drawClanTitle, boolean drawFriendsChatChannel)
    {
        if (player == null)
            return;

        if (drawOutline)
            drawOutline(player, color);

        if (drawHull)
            drawHull(g, player, color);

        if (drawTile)
            drawTile(g, player, color);

        if (drawName)
            drawName(g, player, nameTxt, nameLoc, color);
        
        BufferedImage rankImage = null;
        if (drawClanTitle ){

            if (getFriendsChatRank(player) != null && drawFriendsChatChannel)
            {
                if (getFriendsChatRank(player) != UNRANKED)
                {
                    rankImage = chatIconManager.getRankImage(getFriendsChatRank(player));
                }
            }
            else if (getClanTitle(player) != null && drawClanTitle)
            {
                rankImage = chatIconManager.getRankImage(getClanTitle(player));
            }
        }

//        if (rankImage != null)
//        {
//            final int imageWidth = rankImage.getWidth();
//            final int imageTextMargin;
//            final int imageNegativeMargin;
//
//            if (drawPlayerNamesConfig == nameLoc.MODEL_RIGHT)
//            {
//                imageTextMargin = imageWidth;
//                imageNegativeMargin = 0;
//            }
//            else
//            {
//                imageTextMargin = imageWidth / 2;
//                imageNegativeMargin = imageWidth / 2;
//            }
//
//            final int textHeight = graphics.getFontMetrics().getHeight() - graphics.getFontMetrics().getMaxDescent();
//            final Point imageLocation = new Point(textLocation.getX() - imageNegativeMargin - 1, textLocation.getY() - textHeight / 2 - rankImage.getHeight() / 2);
//            OverlayUtil.renderImageLocation(graphics, imageLocation, rankImage);
//
//            // move text
//            textLocation = new Point(textLocation.getX() + imageTextMargin, textLocation.getY());
//        }

        if (drawLabel)
            drawLabel(g, player, labelTxt, labelLoc, color);
    }

    protected void renderMinimapDot(Graphics2D g, Player player, Color color, MinimapAnimation animated)
    {
        if (player != null)
            drawMinimapDot(g, player, color, animated);
    }

    private ClanTitle getClanTitle(Player player)
    {
        ClanChannel clanChannel = client.getClanChannel();
        ClanSettings clanSettings = client.getClanSettings();
        if (clanChannel == null || clanSettings == null)
        {
            return null;
        }

        ClanChannelMember member = clanChannel.findMember(player.getName());
        if (member == null)
        {
            return null;
        }

        ClanRank rank = member.getRank();
        return clanSettings.titleForRank(rank);
    }

    private FriendsChatRank getFriendsChatRank(Player player)
    {
        final FriendsChatManager friendsChatManager = client.getFriendsChatManager();
        if (friendsChatManager == null)
        {
            return UNRANKED;
        }

        FriendsChatMember friendsChatMember = friendsChatManager.findByName(Text.removeTags(player.getName()));
        return friendsChatMember != null ? friendsChatMember.getRank() : UNRANKED;
    }
}
