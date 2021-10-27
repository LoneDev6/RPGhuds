package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.graphics.CompassSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class CompassHud extends Hud<CompassSettings>
{
    private final PlayerHudsHolderWrapper holder;
    private final PlayerCustomHudWrapper hud;

    private final Player player;

    private Location prevLoc;
    private int prevIndex = -999;
    private FontImageWrapper prevImg;

    Destination destination;

    BukkitTask endSchedule;

    public CompassHud(long refreshIntervalTicks,
                      PlayerHudsHolderWrapper holder,
                      CompassSettings settings) throws NullPointerException
    {
        super(refreshIntervalTicks);
        this.holder = holder;
        this.hudSettings = settings;

        hud = settings.newInstanceByPlayer(holder);
        player = holder.getPlayer();

        imgsBuffer.add(settings.compassIcons.get(0));
    }

    @Override
    public RenderAction refreshRender()
    {
        if(endSchedule != null && !endSchedule.isCancelled())
            return RenderAction.SKIP;

        if (destination == null)
        {
            if (prevImg == hudSettings.iconNothing)
                return RenderAction.SAME_AS_BEFORE;
            setImg(hudSettings.iconNothing);
            return RenderAction.RENDERED;
        }

        hud.setVisible(!hidden);
        if (hidden)
            return RenderAction.SKIP;

        if (!hudSettings.worlds.contains(player.getWorld().getName()))
        {
            hud.setVisible(false);
            return RenderAction.SKIP;
        }

        if (dirtyEquals(prevLoc, player.getLocation()))
            return RenderAction.SAME_AS_BEFORE;

        if (player.getLocation().getWorld() == null)
            return RenderAction.SKIP;

        if (!player.getLocation().getWorld().equals(destination.loc.getWorld()))
        {
            setImg(hudSettings.iconDiffWorld);
            return RenderAction.RENDERED;
        }

        if (player.getLocation().distance(destination.loc) <= 2)
        {
            setImg(hudSettings.iconReached);
            if(destination.callback != null)
                destination.callback.run();
            endSchedule = Bukkit.getScheduler().runTaskLaterAsynchronously(Main.inst(), () -> {
                removeDestination();
                endSchedule.cancel();
                endSchedule = null;
            }, 20 * 4);
            return RenderAction.RENDERED;
        }

        double angle = getAngle();
        int iconIndex = (int) (angle / 11);
        if (iconIndex == prevIndex)
            return RenderAction.SAME_AS_BEFORE;

        setImg(hudSettings.compassIcons.get(iconIndex));
        prevIndex = iconIndex;

        prevLoc = player.getLocation();

        return RenderAction.RENDERED;
    }

    @Override
    public void deleteRender()
    {
        hud.clearFontImagesAndRefresh();
    }

    private void setImg(FontImageWrapper img)
    {
        imgsBuffer.set(0, img);
        hud.clearFontImagesAndRefresh();
        hud.setFontImages(imgsBuffer);
        prevImg = img;
    }

    private double getAngle()
    {
        Location startLoc = player.getEyeLocation();
        destination.loc.setY(startLoc.getY());
        // vector: start to destination
        Vector b = destination.loc.toVector().subtract(startLoc.toVector()).normalize();
        // vector: start to looking direction (a.length == b.length)
        Vector a = player.getEyeLocation().getDirection().clone().setY(0);
        return calculateAngleBetweenVectors(b, a);
    }

    public void removeDestination()
    {
        destination = null;
    }

    public void setDestination(Destination destination)
    {
        this.destination = destination;

        refreshRender();
        holder.recalculateOffsets();
        holder.sendUpdate();
    }

    public static double calculateAngleBetweenVectors(Vector a, Vector b)
    {
        double dot = a.dot(b);
        double det = a.getX() * b.getZ() - a.getZ() * b.getX();
        return Math.toDegrees(Math.atan2(det, dot));
    }

    private static boolean dirtyEquals(Location a, Location b)
    {
        if (a == null && b != null)
            return false;
        return (a.getX() == b.getX() && a.getZ() == b.getZ()) && (a.getYaw() == b.getYaw() && a.getPitch() == b.getPitch());
    }

    public static class Destination
    {
        Location loc;
        Runnable callback;

        /**
         * Rappresents a destination.
         * @param loc location to reach.
         * @param callback callback to be executed when the destination is reached, can be null.
         */
        public Destination(Location loc, @Nullable Runnable callback)
        {
            this.loc = loc;
            this.callback = callback;
        }

        public Destination(Location loc)
        {
            this.loc = loc;
        }
    }
}
