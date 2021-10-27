package dev.lone.rpghuds.core.data;

import dev.lone.rpghuds.core.graphics.IconAmountSettings;

public abstract class PAPIHud<T extends IconAmountSettings> extends Hud<T>
{
    public String placeholder;

    public PAPIHud(String placeholder, long refreshIntervalTicks)
    {
        super(refreshIntervalTicks);
        this.placeholder = placeholder;
    }
}
