package dev.lone.rpghuds.core.graphics;

import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;

import java.util.HashSet;

public abstract class HudSettings
{
    public final String namespacedID;
    public final HashSet<String> worlds;

    HudSettings(String namespacedID, HashSet<String> worlds)
    {
        this.namespacedID = namespacedID;
        this.worlds = worlds;
    }

    public PlayerCustomHudWrapper newInstanceByPlayer(PlayerHudsHolderWrapper holder)
    {
        return new PlayerCustomHudWrapper(holder, namespacedID);
    }
}
