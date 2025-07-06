package com.playerindicatorextended;

import com.playerindicatorextended.enums.*;
import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("playerindicatorextended")
public interface PlayerIndicatorExtendedConfig extends Config
{
    boolean defaultTile = false;
    boolean defaultOutline = false;
    boolean defaultHull = false;
    boolean defaultCombatLevel = false;
    NameLocation defaultNamelocation = NameLocation.ABOVE_HEAD;
    MinimapAnimation defaultMinimapanimation = MinimapAnimation.STATIC;

    // ----------------------------------------------------
    // 0) Some defaults
    // ----------------------------------------------------

    @ConfigSection(
            name="Default settings",
            description="Some default settings",
            position=0
    )
    String defaultSection = "defaultSection";

    @ConfigItem(
            keyName="colorizePlayerMenu",
            name="Colorize player menu",
            description="Shows player names in their respective color when right clicking",
            section = defaultSection,
            position=0
    )
    default boolean colorizePlayerMenu()
    {
        return true;
    }

    @ConfigItem(
            keyName="friendsChatRank",
            name="Show friends chat ranks",
            description="Show the friends chat rank icons",
            section = defaultSection,
            position=1
    )
    default boolean friendsChatRank()
    {
        return true;
    }

    @ConfigItem(
            keyName="clanChatRank",
            name="Show clan chat ranks",
            description="Show the clan chat rank icons",
            section = defaultSection,
            position=2
    )
    default boolean clanChatRank()
    {
        return true;
    }

    @ConfigItem(
            keyName="tagMenuOption",
            name="Tag Menu Option",
            description="Show right click tag option on players",
            section=defaultSection,
            position = 5
    )
    default TagMenuOption tagMenuOption()
    {
        return TagMenuOption.SHIFT_RIGHT_CLICK;
    }

    @ConfigItem(
            keyName="taggedPlayersList",
            name="Tagged Player List",
            description="Comma seperated list of usernames",
            section=defaultSection,
            position = 6
    )
    default String taggedPlayersList()
    {
        return "";
    }

    // ----------------------------------------------------
    // 1) Highlight settings
    // ----------------------------------------------------

    @ConfigSection(
            name="Highlight settings",
            description="Toggle and color various types of players",
            position=1
    )
    String highlightsSection = "highLightSection";

    @ConfigItem(
            keyName="highlightOwnPlayer",
            name="Own player",
            description="Highlight the logged in player",
            section= highlightsSection,
            position=0
    )
    default HighlightSetting highlightOwnPlayer()
    {
        return HighlightSetting.DISABLED;
    }

    @ConfigItem(
            keyName="ownPlayerPlayerColor",
            name="Color",
            description="Color for ownPlayer player highlight",
            section= highlightsSection,
            position=1
    )
    default Color highlightOwnPlayerColor()
    {
        return new Color(0, 184, 212);
    }


    @ConfigItem(
            keyName="highlightParty",
            name="Party members",
            description="Highlight party members",
            section= highlightsSection,
            position=2
    )
    default HighlightSetting highlightParty()
    {
        return HighlightSetting.DISABLED;
    }

    @ConfigItem(
            keyName="highlightPartyColor",
            name="Color",
            description="Color for party members",
            section= highlightsSection,
            position=3
    )
    default Color highlightPartyColor()
    {
        return new Color(234, 123, 91);
    }

    @ConfigItem(
            keyName="highlightFriends",
            name="Friends",
            description="Highlights friends",
            section= highlightsSection,
            position=4
    )
    default HighlightSetting highlightFriends()
    {
        return HighlightSetting.ENABLED;
    }

    @ConfigItem(
            keyName="highlightFriendsColor",
            name="Color",
            description="Highlight color for friends",
            section= highlightsSection,
            position=5
    )
    default Color highlightFriendsColor()
    {
        return new Color(0, 200, 83);
    }

    @ConfigItem(
            keyName="highlightFriendsChat",
            name="Friends Chat",
            description="Highlight players in your friends chat channel",
            section= highlightsSection,
            position=6
    )
    default HighlightSetting highlightFriendsChat()
    {
        return HighlightSetting.DISABLED;
    }

    @ConfigItem(
            keyName="friendsChannelColor",
            name="Color",
            description="Friends channel highlight color",
            section= highlightsSection,
            position=7
    )
    default Color highlightFriendsChatColor()
    {
        return new Color(170, 0, 255);
    }

    @ConfigItem(
            keyName="highlightTeam",
            name="Team Members",
            description="Highlight team members",
            section= highlightsSection,
            position=8
    )
    default HighlightSetting highlightTeam()
    {
        return HighlightSetting.DISABLED;
    }

    @ConfigItem(
            keyName="highlightTeamColor",
            name="Color",
            description="Team member highlight color",
            section= highlightsSection,
            position=9
    )
    default Color highlightTeamColor()
    {
        return new Color(19, 110, 247);
    }

    @ConfigItem(
            keyName="highlightClan",
            name="Clan Members",
            description="Highlight clan members",
            section= highlightsSection,
            position=10
    )
    default HighlightSetting highlightClan()
    {
        return HighlightSetting.ENABLED;
    }

    @ConfigItem(
            keyName="highlightClanColor",
            name="Color",
            description="Clan member highlight color",
            section= highlightsSection,
            position=11
    )
    default Color highlightClanColor()
    {
        return new Color(36, 15, 171);
    }

    @ConfigItem(
            keyName="highlightAttackable",
            name="Attackable players",
            description="Highlight attackable players",
            section= highlightsSection,
            position=12
    )
    default boolean highlightAttackable()
    {
        return true;
    }

    @ConfigItem(
            keyName="highlightAttackableColor",
            name="Color",
            description="Attackable players highlight color",
            section= highlightsSection,
            position=13
    )
    default Color highlightAttackableColor()
    {
        return new Color(170,0,0);
    }

    @ConfigItem(
            keyName="highlightIgnore",
            name="Ignored players",
            description="Highlight ignored players",
            section= highlightsSection,
            position=14
    )
    default HighlightSetting highlightIgnore()
    {
        return HighlightSetting.DISABLED;
    }

    @ConfigItem(
            keyName="highlightIgnoreColor",
            name="Color",
            description="Ignored players highlight color",
            section= highlightsSection,
            position=15
    )
    default Color highlightIgnoreColor()
    {
        return new Color(0,0,0);
    }

    @ConfigItem(
            keyName="highlightTagged",
            name="Tagged players",
            description="Highlight tagged players",
            section= highlightsSection,
            position=16
    )
    default HighlightSetting highlightTagged()
    {
        return HighlightSetting.ENABLED;
    }

    @ConfigItem(
            keyName="highlightTaggedColor",
            name="Color",
            description="Ignored players highlight color",
            section= highlightsSection,
            position=17
    )
    default Color highlightTaggedColor()
    {
        return new Color(170,0,255);
    }

    @ConfigItem(
            keyName="highlightOthers",
            name="Other players",
            description="Highlight tagged players",
            section= highlightsSection,
            position=18
    )
    default HighlightSetting highlightOthers()
    {
        return HighlightSetting.PVP;
    }

    @ConfigItem(
            keyName="highlightOthersColor",
            name="Color",
            description="Ignored Others highlight color",
            section= highlightsSection,
            position=19
    )
    default Color highlightOthersColor()
    {
        return new Color(255,154,0);
    }

    // ----------------------------------------------------
    // 2) Own Player settings
    // ----------------------------------------------------

    @ConfigSection(
            name="Own Player settings",
            description="Own player settings",
            position=2,
            closedByDefault=true
    )
    String ownPlayerSection = "ownPlayerSection";

    @ConfigItem(
            keyName="ownPlayerTile",
            name="Tile",
            description="Highlight ownPlayer player's tile?",
            section= ownPlayerSection,
            position=0
    )
    default boolean ownPlayerTile()
    {
        return defaultTile;
    }

    @ConfigItem(
            keyName="ownPlayerOutline",
            name="Outline",
            description="Highlight ownPlayer player's outline?",
            section= ownPlayerSection,
            position=1
    )
    default boolean ownPlayerOutline()
    {
        return defaultOutline;
    }

    @ConfigItem(
            keyName="ownPlayerHull",
            name="Hull",
            description="Highlight ownPlayer player's hull ring?",
            section= ownPlayerSection,
            position=2
    )
    default boolean ownPlayerHull()
    {
        return defaultHull;
    }

    @ConfigItem(
            keyName="ownPlayerMinimapAnimation",
            name="Minimap",
            description="Minimap highlight (None=off)",
            section= ownPlayerSection,
            position=3
    )
    default MinimapAnimation ownPlayerMinimapAnimation()
    {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName="ownPlayerNameLocation",
            name="Name Location",
            description="Where to display ownPlayer player's name?",
            section= ownPlayerSection,
            position=4
    )
    default NameLocation ownPlayerNameLocation()
    {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "ownPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = ownPlayerSection,
            position = 5
    )
    default boolean ownPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 3) Party members section
    // ----------------------------------------------------

    @ConfigSection(
            name="Party Member settings",
            description="Party member highlight settings",
            position=3,
            closedByDefault=true
    )
    String partySection = "partySection";

    @ConfigItem(
            keyName="partyPlayerTile",
            name="Tile",
            description="Highlight party member's tile?",
            section=partySection,
            position=0
    )
    default boolean partyPlayerTile() {
        return defaultTile;
    }

    @ConfigItem(
            keyName="partyPlayerOutline",
            name="Outline",
            description="Highlight party member's outline?",
            section=partySection,
            position=1
    )
    default boolean partyPlayerOutline() {
        return defaultOutline;
    }

    @ConfigItem(
            keyName="partyPlayerHull",
            name="Hull",
            description="Highlight party member's hull ring?",
            section=partySection,
            position=2
    )
    default boolean partyPlayerHull() {
        return defaultHull;
    }

    @ConfigItem(
            keyName="partyPlayerMinimapAnimation",
            name="Minimap",
            description="Minimap highlight (None=off)",
            section=partySection,
            position=3
    )
    default MinimapAnimation partyPlayerMinimapAnimation() {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName="partyPlayerNameLocation",
            name="Name Location",
            description="Where to display party member's name?",
            section=partySection,
            position=4
    )
    default NameLocation partyPlayerNameLocation() {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "partyPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = partySection,
            position = 5
    )
    default boolean partyPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }


    // ----------------------------------------------------
    // 4) Friends settings
    // ----------------------------------------------------

    @ConfigSection(
            name="Friends settings",
            description="Friends highlight settings",
            position=4,
            closedByDefault=true
    )
    String friendsSection = "friendsSection";

    @ConfigItem(
            keyName="friendsPlayerTile",
            name="Tile",
            description="Highlight friend's tile?",
            section=friendsSection,
            position=0
    )
    default boolean friendsPlayerTile() { return defaultTile; }

    @ConfigItem(
            keyName="friendsPlayerOutline",
            name="Outline",
            description="Highlight friend's outline?",
            section=friendsSection,
            position=1
    )
    default boolean friendsPlayerOutline() { return defaultOutline; }

    @ConfigItem(
            keyName="friendsPlayerHull",
            name="Hull",
            description="Highlight friend's hull ring?",
            section=friendsSection,
            position=2
    )
    default boolean friendsPlayerHull() { return defaultHull; }

    @ConfigItem(
            keyName="friendsPlayerMinimapAnimation",
            name="Minimap",
            description="Minimap highlight (None=off)",
            section=friendsSection,
            position=3
    )
    default MinimapAnimation friendsPlayerMinimapAnimation() { return defaultMinimapanimation; }

    @ConfigItem(
            keyName="friendsPlayerNameLocation",
            name="Name Location",
            description="Where to display friend's name?",
            section=friendsSection,
            position=4
    )
    default NameLocation friendsPlayerNameLocation() { return defaultNamelocation; }

    @ConfigItem(
            keyName = "friendsPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = friendsSection,
            position = 5
    )
    default boolean friendsPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 5) Friends Chat settings
    // ----------------------------------------------------

    @ConfigSection(
            name="Friends Chat settings",
            description="Friends chat highlight settings",
            position=5,
            closedByDefault=true
    )
    String friendsChatSection = "friendsChatSection";

    @ConfigItem(
            keyName="friendsChatPlayerTile",
            name="Tile",
            description="Highlight friends chat member's tile?",
            section=friendsChatSection,
            position=0
    )
    default boolean friendsChatPlayerTile() { return defaultTile; }

    @ConfigItem(
            keyName="friendsChatPlayerOutline",
            name="Outline",
            description="Highlight friends chat member's outline?",
            section=friendsChatSection,
            position=1
    )
    default boolean friendsChatPlayerOutline() { return defaultOutline; }

    @ConfigItem(
            keyName="friendsChatPlayerHull",
            name="Hull",
            description="Highlight friends chat member's hull ring?",
            section=friendsChatSection,
            position=2
    )
    default boolean friendsChatPlayerHull() { return defaultHull; }

    @ConfigItem(
            keyName="friendsChatPlayerMinimapAnimation",
            name="Minimap",
            description="Minimap highlight (None=off)",
            section=friendsChatSection,
            position=3
    )
    default MinimapAnimation friendsChatPlayerMinimapAnimation() { return defaultMinimapanimation; }

    @ConfigItem(
            keyName="friendsChatPlayerNameLocation",
            name="Name Location",
            description="Where to display friends chat member's name?",
            section=friendsChatSection,
            position=4
    )
    default NameLocation friendsChatPlayerNameLocation() { return defaultNamelocation; }

    @ConfigItem(
            keyName = "friendsChatPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = friendsChatSection,
            position = 5
    )
    default boolean friendsChatPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 6) Team Member settings
    // ----------------------------------------------------

    @ConfigSection(
            name="Team Member settings",
            description="Team member highlight settings",
            position=6,
            closedByDefault=true
    )
    String teamSection = "teamSection";

    @ConfigItem(
            keyName="teamPlayerTile",
            name="Tile",
            description="Highlight team member's tile?",
            section=teamSection,
            position=0
    )
    default boolean teamPlayerTile() { return defaultTile; }

    @ConfigItem(
            keyName="teamPlayerOutline",
            name="Outline",
            description="Highlight team member's outline?",
            section=teamSection,
            position=1
    )
    default boolean teamPlayerOutline() { return defaultOutline; }

    @ConfigItem(
            keyName="teamPlayerHull",
            name="Hull",
            description="Highlight team member's hull ring?",
            section=teamSection,
            position=2
    )
    default boolean teamPlayerHull() { return defaultHull; }

    @ConfigItem(
            keyName="teamPlayerMinimapAnimation",
            name="Minimap",
            description="Minimap highlight (None=off)",
            section=teamSection,
            position=3
    )
    default MinimapAnimation teamPlayerMinimapAnimation() { return defaultMinimapanimation; }

    @ConfigItem(
            keyName="teamPlayerNameLocation",
            name="Name Location",
            description="Where to display team member's name?",
            section=teamSection,
            position=4
    )
    default NameLocation teamPlayerNameLocation() { return defaultNamelocation; }

    @ConfigItem(
            keyName = "teamPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = teamSection,
            position = 5
    )
    default boolean teamPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 7) Clan settings
    // ----------------------------------------------------

    @ConfigSection(
            name = "Clan settings",
            description = "Clan settings",
            position = 7,
            closedByDefault = true
    )
    String clanSection = "clanSection";

    @ConfigItem(
            keyName = "clanPlayerTile",
            name = "Tile",
            description = "Highlight clan player's tile?",
            section = clanSection,
            position = 0
    )
    default boolean clanPlayerTile()
    {
        return false;
    }

    @ConfigItem(
            keyName = "clanPlayerOutline",
            name = "Outline",
            description = "Highlight clan player's outline?",
            section = clanSection,
            position = 1
    )
    default boolean clanPlayerOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName = "clanPlayerHull",
            name = "Hull",
            description = "Highlight clan player's hull ring?",
            section = clanSection,
            position = 2
    )
    default boolean clanPlayerHull()
    {
        return false;
    }

    @ConfigItem(
            keyName = "clanPlayerMinimapAnimation",
            name = "Minimap",
            description = "Minimap highlight (None=off)",
            section = clanSection,
            position = 3
    )
    default MinimapAnimation clanPlayerMinimapAnimation()
    {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName = "clanPlayerNameLocation",
            name = "Name Location",
            description = "Where to display clan player's name?",
            section = clanSection,
            position = 4
    )
    default NameLocation clanPlayerNameLocation()
    {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "clanPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = clanSection,
            position = 5
    )
    default boolean clanPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 8) Attackable settings
    // ----------------------------------------------------

    @ConfigSection(
            name = "Attackable settings",
            description = "Attackable player settings",
            position = 8,
            closedByDefault = true
    )
    String attackableSection = "attackableSection";

    @ConfigItem(
            keyName = "attackablePlayerTile",
            name = "Tile",
            description = "Highlight attackable player's tile?",
            section = attackableSection,
            position = 0
    )
    default boolean attackablePlayerTile()
    {
        return defaultTile;
    }

    @ConfigItem(
            keyName = "attackablePlayerOutline",
            name = "Outline",
            description = "Highlight attackable player's outline?",
            section = attackableSection,
            position = 1
    )
    default boolean attackablePlayerOutline()
    {
        return true;
    }

    @ConfigItem(
            keyName = "attackablePlayerHull",
            name = "Hull",
            description = "Highlight attackable player's hull ring?",
            section = attackableSection,
            position = 2
    )
    default boolean attackablePlayerHull()
    {
        return defaultHull;
    }

    @ConfigItem(
            keyName = "attackablePlayerMinimapAnimation",
            name = "Minimap",
            description = "Minimap highlight (None=off)",
            section = attackableSection,
            position = 3
    )
    default MinimapAnimation attackablePlayerMinimapAnimation()
    {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName = "attackablePlayerNameLocation",
            name = "Name Location",
            description = "Where to display attackable player's name?",
            section = attackableSection,
            position = 4
    )
    default NameLocation attackablePlayerNameLocation()
    {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "attackablePlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = attackableSection,
            position = 5
    )
    default boolean attackablePlayerCombatLevel()
    {
        return true;
    }

    // ----------------------------------------------------
    // 9) Ignored settings
    // ----------------------------------------------------

    @ConfigSection(
            name = "Ignored settings",
            description = "Ignored player settings",
            position = 9,
            closedByDefault = true
    )
    String ignoredSection = "ignoredSection";

    @ConfigItem(
            keyName = "ignoredPlayerTile",
            name = "Tile",
            description = "Highlight ignored player's tile?",
            section = ignoredSection,
            position = 0
    )
    default boolean ignoredPlayerTile()
    {
        return defaultTile;
    }

    @ConfigItem(
            keyName = "ignoredPlayerOutline",
            name = "Outline",
            description = "Highlight ignored player's outline?",
            section = ignoredSection,
            position = 1
    )
    default boolean ignoredPlayerOutline()
    {
        return defaultOutline;
    }

    @ConfigItem(
            keyName = "ignoredPlayerHull",
            name = "Hull",
            description = "Highlight ignored player's hull ring?",
            section = ignoredSection,
            position = 2
    )
    default boolean ignoredPlayerHull()
    {
        return defaultHull;
    }

    @ConfigItem(
            keyName = "ignoredPlayerMinimapAnimation",
            name = "Minimap",
            description = "Minimap highlight (None=off)",
            section = ignoredSection,
            position = 3
    )
    default MinimapAnimation ignoredPlayerMinimapAnimation()
    {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName = "ignoredPlayerNameLocation",
            name = "Name Location",
            description = "Where to display ignored player's name?",
            section = ignoredSection,
            position = 4
    )
    default NameLocation ignoredPlayerNameLocation()
    {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "ignoredPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = ignoredSection,
            position = 5
    )
    default boolean ignoredPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 10) Tagged settings
    // ----------------------------------------------------

    @ConfigSection(
            name = "Tagged settings",
            description = "Tagged player settings",
            position = 10,
            closedByDefault = true
    )
    String taggedSection = "taggedSection";

    @ConfigItem(
            keyName = "taggedPlayerTile",
            name = "Tile",
            description = "Highlight tagged player's tile?",
            section = taggedSection,
            position = 0
    )
    default boolean taggedPlayerTile()
    {
        return defaultTile;
    }

    @ConfigItem(
            keyName = "taggedPlayerOutline",
            name = "Outline",
            description = "Highlight tagged player's outline?",
            section = taggedSection,
            position = 1
    )
    default boolean taggedPlayerOutline()
    {
        return true;
    }

    @ConfigItem(
            keyName = "taggedPlayerHull",
            name = "Hull",
            description = "Highlight tagged player's hull ring?",
            section = taggedSection,
            position = 2
    )
    default boolean taggedPlayerHull()
    {
        return defaultHull;
    }

    @ConfigItem(
            keyName = "taggedPlayerMinimapAnimation",
            name = "Minimap",
            description = "Minimap highlight (None=off)",
            section = taggedSection,
            position = 3
    )
    default MinimapAnimation taggedPlayerMinimapAnimation()
    {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName = "taggedPlayerNameLocation",
            name = "Name Location",
            description = "Where to display tagged player's name?",
            section = taggedSection,
            position = 4
    )
    default NameLocation taggedPlayerNameLocation()
    {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "taggedPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = taggedSection,
            position = 5
    )
    default boolean taggedPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 11) Others settings
    // ----------------------------------------------------

    @ConfigSection(
            name = "Others settings",
            description = "Other/unmatched players",
            position = 11,
            closedByDefault = true
    )
    String othersSection = "othersSection";

    @ConfigItem(
            keyName = "othersPlayerTile",
            name = "Tile",
            description = "Highlight unmatched player's tile?",
            section = othersSection,
            position = 0
    )
    default boolean othersPlayerTile()
    {
        return defaultTile;
    }

    @ConfigItem(
            keyName = "othersPlayerOutline",
            name = "Outline",
            description = "Highlight unmatched player's outline?",
            section = othersSection,
            position = 1
    )
    default boolean othersPlayerOutline()
    {
        return true;
    }

    @ConfigItem(
            keyName = "othersPlayerHull",
            name = "Hull",
            description = "Highlight unmatched player's hull ring?",
            section = othersSection,
            position = 2
    )
    default boolean othersPlayerHull()
    {
        return defaultHull;
    }

    @ConfigItem(
            keyName = "othersPlayerMinimapAnimation",
            name = "Minimap",
            description = "Minimap highlight (None=off)",
            section = othersSection,
            position = 3
    )
    default MinimapAnimation othersPlayerMinimapAnimation()
    {
        return defaultMinimapanimation;
    }

    @ConfigItem(
            keyName = "othersPlayerNameLocation",
            name = "Name Location",
            description = "Where to display unmatched player's name?",
            section = othersSection,
            position = 4
    )
    default NameLocation othersPlayerNameLocation()
    {
        return defaultNamelocation;
    }

    @ConfigItem(
            keyName = "othersPlayerCombatLevel",
            name = "Show combat level",
            description = "Show player's combat level next to the name.",
            section = othersSection,
            position = 5
    )
    default boolean othersPlayerCombatLevel()
    {
        return defaultCombatLevel;
    }

    // ----------------------------------------------------
    // 1) Priority settings
    // ----------------------------------------------------

    @ConfigSection(
            name="Priority settings",
            description="Change default priorities. This changes which color is shown ",
            position=12
    )
    String prioritySection = "prioritySection";

    @ConfigItem(
            keyName="priorityExplanation",
            name="Change default priorities.",
            description="",
            section= prioritySection,
            position=0
    )
    default void priorityExplanation(){}

    @ConfigItem(
            keyName="priorityExplanation",
            name="If a player falls under multiple ",
            description="",
            section= prioritySection,
            position=1
    )
    default void priorityExplanation2(){}

    @ConfigItem(
            keyName="priorityExplanation",
            name="categories the one with the highest",
            description="",
            section= prioritySection,
            position=2
    )
    default void priorityExplanation3(){}

    @ConfigItem(
            keyName="priorityExplanation",
            name="priority will be chosen as the color/settings",
            description="priority will be chosen as the color/settings",
            section= prioritySection,
            position=3
    )
    default void priorityExplanation4(){}
    

    @ConfigItem(
            keyName="priorityOthers",
            name="Other players",
            description="Highlight tagged players",
            section= prioritySection,
            position=4
    )
    default int priorityOthers()
    {
        return HighlighterType.OTHER.getPriority();
    }

    @ConfigItem(
            keyName="priorityParty",
            name="Party members",
            description="Highlight party members",
            section= prioritySection,
            position=5
    )
    default int priorityParty()
    {
        return HighlighterType.PARTY.getPriority();
    }

    @ConfigItem(
            keyName="priorityFriendsChat",
            name="Friends Chat",
            description="Highlight players in your friends chat channel",
            section= prioritySection,
            position=6
    )
    default int priorityFriendsChat()
    {
        return HighlighterType.FRIENDS_CHAT.getPriority();
    }

    @ConfigItem(
            keyName="priorityTeam",
            name="Team Members",
            description="Highlight team members",
            section= prioritySection,
            position=7
    )
    default int priorityTeam()
    {
        return HighlighterType.TEAM_MEMBERS.getPriority();
    }

    @ConfigItem(
            keyName="priorityClan",
            name="Clan Members",
            description="Highlight clan members",
            section= prioritySection,
            position=8
    )
    default int priorityClan()
    {
        return HighlighterType.CLAN_MEMBERS.getPriority();
    }

    @ConfigItem(
            keyName="priorityFriends",
            name="Friends",
            description="Highlights friends",
            section= prioritySection,
            position=9
    )
    default int priorityFriends()
    {
        return HighlighterType.FRIENDS.getPriority();
    }

    @ConfigItem(
            keyName="priorityAttackable",
            name="Attackable players",
            description="Highlight attackable players",
            section= prioritySection,
            position=10
    )
    default int priorityAttackable()
    {
        return HighlighterType.ATTACKABLE.getPriority();
    }

    @ConfigItem(
            keyName="priorityIgnore",
            name="Ignored players",
            description="Highlight ignored players",
            section= prioritySection,
            position=11
    )
    default int priorityIgnore()
    {
        return HighlighterType.IGNORED.getPriority();
    }

    @ConfigItem(
            keyName="priorityTagged",
            name="Tagged players",
            description="Highlight tagged players",
            section= prioritySection,
            position=12
    )
    default int priorityTagged()
    {
        return HighlighterType.TAGGED.getPriority();
    }

    // ----------------------------------------------------
    // 2) Thickness & sizes
    // ----------------------------------------------------

    @ConfigSection(
            name="Thickness & sizes",
            description="Global thickness and sizes settings",
            position=12,
            closedByDefault=true
    )
    String thicknessSizesSection = "thicknessSizesSection";

    @Range(min=1, max=255)
    @ConfigItem(
            keyName="tileThickness",
            name="Tile Thickness",
            description="Line thickness (1..255) for tile highlights",
            section= thicknessSizesSection,
            position = 0
    )
    default int tileThickness()
    {
        return 2;
    }

    @Range(min=1, max=255)
    @ConfigItem(
            keyName="outlineThickness",
            name="Outline Thickness",
            description="Outline thickness (1..255).",
            section= thicknessSizesSection,
            position = 1
    )
    default int outlineThickness()
    {
        return 2;
    }

    @Range(min=1, max=255)
    @ConfigItem(
            keyName="hullThickness",
            name="Hull Thickness",
            description="Stroke thickness for hull (no fill).",
            section= thicknessSizesSection,
            position = 2
    )
    default int hullThickness()
    {
        return 2;
    }

    @Range(min=1, max=255)
    @ConfigItem(
            keyName="minimapCircleSize",
            name="Minimap Circle Size",
            description="Base size (1..255) of minimap highlight circle.",
            section= thicknessSizesSection,
            position = 3
    )
    default int minimapCircleSize()
    {
        return 5;
    }

    @Range(min=20, max=20000)
    @ConfigItem(
            keyName="minimapAnimSpeed",
            name="Minimap Anim Speed",
            description="Animation speed in milliseconds per cycle",
            section= thicknessSizesSection,
            position = 4
    )
    default int minimapAnimSpeed()
    {
        return 1500;
    }
}
