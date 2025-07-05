package com.playerindicatorextended;

import com.playerindicatorextended.enums.FontEnum;
import com.playerindicatorextended.enums.MinimapAnimation;
import com.playerindicatorextended.enums.NameLocation;
import com.playerindicatorextended.enums.TagMenuOption;
import net.runelite.client.config.*;
import net.runelite.client.plugins.playerindicators.PlayerNameLocation;

import java.awt.*;

@ConfigGroup("playerindicatorextended")
public interface PlayerIndicatorExtendedConfig extends Config
{
    // ----------------------------------------------------
    // 1) Highlight settings
    // ----------------------------------------------------


    // ----------------------------------------------------
    // 1) Highlight settings
    // ----------------------------------------------------
    @ConfigSection(
            name="Highlight settings",
            description="Toggle and color various types of players",
            position=100
    )
    String highlightsSection = "highLightSection";

    @ConfigItem(
            keyName="highlightOwnPlayer",
            name="Own player",
            description="Highlight the logged in player",
            section= highlightsSection,
            position=0
    )
    default boolean highlightOwnPlayer()
    {
        return false;
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
    default boolean highlightParty()
    {
        return false;
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
    default boolean highlightFriends()
    {
        return false;
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
    default boolean highlightFriendsChat()
    {
        return false;
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
    default boolean highlightTeam()
    {
        return false;
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
    default boolean highlightClan()
    {
        return false;
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
        return false;
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
        return Color.YELLOW;
    }

    @ConfigItem(
            keyName="highlightIgnore",
            name="Ignored players",
            description="Highlight ignored players",
            section= highlightsSection,
            position=14
    )
    default boolean highlightIgnore()
    {
        return false;
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
        return Color.BLACK;
    }

    @ConfigItem(
            keyName="highlightTagged",
            name="Tagged players",
            description="Highlight tagged players",
            section= highlightsSection,
            position=16
    )
    default boolean highlightTagged()
    {
        return false;
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
        return Color.BLACK;
    }

    @ConfigItem(
            keyName="highlightOthers",
            name="Other players",
            description="Highlight tagged players",
            section= highlightsSection,
            position=18
    )
    default boolean highlightOthers()
    {
        return false;
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
        return Color.BLACK;
    }



    @ConfigSection(
            name="Thickness & Minimap",
            description="Global thickness + minimap settings",
            position=100,
            closedByDefault=true
    )
    String secThicknessMinimap = "secThicknessMinimap";


    @ConfigSection(
            name="Name Font & Style",
            description="Global name font, style, offset",
            position=200,
            closedByDefault=true
    )
    String secNameFont = "secNameFont";

    @ConfigSection(
            name="Label Font & Style",
            description="Global label font, style, offset",
            position=300,
            closedByDefault=true
    )
    String secLabelFont = "secLabelFont";

    @ConfigSection(
            name="Own Player Highlight",
            description="Own player settings",
            position=400,
            closedByDefault=true
    )
    String secOwnPlayer = "secOwnPlayer";


    @ConfigSection(
            name="Attackable Highlight",
            description="Players you can attack",
            position=500,
            closedByDefault=true
    )
    String secAttackable = "secAttackable";

    @ConfigSection(
            name="Friends Highlight",
            description="Highlight your friends",
            position=600,
            closedByDefault=true
    )
    String secFriends = "secFriends";

    @ConfigSection(
            name="Ignore Highlight",
            description="Highlight players on your ignore list",
            position=700,
            closedByDefault=true
    )
    String secIgnore = "secIgnore";

    @ConfigSection(
            name="Chat Channel Highlight",
            description="Highlight players in your clan/friends chat channel",
            position=800,
            closedByDefault=true
    )
    String secChat = "secChat";


    @ConfigSection(
            name="Tag Players Highlight",
            description="Highlight players by name",
            position=900,
            closedByDefault=true
    )
    String secTag = "secTag";

    @Range(min=1, max=255)
    @ConfigItem(
            keyName="tileThickness",
            name="Tile Thickness",
            description="Line thickness (1..255) for tile highlights",
            section=secThicknessMinimap
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
            section=secThicknessMinimap
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
            section=secThicknessMinimap
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
            section=secThicknessMinimap
    )
    default int minimapCircleSize()
    {
        return 3;
    }

    @Range(min=20, max=20000)
    @ConfigItem(
            keyName="minimapAnimSpeed",
            name="Minimap Anim Speed",
            description="Animation speed in milliseconds per cycle",
            section=secThicknessMinimap
    )
    default int minimapAnimSpeed()
    {
        return 500;
    }

    // ----------------------------------------------------
    // 2) Name Font & Style
    // ----------------------------------------------------

    @ConfigItem(
            keyName="nameFont",
            name="Name Font",
            description="Which font to use for names",
            section=secNameFont
    )
    default FontEnum nameFont(){ return FontEnum.ARIAL; }

    @ConfigItem(
            keyName="nameBold",
            name="Name Bold",
            description="Use bold style for names?",
            section=secNameFont
    )
    default boolean nameBold()
    {
        return false;
    }

    @ConfigItem(
            keyName="nameUnderline",
            name="Name Underline",
            description="Underline the names?",
            section=secNameFont
    )
    default boolean nameUnderline()
    {
        return false;
    }

    @ConfigItem(
            keyName="nameItalic",
            name="Name Italic",
            description="Use italic style for names?",
            section=secNameFont
    )
    default boolean nameItalic()
    {
        return false;
    }

    // ----------------------------------------------------
    // 3) Label Font & Style + Label Offset
    // ----------------------------------------------------

    @ConfigItem(
            keyName="labelFont",
            name="Label Font",
            description="Which font to use for category labels",
            section=secLabelFont
    )
    default FontEnum labelFont()
    {
        return FontEnum.ARIAL;
    }

    @ConfigItem(
            keyName="labelBold",
            name="Label Bold",
            description="Use bold style for labels?",
            section=secLabelFont
    )
    default boolean labelBold()
    {
        return false;
    }

    @ConfigItem(
            keyName="labelUnderline",
            name="Label Underline",
            description="Underline category labels?",
            section=secLabelFont
    )
    default boolean labelUnderline()
    {
        return false;
    }

    @ConfigItem(
            keyName="labelItalic",
            name="Label Italic",
            description="Use italic style for labels?",
            section=secLabelFont
    )
    default boolean labelItalic()
    {
        return false;
    }

    // ----------------------------------------------------
    // 4) OwnPlayer Player
    // ----------------------------------------------------


    @ConfigItem(
            keyName="ownPlayerPlayerOutline",
            name="Outline",
            description="Highlight ownPlayer player's outline?",
            section=secOwnPlayer
    )
    default boolean ownPlayerPlayerOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName="ownPlayerPlayerHull",
            name="Hull",
            description="Highlight ownPlayer player's hull ring?",
            section=secOwnPlayer
    )
    default boolean ownPlayerPlayerHull()
    {
        return false;
    }

    @ConfigItem(
            keyName="ownPlayerPlayerTile",
            name="Tile",
            description="Highlight ownPlayer player's tile?",
            section=secOwnPlayer
    )
    default boolean ownPlayerPlayerTile()
    {
        return false;
    }

    @ConfigItem(
            keyName="ownPlayerPlayerMinimapAnimation",
            name="Minimap",
            description="Minimap highlight (None=off)",
            section=secOwnPlayer
    )
    default MinimapAnimation ownPlayerPlayerMinimapAnimation()
    {
        return MinimapAnimation.NONE;
    }

    @ConfigItem(
            keyName="ownPlayerPlayerNameLocation",
            name="Name Location",
            description="Where to display ownPlayer player's name?",
            section=secOwnPlayer
    )
    default NameLocation ownPlayerPlayerNameLocation()
    {
        return NameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="ownPlayerPlayerLabelLocation",
            name="Label Location",
            description="Where to display 'OwnPlayer Player' label?",
            section=secOwnPlayer
    )
    default NameLocation ownPlayerPlayerLabelLocation()
    {
        return NameLocation.DISABLED;
    }

    // ----------------------------------------------------
    // 5) Attackable
    // ----------------------------------------------------

    @ConfigItem(
            keyName="attackableOutline",
            name="Outline",
            description="Outline highlight for attackable?",
            section=secAttackable
    )
    default boolean attackableOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName="attackableHull",
            name="Hull",
            description="Highlight hull ring for attackable?",
            section=secAttackable
    )
    default boolean attackableHull()
    {
        return false;
    }

    @ConfigItem(
            keyName="attackableTile",
            name="Tile",
            description="Highlight tile for attackable?",
            section=secAttackable
    )
    default boolean attackableTile()
    {
        return false;
    }

    @ConfigItem(
            keyName="attackableMinimapAnimation",
            name="Minimap",
            description="Minimap highlight for attackable",
            section=secAttackable
    )
    default MinimapAnimation attackableMinimapAnimation()
    {
        return MinimapAnimation.NONE;
    }

    @ConfigItem(
            keyName="attackableNameLocation",
            name="Name Location",
            description="Where to display attackable player's name?",
            section=secAttackable
    )
    default PlayerNameLocation attackableNameLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="attackableLabelLocation",
            name="Label Location",
            description="Where to display 'Attackable' label?",
            section=secAttackable
    )
    default PlayerNameLocation attackableLabelLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    // ----------------------------------------------------
    // 6) Friends
    // ----------------------------------------------------



    @ConfigItem(
            keyName="friendsOutline",
            name="Outline",
            description="Highlight outline for friends?",
            section=secFriends
    )
    default boolean friendsOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName="friendsHull",
            name="Hull",
            description="Highlight hull ring for friends?",
            section=secFriends
    )
    default boolean friendsHull()
    {
        return false;
    }

    @ConfigItem(
            keyName="friendsTile",
            name="Tile",
            description="Highlight tile for friends?",
            section=secFriends
    )
    default boolean friendsTile()
    {
        return false;
    }

    @ConfigItem(
            keyName="friendsMinimapAnimation",
            name="Minimap",
            description="Minimap highlight for friends",
            section=secFriends
    )
    default MinimapAnimation friendsMinimapAnimation()
    {
        return MinimapAnimation.NONE;
    }

    @ConfigItem(
            keyName="friendsNameLocation",
            name="Name Location",
            description="Where to display friend name?",
            section=secFriends
    )
    default PlayerNameLocation friendsNameLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="friendsLabelLocation",
            name="Label Location",
            description="Where to display 'Friend' label?",
            section=secFriends
    )
    default PlayerNameLocation friendsLabelLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    // ----------------------------------------------------
    // 7) Ignore
    // ----------------------------------------------------

    @ConfigItem(
            keyName="ignoreOutline",
            name="Outline",
            description="Highlight outline for ignored players?",
            section=secIgnore
    )
    default boolean ignoreOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName="ignoreHull",
            name="Hull",
            description="Highlight hull ring for ignored players?",
            section=secIgnore
    )
    default boolean ignoreHull()
    {
        return false;
    }

    @ConfigItem(
            keyName="ignoreTile",
            name="Tile",
            description="Highlight tile for ignored players?",
            section=secIgnore
    )
    default boolean ignoreTile()
    {
        return false;
    }

    @ConfigItem(
            keyName="ignoreMinimapAnimation",
            name="Minimap",
            description="Minimap highlight for ignored players",
            section=secIgnore
    )
    default MinimapAnimation ignoreMinimapAnimation()
    {
        return MinimapAnimation.NONE;
    }

    @ConfigItem(
            keyName="ignoreNameLocation",
            name="Name Location",
            description="Where to display ignored player's name?",
            section=secIgnore
    )
    default PlayerNameLocation ignoreNameLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="ignoreLabelLocation",
            name="Label Location",
            description="Where to display 'Ignored' label?",
            section=secIgnore
    )
    default PlayerNameLocation ignoreLabelLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    // ----------------------------------------------------
    // 8) Chat Channel
    // ----------------------------------------------------



    @ConfigItem(
            keyName="chatChannelOutline",
            name="Outline",
            description="Outline highlight for chat channel players?",
            section=secChat
    )
    default boolean chatChannelOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName="chatChannelHull",
            name="Hull",
            description="Hull ring for chat channel players?",
            section=secChat
    )
    default boolean chatChannelHull()
    {
        return false;
    }

    @ConfigItem(
            keyName="chatChannelTile",
            name="Tile",
            description="Tile highlight for chat channel players?",
            section=secChat
    )
    default boolean chatChannelTile()
    {
        return false;
    }

    @ConfigItem(
            keyName="chatChannelMinimapAnimation",
            name="Minimap",
            description="Minimap highlight for chat channel players?",
            section=secChat
    )
    default MinimapAnimation chatChannelMinimapAnimation()
    {
        return MinimapAnimation.NONE;
    }

    @ConfigItem(
            keyName="chatChannelNameLocation",
            name="Name Location",
            description="Where to display name for chat channel players?",
            section=secChat
    )
    default PlayerNameLocation chatChannelNameLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="chatChannelLabelLocation",
            name="Label Location",
            description="Where to display 'Chat Channel' label?",
            section=secChat
    )
    default PlayerNameLocation chatChannelLabelLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    // ----------------------------------------------------
    // 9) Tag Players
    // ----------------------------------------------------

    @ConfigItem(
            keyName="tagOutline",
            name="Outline",
            description="Highlight outline for tagged players?",
            section=secTag
    )
    default boolean tagOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName="tagHull",
            name="Hull",
            description="Highlight hull ring for tagged players?",
            section=secTag
    )
    default boolean tagHull()
    {
        return false;
    }

    @ConfigItem(
            keyName="tagTile",
            name="Tile",
            description="Highlight tile for tagged players?",
            section=secTag
    )
    default boolean tagTile()
    {
        return false;
    }

    @ConfigItem(
            keyName="tagMinimapAnimation",
            name="Minimap",
            description="Minimap highlight for tagged players? (None=off)",
            section=secTag
    )
    default MinimapAnimation tagMinimapAnimation()
    {
        return MinimapAnimation.NONE;
    }

    @ConfigItem(
            keyName="tagNameLocation",
            name="Name Location",
            description="Where to display name for tagged players?",
            section=secTag
    )
    default PlayerNameLocation tagNameLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="tagLabelLocation",
            name="Label Location",
            description="Where to display 'Tagged' label?",
            section=secTag
    )
    default PlayerNameLocation tagLabelLocation()
    {
        return PlayerNameLocation.DISABLED;
    }

    @ConfigItem(
            keyName="tagMenuOption",
            name="Tag Menu Option",
            description="OFF=disabled, RIGHT_CLICK=always show, SHIFT_RIGHT_CLICK=only when SHIFT held",
            section=secTag
    )
    default TagMenuOption tagMenuOption()
    {
        return TagMenuOption.OFF;
    }

    // (10) Tagged Player List
    @ConfigItem(
            keyName="taggedPlayersList",
            name="Tagged Player List",
            description="Multi-line list of players (one per line).",
            section=secTag
    )
    default String taggedPlayersList()
    {
        return "";
    }
}
