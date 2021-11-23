package dev.lone.rpghuds.core;

import dev.lone.rpghuds.core.data.ArrowTargetHud;
import dev.lone.rpghuds.utils.EventsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;

import java.util.WeakHashMap;

public class ArrowTargetHudListener implements Listener
{
    private final Plugin plugin;
    private final RPGHuds rpgHuds;

    WeakHashMap<Integer, Player> projectilesShotByPlayer = new WeakHashMap<>();

    public ArrowTargetHudListener(Plugin plugin, RPGHuds rpgHuds)
    {
        this.plugin = plugin;
        this.rpgHuds = rpgHuds;
    }

    public void registerListener()
    {
        EventsUtil.registerEventOnce(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerShootBow(EntityShootBowEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;

        ArrowTargetHud hud = (ArrowTargetHud) rpgHuds.getPlayerHud((Player) e.getEntity(), "rpghuds:arrow_target");
        if(hud != null)
            projectilesShotByPlayer.put(e.getProjectile().getEntityId(), (Player) e.getEntity());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onProjectileHit(ProjectileHitEvent e)
    {
        Projectile projectile = e.getEntity();
        Player player = projectilesShotByPlayer.get(projectile.getEntityId());
        if(player == null)
            return;

        Block hitBlock = e.getHitBlock();
        if (hitBlock != null)
        {
            if (hitBlock.getType() == Material.TARGET)
            {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {

                    if (hitBlock.getType() == Material.TARGET)
                    {
                        ArrowTargetHud hud = (ArrowTargetHud) rpgHuds.getPlayerHud(player, "rpghuds:arrow_target");
                        if (hud != null)
                        {
                            int power = ((AnaloguePowerable) hitBlock.getBlockData()).getPower();
                            hud.setAccuracy(power);
                        }
                    }

                }, 1);
            }
        }
        //TODO: hit entity ?

        projectilesShotByPlayer.remove(projectile.getEntityId());
    }
}
