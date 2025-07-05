package com.playerindicatorextended.PlayerRender;

import com.playerindicatorextended.enums.NameLocation;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.clan.*;
import net.runelite.client.game.ChatIconManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;

import static net.runelite.api.FriendsChatRank.UNRANKED;

@Singleton
@Slf4j
public class PlayerSceneOverlay extends Overlay
{
    private final Client client;
    private final PlayerRenderPropertiesService playerRenderPropertiesService;
    private final PlayerRenderHelper renderHelper;
    private final ChatIconManager chatIconManager;

    @Inject
    public PlayerSceneOverlay(Client client, PlayerRenderPropertiesService renderDecisionService, PlayerRenderHelper renderHelper, ChatIconManager  chatIconManager)
    {
        this.client = client;
        this.playerRenderPropertiesService = renderDecisionService;
        this.renderHelper = renderHelper;
        this.chatIconManager = chatIconManager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(3);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        for (PlayerRenderProperties decision : playerRenderPropertiesService.getRenderDecisions())
        {
            Player player = decision.getPlayer();
            if (player == null)
                continue;

            Color color = decision.getRenderColor();

            // Core rendering logic
            if (decision.isRenderOutline())
                renderHelper.drawOutline(player, color);

            if (decision.isRenderHull())
                renderHelper.drawHull(g, player, color);

            if (decision.isRenderTile())
                renderHelper.drawTile(g, player, color);


            if (decision.getRenderLabelLocation() != NameLocation.DISABLED && decision.getRenderLabel() != null)
                renderHelper.drawLabel(g, player, decision.getRenderLabel(), decision.getRenderLabelLocation(), color);

            BufferedImage rankImage = null;
            if (decision.isRenderClanChatRank() && getClanTitle(player) != null && chatIconManager.getRankImage(getClanTitle(player)) != null)
            {
                rankImage = chatIconManager.getRankImage(getClanTitle(player));
            }else if (decision.isRenderFriendsChatRank() && getFriendsChatRank(player) != null && getFriendsChatRank(player) != UNRANKED){
                rankImage = chatIconManager.getRankImage(getFriendsChatRank(player));
            }


            if (decision.getRenderNameLocation() != NameLocation.DISABLED)
                renderHelper.drawName(g, player, player.getName(), decision.getRenderNameLocation(), color, rankImage);
        }

        return null;
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
