package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.enums.HighlighterType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.game.ChatIconManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.awt.*;

@Slf4j
@Singleton
public class OwnPlayerHighlighter extends BaseHighlighter {

    @Inject
    public OwnPlayerHighlighter(Client client, PlayerIndicatorExtendedConfig config, ModelOutlineRenderer modelOutlineRenderer, Provider<MenuManager> menuManagerProvider, ChatIconManager chatIconManager) {
        super(client, config, modelOutlineRenderer, menuManagerProvider, chatIconManager);
    }

    @Override
    public HighlighterType getType() {
        return HighlighterType.OWN_PLAYER;
    }

    @Override
    public void renderNormal(Graphics2D g) {
        if (!config.highlightOwnPlayer())
            return;

        Player ownPlayer = client.getLocalPlayer();
        if (ownPlayer == null)
            return;

        Color c = config.highlightOwnPlayerColor();

        renderPlayer(
            g,
            ownPlayer,
            c,
            config.ownPlayerPlayerOutline(),
            config.ownPlayerPlayerHull(),
            config.ownPlayerPlayerTile(),
            true,
            ownPlayer.getName() + " (" + ownPlayer.getCombatLevel() + ")",
            config.ownPlayerPlayerNameLocation(),
            true,
            "Local Player",
            config.ownPlayerPlayerLabelLocation(),false, false
        );
    }

    @Override
    public void renderMinimap(Graphics2D g) {
        if (!config.highlightOwnPlayer())
            return;

        renderMinimapDot(g, client.getLocalPlayer(), config.highlightOwnPlayerColor(), config.ownPlayerPlayerMinimapAnimation());
    }
}
