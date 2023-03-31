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
    public boolean legacyTextures;
    public int moneyOffset;
    public HashSet<String> moneyWorlds;

    public boolean compassEnabled;
    public int compassOffset;
    public HashSet<String> compassWorlds;

    public boolean quiverEnabled;
    public HashSet<String> quiverWorlds;
    public int quiverOffset;
    public int quiverOffsetWhenOffhandShown;
    public int quiverContentUpdateTicks;

    public boolean arrowTargetEnabled;
    public int arrowTargetOffset;
    public HashSet<String> arrowTargetWorlds;

    public boolean debug;
    public String msgHudNotFound;
    public String msgWrongUsage;
    public String msgDestinationSet;
    public String msgDestinationRemoved;

    public Settings(FileConfiguration config)
    {
        this.refreshIntervalTicks = config.getLong("huds_refresh_interval_ticks", 30);
        this.refreshHighFrequencyIntervalTicks = config.getLong("huds_high_frequency_refresh_interval_ticks", 2);
        this.legacyTextures = config.getBoolean("legacy_1_18_and_lower_textures", false);

        this.moneyEnabled = config.getBoolean("money.enabled", true);
        this.moneyPapi = config.getString("money.papi_placeholder", "%vault_eco_balance_fixed%");
        this.moneyOffset = config.getInt("money.offset", 88);
        this.moneyWorlds = new HashSet<>(config.getStringList("money.worlds"));

        this.compassEnabled = config.getBoolean("compass.enabled", true);
        this.compassOffset = config.getInt("compass.offset", 6);
        this.compassWorlds = new HashSet<>(config.getStringList("compass.worlds"));

        this.quiverEnabled = config.getBoolean("quiver.enabled", true);
        this.quiverWorlds = new HashSet<>(config.getStringList("quiver.worlds"));
        this.quiverOffset = config.getInt("quiver.offset.normal", -96);
        this.quiverOffsetWhenOffhandShown = config.getInt("quiver.offset.when_offhand_shown", -124);
        this.quiverContentUpdateTicks = config.getInt("quiver.content_update_ticks", 100);

        this.arrowTargetEnabled = config.getBoolean("arrow_target.enabled", true);
        this.arrowTargetOffset = config.getInt("arrow_target.offset", 0);
        this.arrowTargetWorlds = new HashSet<>(config.getStringList("arrow_target.worlds"));

        this.debug = config.getBoolean("log.debug", false);

        this.msgHudNotFound = Utilz.color(config.getString("lang.hud_not_found"));
        this.msgWrongUsage = Utilz.color(config.getString("lang.wrong_usage"));
        this.msgDestinationSet = Utilz.color(config.getString("lang.destination_set"));
        this.msgDestinationRemoved = Utilz.color(config.getString("lang.destination_removed"));
    }
}
