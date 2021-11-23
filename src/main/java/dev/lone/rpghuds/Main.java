package dev.lone.rpghuds;

import dev.lone.rpghuds.core.Commands;
import dev.lone.rpghuds.core.RPGHuds;
import dev.lone.rpghuds.core.Settings;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * This is a proof of concept, feel free to modify it and help me to make it better.
 * <p>
 * NOTE: make sure your code is optimized, I don't care about clean code just to feel cool or edgy.
 * If it's heavier than the actual design please don't make any change.
 * Make sure your changes are actually optimized and perfectly usable in a large scale server.
 */
public final class Main extends JavaPlugin implements Listener
{
    private static Main instance;
    public static Settings settings;
    private static RPGHuds rpgHuds;

    @Nullable
    public static Economy econ = null;

    public static Main inst()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;

        initVaultEconomy();
        initConfig();

        rpgHuds = new RPGHuds(this);

        new Commands().register();
    }

    @Override
    public void onDisable()
    {
        rpgHuds.cleanup();
    }

    public void initConfig()
    {
        reloadConfig();

        try
        {
            File configFile = new File(getDataFolder(), "config.yml");
            FileConfiguration config = getConfig();
            InputStream configResource = getResource("config.yml");
            if(configResource == null)
            {
                getLogger().severe("Error. Missing config.yml inside the JAR file.");
                return;
            }

            // Load the default file from JAR resources
            if (!configFile.exists())
            {
                FileUtils.copyInputStreamToFile(configResource, configFile);
            }
            else // Add missing properties
            {
                FileConfiguration tmp = YamlConfiguration.loadConfiguration((new InputStreamReader(configResource, StandardCharsets.UTF_8)));
                for (String k : tmp.getKeys(true))
                {
                    if (!config.contains(k))
                        config.set(k, tmp.get(k));
                }
                config.save(configFile);
            }
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            getLogger().severe("Error loading config.yml file.");
            e.printStackTrace();
        }

        settings = new Settings(getConfig());
    }

    private void initVaultEconomy()
    {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null)
            econ = rsp.getProvider();
    }

    public void reloadPlugin()
    {
        rpgHuds.cleanup();
        initVaultEconomy();
        initConfig();
        rpgHuds.initAllPlayers();
    }
}
