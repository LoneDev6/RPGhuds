package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.rpghuds.core.graphics.HudSettings;

import java.util.ArrayList;
import java.util.List;

public abstract class Hud<T extends HudSettings>
{
    public T hudSettings;
    public long refreshIntervalTicks;
    public boolean hidden;

    final List<FontImageWrapper> imgsBuffer;

    public Hud(long refreshIntervalTicks)
    {
        this.refreshIntervalTicks = refreshIntervalTicks;
        this.imgsBuffer = new ArrayList<>();
    }

    public abstract RenderAction refreshRender();

    public abstract void deleteRender();

    enum RenderAction
    {
        RENDERED,
        SAME_AS_BEFORE,
        SKIP
    }
}
