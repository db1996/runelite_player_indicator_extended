package com.playerindicatorextended.Highlighters;


import com.playerindicatorextended.PlayerRender.PlayerRenderProperties;

import java.util.List;

public abstract class BaseHighlighter
{
    public abstract List<PlayerRenderProperties> getRenderDecisions();
}
