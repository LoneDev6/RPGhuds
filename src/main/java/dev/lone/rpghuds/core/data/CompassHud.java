package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.settings.CompassSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class CompassHud extends Hud<CompassSettings>
{
    private static final double MAGIC_NUMBER = 11;

    private final Player player;

    private Location prevLoc;
    private int prevIndex = -999;

    Destination destination;

    BukkitTask endSchedule;

    public CompassHud(PlayerHudsHolderWrapper holder,
                      CompassSettings settings) throws NullPointerException
    {
        super(holder, settings);
        player = holder.getPlayer();

        // To init the size of the buffer and avoid NullPointerException
        imgsBuffer.add(settings.compassIcons.get(0));
        hud.setVisible(true);
    }

    @Override
    public RenderAction refreshRender(boolean force)
    {
        if(endSchedule != null && !endSchedule.isCancelled())
            return RenderAction.HIDDEN;

        if (hidden || destination == null)
            return RenderAction.HIDDEN;

        if (!hudSettings.worlds.contains(player.getWorld().getName()))
        {
            hud.setVisible(false);
            return RenderAction.HIDDEN;
        }

        if (dirtyEquals(prevLoc, player.getLocation()))
            return RenderAction.SAME_AS_BEFORE;

        if (player.getLocation().getWorld() == null)
            return RenderAction.HIDDEN;

        if (!player.getLocation().getWorld().equals(destination.loc.getWorld()))
        {
            setImg(hudSettings.iconDiffWorld);
            return RenderAction.SEND_REFRESH;
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
            return RenderAction.SEND_REFRESH;
        }

        double angle = getAngle();
        int iconIndex = (int) (angle / MAGIC_NUMBER);
        if (iconIndex == prevIndex)
            return RenderAction.SAME_AS_BEFORE;

        setImg(hudSettings.compassIcons.get(iconIndex));
        prevIndex = iconIndex;

        prevLoc = player.getLocation();

        return RenderAction.SEND_REFRESH;
    }

    @Override
    public RenderAction refreshRender()
    {
        return refreshRender(false);
    }

    @Override
    public void deleteRender()
    {
        hud.clearFontImagesAndRefresh();

        if(endSchedule != null)
           endSchedule.cancel();
        endSchedule = null;
    }

    private void setImg(FontImageWrapper img)
    {
        imgsBuffer.set(0, img);
        hud.setFontImages(imgsBuffer);
    }

    private double getAngle()
    {
        Location startLoc = player.getEyeLocation();
        // vector: start to destination
        Vector b = destination.loc.toVector().subtract(startLoc.toVector()).normalize();
        // vector: start to looking direction (a.length == b.length)
        Vector a = player.getEyeLocation().getDirection().clone().setY(0);
        return calculateAngleBetweenVectors(b, a);
    }

    public void removeDestination()
    {
        destination = null;
        hud.clearFontImagesAndRefresh();
        holder.sendUpdate();
    }

    public void setDestination(Destination destination)
    {
        this.destination = destination;

        refreshRender();
        PlayerData.sendPacket(holder, true);
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
