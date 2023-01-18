package dev.lone.rpghuds.core.settings;

import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public abstract class HudSettings
{
    public final String namespacedID;
    public final int initialOffsetX;
    final HashSet<String> worlds;
    final boolean allWorlds;
    @Nullable
    final HashSet<String> worldsEndsWith;
    @Nullable
    final HashSet<String> worldsStartsWith;

    HudSettings(String namespacedID, int initialOffsetX, HashSet<String> worlds)
    {
        this.namespacedID = namespacedID;
        this.initialOffsetX = initialOffsetX;
        this.worlds = worlds;
        this.allWorlds = (this.worlds.size() == 1 && this.worlds.stream().findFirst().get().equalsIgnoreCase("all"));
        if(!allWorlds)
        {
            this.worldsEndsWith = new HashSet<>();
            for (String w : worlds)
            {
                if (w.startsWith("*"))
                {
                    worldsEndsWith.add(w.substring(1));
                }
            }
            this.worldsStartsWith = new HashSet<>();
            for (String w : worlds)
            {
                if (w.endsWith("*"))
                {
                    worldsStartsWith.add(w.substring(0, w.length() - 1));
                }
            }
        }
        else
        {
            this.worldsEndsWith = null;
            this.worldsStartsWith = null;
        }
    }

    public PlayerCustomHudWrapper newInstanceByPlayer(PlayerHudsHolderWrapper holder)
    {
        return new PlayerCustomHudWrapper(holder, namespacedID);
    }

    public boolean isEnabledInWorld(World world)
    {
        if(allWorlds)
            return true;

        final String name = world.getName();
        if(worlds.contains(name))
            return true;

        if (worldsEndsWith != null)
        {
            for (final String s : worldsEndsWith)
            {
                if(name.endsWith(s))
                    return true;
            }
        }

        if (worldsStartsWith != null)
        {
            for (final String s : worldsStartsWith)
            {
                if(name.startsWith(s))
                    return true;
            }
        }

        return false;
    }
}
