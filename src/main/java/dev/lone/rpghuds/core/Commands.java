package dev.lone.rpghuds.core;

import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.data.CompassHud;
import dev.lone.rpghuds.core.data.Hud;
import dev.lone.rpghuds.utils.Utilz;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter
{
    private static final List<String> EMPTY_LIST = Collections.singletonList("");

    public void register()
    {
        Bukkit.getPluginCommand("rpgcompass").setExecutor(this);
        Bukkit.getPluginCommand("rpghuds").setExecutor(this);

        Bukkit.getPluginCommand("rpgcompass").setTabCompleter(this);
        Bukkit.getPluginCommand("rpghuds").setTabCompleter(this);
    }

    private boolean hasPerm(CommandSender sender, Player target, String perm)
    {
        if (sender == target)
        {
            if (!sender.hasPermission(perm))
            {
                sender.sendMessage(ChatColor.RED + "No permission " + perm);
                return false;
            }
        }

        if (sender != target)
        {
            if (!sender.hasPermission(perm + ".others"))
            {
                sender.sendMessage(ChatColor.RED + "No permission " + perm + ".others");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        switch (command.getName())
        {
            case "rpghuds":
            {
                Player player;
                if (args.length == 3)
                {
                    player = Bukkit.getPlayer(args[2]);
                }
                else
                {
                    if (sender instanceof Player)
                        player = (Player) sender;
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "Please specify a player.");
                        return true;
                    }
                }

                Hud<?> playerHud = RPGHuds.inst().getPlayerHud(player, args[1]);
                if (playerHud == null)
                {
                    sender.sendMessage(Main.settings.msgHudNotFound);
                    return true;
                }

                switch (args[0])
                {
                    case "show":
                        if (hasPerm(sender, player, "rpghuds.show"))
                            playerHud.hidden = false;
                        break;
                    case "hide":
                        if (hasPerm(sender, player, "rpghuds.hide"))
                            playerHud.hidden = true;
                        break;
                }
                break;
            }
            case "rpgcompass":
            {
                if (args.length < 2)
                {
                    sender.sendMessage(Main.settings.msgWrongUsage);
                    return true;
                }

                if (args[1].equals("set"))
                {
                    if (args.length < 6)
                    {
                        sender.sendMessage(Main.settings.msgWrongUsage);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[0]);

                    if (hasPerm(sender, player, "rpghuds.compass.set"))
                    {
                        Location location = new Location(
                                Bukkit.getWorld(args[2]),
                                Utilz.parseInt(args[3], 0),
                                Utilz.parseInt(args[4], 0),
                                Utilz.parseInt(args[5], 0)
                        );

                        CompassHud hud = (CompassHud) RPGHuds.inst().getPlayerHud(player, "rpghuds:compass");
                        if (hud == null)
                        {
                            sender.sendMessage(Main.settings.msgHudNotFound);
                            return true;
                        }
                        hud.setDestination(new CompassHud.Destination(location));
                        sender.sendMessage(Main.settings.msgDestinationSet);
                    }
                }
                else if (args[1].equals("remove"))
                {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (hasPerm(sender, player, "rpghuds.compass.remove"))
                    {
                        CompassHud hud = (CompassHud) RPGHuds.inst().getPlayerHud(player, "rpghuds:compass");
                        if (hud == null)
                        {
                            sender.sendMessage(Main.settings.msgHudNotFound);
                            return true;
                        }
                        hud.removeDestination();
                        sender.sendMessage(Main.settings.msgDestinationRemoved);
                    }
                }
                break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, @NotNull String[] args)
    {
        switch (command.getName())
        {
            case "rpghuds":
            {
                if (args.length == 1)
                    return Arrays.asList("show", "hide");
                if (args.length == 2)
                    return RPGHuds.inst().getHudsNames();
                if (args.length == 3)
                {
                    List<String> names = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers())
                        names.add(p.getName());
                    return names;
                }
                break;
            }
            case "rpgcompass":
            {
                if (args.length == 1)
                {
                    List<String> names = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers())
                        names.add(p.getName());
                    return names;
                }
                if (args.length == 2)
                    return Arrays.asList("set", "remove");
                if (args.length == 3)
                {
                    List<String> names = new ArrayList<>();
                    for (World w : Bukkit.getWorlds())
                        names.add(w.getName());
                    return names;
                }

                if (args.length > 3 && args.length < 7)
                {
                    if (sender instanceof Player)
                    {
                        Player player = (Player) sender;
                        Location location = player.getLocation();
                        if (args.length == 4)
                            return Collections.singletonList(String.valueOf(location.getBlockX() + 10));
                        if (args.length == 5)
                            return Collections.singletonList(String.valueOf(location.getBlockY() + 10));
                        if (args.length == 6)
                            return Collections.singletonList(String.valueOf(location.getBlockZ() + 10));
                    }
                    else
                        return Arrays.asList("0", "1", "2", "100", "200");
                }
                break;
            }
        }
        return EMPTY_LIST;
    }
}
