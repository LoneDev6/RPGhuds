package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.settings.ArrowTargetSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ArrowTargetHud extends Hud<ArrowTargetSettings>
{
    private final Player player;

    BukkitTask hideSchedule;

    private int accuracy = -1;
    private int prevAccuracy;

    public ArrowTargetHud(PlayerHudsHolderWrapper holder,
                          ArrowTargetSettings settings) throws NullPointerException
    {
        super(holder, settings);
        this.player = holder.getPlayer();

        hud.setVisible(true);
    }

    @Override
    public RenderAction refreshRender()
    {
        return refreshRender(false);
    }

    @Override
    public RenderAction refreshRender(boolean forceRender)
    {
        if (hidden)
            return RenderAction.HIDDEN;

        if (!forceRender && hideSchedule == null)
            return RenderAction.HIDDEN;

        //TODO: better abstract logic: HudDataProvider ???
        if (!forceRender && accuracy == prevAccuracy)
            return RenderAction.SAME_AS_BEFORE;

        if (!hudSettings.worlds.contains(player.getWorld().getName()))
        {
            hud.setVisible(false);
            return RenderAction.HIDDEN;
        }

        imgsBuffer.clear();

        hudSettings.appendAmountToImages(String.valueOf(accuracy) + '%', imgsBuffer);
        imgsBuffer.add(hudSettings.icon);

        hud.setFontImages(imgsBuffer);

        adjustOffset();

        prevAccuracy = accuracy;

        return RenderAction.SEND_REFRESH;
    }

    @Override
    public void deleteRender()
    {
        hud.clearFontImagesAndRefresh();

        if(hideSchedule != null)
            hideSchedule.cancel();
        hideSchedule = null;
    }

    public void setDamage(int damage)
    {
        //TODO:
        // it has no sense to use accuracy for entities because what should I check? Headshot accuracy? idk.
        // instead I show the damage dealt to the entity
    }

    public void setAccuracy(int power)
    {
        this.accuracy = power * 100 / 15;

        refreshRender(true);
        holder.sendUpdate();

        if(hideSchedule != null)
            hideSchedule.cancel();

        hideSchedule = Bukkit.getScheduler().runTaskLaterAsynchronously(Main.inst(), () -> {

            hud.clearFontImagesAndRefresh();
            holder.sendUpdate();

            if(hideSchedule != null)
            {
                hideSchedule.cancel();
                hideSchedule = null;
            }

        }, 20 * 3);
    }
}
