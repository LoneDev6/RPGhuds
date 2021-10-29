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
        refreshHuds(allHuds);
    }

    public void refreshHighFrequency()
    {
        refreshHuds(hudsHighFreq);
    }

    private void refreshHuds(List<Hud<?>> hudsHighFreq)
    {
        boolean changedRenderAny = false;
        boolean asBeforeAny = false;
        for (Hud<?> hud : hudsHighFreq)
        {
            if (hud.refreshRender() == Hud.RenderAction.RENDERED)
                changedRenderAny = true;
            if (hud.refreshRender() == Hud.RenderAction.SAME_AS_BEFORE)
                asBeforeAny = true;
        }

        if (changedRenderAny)
        {
            holder.recalculateOffsets();
            holder.sendUpdate();
        }
        if(asBeforeAny)
            holder.sendUpdate();
    }

    public void cleanup()
    {
        for (Hud<?> hud : allHuds)
        {
            hud.deleteRender();
        }
    }
}