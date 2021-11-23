package dev.lone.rpghuds.core;

import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.utils.EventsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

class EventsListener implements Listener
{
    private final Plugin plugin;
    private final RPGHuds rpgHuds;
    private ItemsAdderLoadListener itemsAdderLoadListener;

    EventsListener(Plugin plugin, RPGHuds rpgHuds)
    {
        this.plugin = plugin;
        this.rpgHuds = rpgHuds;
        this.itemsAdderLoadListener = new ItemsAdderLoadListener(plugin, rpgHuds);
    }

    public void registerListener()
    {
        EventsUtil.registerEventOnce(this, plugin);
        itemsAdderLoadListener.registerListener();

        if(Main.settings.quiverEnabled)
            new QuiverHudListener(plugin, rpgHuds, Main.settings.quiverContentUpdateTicks).registerListener();

        if(Main.settings.arrowTargetEnabled)
            new ArrowTargetHudListener(plugin, rpgHuds).registerListener();
    }

    @EventHandler
    private void onItemsAdderLoad(PluginEnableEvent e)
    {
        if (!e.getPlugin().getName().equals("ItemsAdder"))
            return;

        if (Main.settings.debug)
            plugin.getLogger().log(Level.INFO, "RPGhuds - detected ItemsAdder loading...");

        if (this.itemsAdderLoadListener != null)
            this.itemsAdderLoadListener = new ItemsAdderLoadListener(plugin, rpgHuds);

        rpgHuds.initAllPlayers();
    }

    @EventHandler
    private void onItemsAdderUnload(PluginDisableEvent e)
    {
        if (!e.getPlugin().getName().equals("ItemsAdder"))
            return;

        if (Main.settings.debug)
            plugin.getLogger().log(Level.INFO, "RPGhuds - detected ItemsAdder unload...");

        EventsUtil.unregisterEvent(itemsAdderLoadListener);
        itemsAdderLoadListener = null;

        rpgHuds.cleanup();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e)
    {
        if (rpgHuds.notifyIazip)
        {
            if (e.getPlayer().isOp())
            {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    e.getPlayer().sendMessage(ChatColor.RED + RPGHuds.WARNING);
                }, 60L);
                rpgHuds.notifyIazip = false;
            }
        }

        if (!rpgHuds.needsIaZip)
            rpgHuds.initPlayer(e.getPlayer());
    }
}
