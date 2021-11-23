package dev.lone.rpghuds.core;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import dev.lone.rpghuds.utils.EventsUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ItemsAdderLoadListener implements Listener
{
    private final Plugin plugin;
    private final RPGHuds rpgHuds;

    ItemsAdderLoadListener(Plugin plugin, RPGHuds rpgHuds)
    {
        this.plugin = plugin;
        this.rpgHuds = rpgHuds;
    }

    public void registerListener()
    {
        EventsUtil.registerEventOnce(this, plugin);
    }

    @EventHandler
    private void onItemsAdderLoadData(ItemsAdderLoadDataEvent e)
    {
        plugin.getLogger().log(Level.INFO, "RPGhuds - ItemsAdder finished loading");
        rpgHuds.needsIaZip = false;

        rpgHuds.cleanup();
        rpgHuds.initAllPlayers();
    }
}
