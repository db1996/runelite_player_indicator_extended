package com.playerindicatorextended;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.playerindicatorextended.Highlighters.*;
import com.playerindicatorextended.PlayerRender.PlayerMinimapOverlay;
import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;
import com.playerindicatorextended.PlayerRender.PlayerSceneOverlay;
import com.playerindicatorextended.PlayerRender.PlayerRenderPropertiesService;
import com.playerindicatorextended.enums.TagMenuOption;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;

import java.awt.*;
import java.util.Objects;

@Slf4j
@PluginDescriptor(
        name="Player Indicator Extended"
)
public class PlayerIndicatorExtendedPlugin extends Plugin
{
    private final String MENU_ITEM = "Tag";
    private final String MENU_ITEM_UNTAG = "Untag";

    @Inject private Client client;
    @Inject private PlayerIndicatorExtendedConfig config;
    @Inject private OverlayManager overlayManager;
    @Inject private PlayerRenderPropertiesService renderDecisionService;
    @Inject private PlayerSceneOverlay playerSceneOverlay;
    @Inject private PlayerMinimapOverlay  playerMinimapOverlay;
    @Inject private ConfigManager configManager;
    @Inject private EventBus eventBus;
    @Inject private Provider<MenuManager> menuManager;


    @Inject private OwnPlayerHighlighter ownPlayerHighlighter;
    @Inject private PartyHighlighter  partyHighlighter;
    @Inject private FriendsHighlighter  friendsHighlighter;
    @Inject private FriendsChatHighlighter friendsChatHighlighter;
    @Inject private TeamHighlighter teamHighlighter;
    @Inject private ClanMemberHighlighter clanMemberHighlighter;
    @Inject private AttackableHighlighter attackableHighlighter;
    @Inject private TaggedPlayerHighlighter  taggedPlayerHighlighter;
    @Inject private IgnoreHighlighter ignoreHighlighter;
    @Inject private OthersHighlighter othersHighlighter;

    @Override
    protected void startUp()
    {
        log.info("PlayerIndicatorExtendedPlugin started");
        renderDecisionService.registerHighlighter(ownPlayerHighlighter);
        renderDecisionService.registerHighlighter(partyHighlighter);
        renderDecisionService.registerHighlighter(friendsHighlighter);
        renderDecisionService.registerHighlighter(friendsChatHighlighter);
        renderDecisionService.registerHighlighter(teamHighlighter);
        renderDecisionService.registerHighlighter(clanMemberHighlighter);
        renderDecisionService.registerHighlighter(attackableHighlighter);
        renderDecisionService.registerHighlighter(taggedPlayerHighlighter);
        renderDecisionService.registerHighlighter(ignoreHighlighter);
        renderDecisionService.registerHighlighter(othersHighlighter);

        eventBus.register(taggedPlayerHighlighter);

        overlayManager.add(playerSceneOverlay);
        overlayManager.add(playerMinimapOverlay);

    }

    @Override
    protected void shutDown()
    {
        log.info("PlayerIndicatorExtendedPlugin stopped");
        eventBus.unregister(taggedPlayerHighlighter);

        overlayManager.remove(playerSceneOverlay);
        overlayManager.remove(playerMinimapOverlay);
    }

    @Provides
    PlayerIndicatorExtendedConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PlayerIndicatorExtendedConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event){
        if(Objects.equals(event.getGroup(), "playerindicatorextended")){
            log.info("PlayerIndicatorExtendedPlugin config changed");
            renderDecisionService.resortHighlighters();
            log.info("{}", renderDecisionService.getHighlighters());
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event)
    {
        Player player = event.getMenuEntry().getPlayer();
        if (player == null)
        {
            return;
        }

        String target = player.getName();
        if(target == null || target.isEmpty()){
            return;
        }

        if (event.getMenuAction() == MenuAction.RUNELITE_PLAYER && event.getMenuOption().equals(MENU_ITEM))
        {
            taggedPlayerHighlighter.tagPlayer(target);
            updateTaggedConfig();
        }

        if (event.getMenuAction() == MenuAction.RUNELITE_PLAYER && event.getMenuOption().equals(MENU_ITEM_UNTAG))
        {
            taggedPlayerHighlighter.untagPlayer(target);
            updateTaggedConfig();
        }
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        if (config.tagMenuOption() != TagMenuOption.RIGHT_CLICK && config.tagMenuOption() != TagMenuOption.SHIFT_RIGHT_CLICK)
            return;

        if(config.tagMenuOption() == TagMenuOption.SHIFT_RIGHT_CLICK){
            if (!client.isKeyPressed(KeyCode.KC_SHIFT))
            {
                return;
            }
        }
        MenuEntry menuEntry = event.getMenuEntry();

        if(menuEntry == null)
            return;

        Player player = menuEntry.getPlayer();
        if(player == null || player.getName() == null)
            return;

        String playerName = player.getName();
        String originalTarget = event.getTarget();

        String coloredPlayerNameInMenu = playerName;

        Color customRenderColor = null;
        if(config.colorizePlayerMenu()){
            for (PlayerRenderProperties prop : renderDecisionService.getRenderDecisions())
            {
                Player propPlayer = prop.getPlayer();
                if (propPlayer != null && playerName.equals(propPlayer.getName()))
                {
                    customRenderColor = prop.getRenderColor();
                    break;
                }
            }
        }

        if (customRenderColor != null)
            coloredPlayerNameInMenu = ColorUtil.wrapWithColorTag(playerName, customRenderColor);


        String newTarget;
        int nameIndexInTarget = originalTarget.indexOf(playerName);

        if (nameIndexInTarget != -1) {
            newTarget = originalTarget.substring(0, nameIndexInTarget) +
                    coloredPlayerNameInMenu +
                    originalTarget.substring(nameIndexInTarget + playerName.length());
        } else {
            newTarget = originalTarget;
        }

        menuEntry.setTarget(newTarget);

        if (event.getType() != MenuAction.PLAYER_THIRD_OPTION.getId())
            return;

        boolean isTagged = taggedPlayerHighlighter.isTagged(player.getName());
        String option = isTagged ? MENU_ITEM_UNTAG : MENU_ITEM;

        client.getMenu().createMenuEntry(-2)
                .setOption(option)
                .setTarget(newTarget)
                .setType(MenuAction.RUNELITE_PLAYER)
                .setIdentifier(event.getIdentifier());
    }

    private void updateTaggedConfig()
    {
        String joined = String.join(",", taggedPlayerHighlighter.getTaggedNames());
        configManager.setConfiguration("playerindicatorextended", "taggedPlayersList", joined);
    }
}
