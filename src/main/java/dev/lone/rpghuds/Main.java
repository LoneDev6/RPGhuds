package dev.lone.rpghuds;

import dev.lone.rpghuds.core.Commands;
import dev.lone.rpghuds.core.RPGHuds;
import dev.lone.rpghuds.core.Settings;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener //TODO: update the ItemsAdder API in pom.xml
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
