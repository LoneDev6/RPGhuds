package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;

import java.util.ArrayList;
import java.util.List;

public class PlayerData
{
    private final PlayerHudsHolderWrapper holder;
    public final List<Hud<?>> allHuds = new ArrayList<>();
    private final List<Hud<?>> hudsHighFreq = new ArrayList<>();

    public PlayerData(PlayerHudsHolderWrapper holder)
    {
        this.holder = holder;
    }

    public void registerHud(Hud<?> hud, boolean highFrequency)
    {
        if (highFrequency)
            hudsHighFreq.add(hud);
        allHuds.add(hud);
    }

    public PlayerHudsHolderWrapper getHolder()
    {
        return holder;
    }

    public void refreshAllHuds()
    {
        boolean refreshedAny = false;
        for (Hud<?> hud : allHuds)
        {
            if (hud.refreshRender() == Hud.RenderAction.RENDERED)
                refreshedAny = true;
        }

        if (refreshedAny)
        {
            holder.recalculateOffsets();
            holder.sendUpdate();
        }
    }

    public void refreshHighFrequency()
    {
        boolean refreshedAny = false;
        for (Hud<?> hud : hudsHighFreq)
        {
            if (hud.refreshRender() == Hud.RenderAction.RENDERED)
                refreshedAny = true;
        }

        if (refreshedAny)
        {
            holder.recalculateOffsets();
            holder.sendUpdate();
        }
    }

    public void cleanup()
    {
        for (Hud<?> hud : allHuds)
        {
            hud.deleteRender();
        }
    }
}