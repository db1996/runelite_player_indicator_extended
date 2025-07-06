package com.playerindicatorextended.Highlighters;

import com.playerindicatorextended.PlayerIndicatorExtendedConfig;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.enums.HighlighterType;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.WorldType;
import net.runelite.api.WorldView;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class AttackableHighlighter extends BaseHighlighter
{
    private final Client client;
    private final PlayerIndicatorExtendedConfig config;

    @Inject
    public AttackableHighlighter(Client client, PlayerIndicatorExtendedConfig config)
    {
        this.client = client;
        this.config = config;
    }

    @Override
    public HighlighterType getHighlighterType() {
        return HighlighterType.ATTACKABLE;
    }

    @Override
    public int getPriority() {
        return this.getHighlighterType().getPriority();
    }

    @Override
    public PlayerRenderProperties getRenderProperties(Player player, Player localPlayer)
    {
        if (!config.highlightAttackable()) {
            return null;
        }

        if (player == null || player.getName() == null || player.getName().equals(localPlayer.getName())) {
            return null;
        }

        if (!canAttack(localPlayer, player)) {
            return null;
        }

        return PlayerRenderProperties.builder()
                .player(player)
                .renderColor(config.highlightAttackableColor())
                .renderNameLocation(config.attackablePlayerNameLocation())
                .renderOutline(config.attackablePlayerOutline())
                .renderMinimap(config.attackablePlayerMinimapAnimation())
                .renderTile(config.attackablePlayerTile())
                .renderHull(config.attackablePlayerHull())
                .priority(this.getPriority())
                .renderClanChatRank(config.clanChatRank())
                .renderFriendsChatRank(config.friendsChatRank())
                .renderCombatLevel(config.attackablePlayerCombatLevel())
                .build();
    }


    public boolean canAttack(Player player1, Player player2) {
        if (player1 == null || player2 == null || player1.equals(player2)) {
            return false; // Cannot attack self or null players
        }

        // 1. Check if both players are in a PvP-enabled environment
        if (isNotInPvPZone(player1.getWorldLocation()) && isNotInPvPWorld()) {
            return false;
        }
        if (isNotInPvPZone(player2.getWorldLocation()) && isNotInPvPWorld()) {
            return false;
        }

        boolean player1InWild = isInWilderness(player1.getWorldLocation());
        boolean player2InWild = isInWilderness(player2.getWorldLocation());

        if (player1InWild != player2InWild) {
            return false;
        }

        // 2. Wilderness Rules (if in Wilderness)
        if (player1InWild) {
            int wildLevel1 = getWildernessLevel(player1.getWorldLocation());
            int wildLevel2 = getWildernessLevel(player2.getWorldLocation());

            int effectiveWildLevel = Math.max(wildLevel1, wildLevel2);

            int combatLevel1 = player1.getCombatLevel();
            int combatLevel2 = player2.getCombatLevel();

            if (combatLevel1 < combatLevel2 - effectiveWildLevel ||
                    combatLevel1 > combatLevel2 + effectiveWildLevel) {
                return false; // Out of combat level range
            }

            if (isWildernessSafeZone(player1.getWorldLocation()) || isWildernessSafeZone(player2.getWorldLocation())) {
                return false;
            }
        }

        return true;
    }


    /**
     * Helper to determine if a WorldPoint is within the Wilderness.
     * This is an approximation. The actual Wilderness boundary is complex.
     * A common way is to check the Y-coordinate.
     * Wilderness starts around Y=3520 (south of Edgeville Dungeon entrance).
     */
    private boolean isInWilderness(WorldPoint wp) {
        return wp.getY() >= 3520 && wp.getY() <= 4000;
    }

    /**
     * Calculates the Wilderness level based on WorldPoint.
     * This is a common approximation. Wilderness levels increment roughly every 8 tiles north from the ditch.
     */
    private int getWildernessLevel(WorldPoint wp) {
        // The wilderness ditch is around y=3520 (varies slightly by x-coord).
        // Wilderness level 1 starts around y=3520 to y=3583 (approximate)
        // Each ~8 tiles north increases the level by 1.
        if (!isInWilderness(wp)) {
            return 0;
        }

        int y = wp.getY();
        // Base Y-coordinate for Wilderness level 1 (approximately)
        final int WILD_LEVEL_BASE_Y = 3520; // Southmost wilderness
        final int TILES_PER_WILD_LEVEL = 8; // Roughly 8 tiles per level

        if (y < WILD_LEVEL_BASE_Y) {
            return 0;
        }

        return (y - WILD_LEVEL_BASE_Y) / TILES_PER_WILD_LEVEL + 1;
    }

    /**
     * Checks if the current world is a PvP world.
     */
    private boolean isNotInPvPWorld() {
        return !client.getWorldType().contains(WorldType.PVP);
    }

    /**
     * Checks if the current world is a Deadman Mode world.
     */
    private boolean isInDeadmanWorld() {
        return client.getWorldType().contains(WorldType.DEADMAN);
    }

    /**
     * Determines if a given WorldPoint is within a PvP-enabled zone (Wilderness, certain minigames, etc.).
     * This is a simplified check.
     */
    private boolean isNotInPvPZone(WorldPoint wp) {
        return !isInWilderness(wp) && isNotInPvPWorld();
    }


    private boolean isWildernessSafeZone(WorldPoint wp) {
        // Ferox Enclave roughly
        if (wp.getX() >= 3120 && wp.getX() <= 3160 && wp.getY() >= 3618 && wp.getY() <= 3640) {
            return true;
        }
        // Add other wilderness safe zones (e.g., Resource Area bank)
        return false;
    }
}
