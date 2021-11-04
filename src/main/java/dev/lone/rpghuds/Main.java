package dev.lone.rpghuds;

import dev.lone.rpghuds.core.Commands;
import dev.lone.rpghuds.core.RPGHuds;
import dev.lone.rpghuds.core.Settings;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is a proof of concept, feel free to modify it and help me to make it better.
 *
 * NOTE: make sure your code is optimized, I don't care about clean code just to feel cool or edgy.
 * If it's heavier than the actual design please don't make any change.
 * Make sure your changes are actually optimized and perfectly usable in a large scale server.
 */
public final class Main extends JavaPlugin implements Listener
{
    private static Main instance;
    public static Settings settings;
    private static RPGHuds rpgHuds;

    public static Main inst()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;

        initConfig();

        settings = new Settings(getConfig());
        rpgHuds = new RPGHuds(this);

        new Commands().register();
    }

    @Override
    public void onDisable()
    {
        rpgHuds.cleanup();
    }

    private void initConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
