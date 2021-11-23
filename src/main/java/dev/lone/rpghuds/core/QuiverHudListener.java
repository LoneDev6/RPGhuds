package dev.lone.rpghuds.core;

import dev.lone.rpghuds.core.data.PlayerData;
import dev.lone.rpghuds.core.data.QuiverHud;
import dev.lone.rpghuds.utils.EventsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class QuiverHudListener implements Listener
{
    private final Plugin plugin;
    private final RPGHuds rpgHuds;

    public QuiverHudListener(Plugin plugin, RPGHuds rpgHuds, long compassContentUpdateTicks)
    {
        this.plugin = plugin;
        this.rpgHuds = rpgHuds;

        //This task makes sure the quiver HUD arrows indicator is always up-to-date because the player inventory
        //may be changed by external causes (plugins manually giving/removing items to the player)
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers())
            {
                QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud(player, "rpghuds:quiver");
                if(hud == null)
                    continue;

                if(QuiverHud.hasWeapon(player))
                {
                    hud.calculateHasWeapon();
                    hud.refreshArrows();
                }
            }

        }, compassContentUpdateTicks, compassContentUpdateTicks);
    }

    public void registerListener()
    {
        EventsUtil.registerEventOnce(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onInventoryClose(InventoryCloseEvent e)
    {
        QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud((Player) e.getPlayer(), "rpghuds:quiver");
        if(hud != null)
            hud.refreshArrows();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerPickupArrow(PlayerPickupItemEvent e)
    {
        if(QuiverHud.isArrow(e.getItem().getItemStack().getType()))
        {
            QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud(e.getPlayer(), "rpghuds:quiver");
            if(hud != null)
                hud.refreshArrowsAdjust(e.getItem().getItemStack().getAmount());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerDropItem(PlayerDropItemEvent e)
    {
        if(QuiverHud.isArrow(e.getItemDrop().getItemStack().getType()))
        {
            QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud(e.getPlayer(), "rpghuds:quiver");
            if(hud != null)
                hud.refreshArrowsAdjust(-e.getItemDrop().getItemStack().getAmount());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerItemHeld(PlayerItemHeldEvent e)
    {
        ItemStack itemStack = e.getPlayer().getInventory().getItem(e.getNewSlot());

        QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud(e.getPlayer(), "rpghuds:quiver");
        if(hud != null)
            hud.refreshOnWeaponHold(itemStack);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e)
    {
        QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud(e.getPlayer(), "rpghuds:quiver");
        if(hud != null)
        {
            hud.calculateHasWeapon();

            // If it's swapping offhand -> mainhand we need a little delay because the vanilla animation has a little delay.
            if(QuiverHud.isWeapon(e.getMainHandItem()))
                Bukkit.getScheduler().runTaskLater(plugin, () -> handleItemSwap(hud, e), 2);
            else
                handleItemSwap(hud, e);
        }
    }

    private void handleItemSwap(QuiverHud hud, PlayerSwapHandItemsEvent e)
    {
        hud.updateOffsetX(e.getOffHandItem() != null && e.getOffHandItem().getType() != Material.AIR);
        hud.refreshRender(true);
        PlayerData.sendPacket(hud.holder, true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerShootBow(EntityShootBowEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;

        QuiverHud hud = (QuiverHud) rpgHuds.getPlayerHud((Player) e.getEntity(), "rpghuds:quiver");
        if(hud != null)
            hud.refreshArrowsAdjust(-1);
    }
}
