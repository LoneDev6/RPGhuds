package dev.lone.rpghuds.core;

import dev.lone.rpghuds.utils.Utilz;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;

/**
 * Recode this shit
 */
@Deprecated
public class Settings
{
    public boolean moneyEnabled;
    public String moneyPapi;
    public long refreshIntervalTicks;
    public long refreshHighFrequencyIntervalTicks;
    public HashSet<String> moneyWorlds;

    public boolean compassEnabled;
    public HashSet<String> compassWorlds;

    public boolean debug;
    public String msgHudNotFound;
    public String msgWrongUsage;
    public String msgDestinationSet;
    public String msgDestinationRemoved;

    public Settings(FileConfiguration config)
    {
        this.moneyEnabled = config.getBoolean("money.enabled", true);
        this.moneyPapi = config.getString("money.papi_placeholder", "%vault_eco_balance_fixed%");
        this.refreshIntervalTicks = config.getLong("huds_refresh_interval_ticks", 30);
        this.refreshHighFrequencyIntervalTicks = config.getLong("huds_high_frequency_refresh_interval_ticks", 2);
        this.moneyWorlds = new HashSet<>(config.getStringList("money.worlds"));

        this.compassEnabled = config.getBoolean("compass.enabled", true);
        this.compassWorlds = new HashSet<>(config.getStringList("compass.worlds"));

        this.debug = config.getBoolean("log.debug", false);

        this.msgHudNotFound = Utilz.color(config.getString("lang.hud_not_found"));
        this.msgWrongUsage = Utilz.color(config.getString("lang.wrong_usage"));
        this.msgDestinationSet = Utilz.color(config.getString("lang.destination_set"));
        this.msgDestinationRemoved = Utilz.color(config.getString("lang.destination_removed"));
    }
}
