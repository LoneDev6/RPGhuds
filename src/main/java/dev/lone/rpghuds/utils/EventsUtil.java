package dev.lone.rpghuds.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class EventsUtil
{
    public static void registerEventOnce(Listener li, Plugin plugin)
    {
        for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin))
            if (li.getClass().isInstance(listener.getListener()))
                return;
        Bukkit.getPluginManager().registerEvents(li, plugin);
    }

    public static void unregisterEvent(Listener li)
    {
        HandlerList.unregisterAll(li);
    }

    public static boolean call(Event e)
    {
        Bukkit.getPluginManager().callEvent(e);
        if (e instanceof Cancellable)
            return !((Cancellable) e).isCancelled();
        return true;
    }
}
