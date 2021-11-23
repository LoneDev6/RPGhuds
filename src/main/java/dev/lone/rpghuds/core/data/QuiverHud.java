package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.settings.QuiverSettings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuiverHud extends Hud<QuiverSettings>
{
    private final Player player;

    private int arrows = -1;
    private int prevArrows;
    private boolean hasWeapon;

    private boolean hiddenNoWeapon;

    public QuiverHud(PlayerHudsHolderWrapper holder,
                     QuiverSettings settings) throws NullPointerException
    {
        super(holder, settings);

        this.player = holder.getPlayer();

        updateOffsetX();
        hud.setOffsetX(initialXOffset);

        calculateHasWeapon();
        calculateArrows();

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
        if (hidden || hiddenNoWeapon)
            return RenderAction.HIDDEN;

        //TODO: better abstract logic: HudDataProvider ???
        if (!forceRender && arrows == prevArrows)
            return RenderAction.SAME_AS_BEFORE;

        if (!hudSettings.worlds.contains(player.getWorld().getName()))
        {
            hud.setVisible(false);
            return RenderAction.HIDDEN;
        }

        imgsBuffer.clear();

        hudSettings.appendAmountToImages(String.valueOf(arrows), imgsBuffer);

        if(arrows == 0)
            imgsBuffer.add(hudSettings.icon_empty);
        else if(arrows <= 32)
            imgsBuffer.add(hudSettings.icon_half);
        else
            imgsBuffer.add(hudSettings.icon);

        hud.setFontImages(imgsBuffer);

        adjustOffset();

        prevArrows = arrows;

        return RenderAction.SEND_REFRESH;
    }

    @Override
    public void deleteRender()
    {
        hud.clearFontImagesAndRefresh();
    }

    private void calculateArrows()
    {
        arrows = 0;
        for (ItemStack itemStack : player.getInventory())
        {
            if(itemStack == null)
                continue;
            Material type = itemStack.getType();
            if(isArrow(type))
                arrows += itemStack.getAmount();
        }
    }

    /**
     * Called when the player holds a weapon which can shoot arrows.
     */
    public void refreshOnWeaponHold(ItemStack weapon)
    {
        hasWeapon = weapon != null && isWeapon(weapon.getType());
        if(!hasWeapon)
            hasWeapon = isWeapon(player.getInventory().getItemInOffHand().getType());

        calculateArrows();
        updateOffsetX();

        hiddenNoWeapon = !hasWeapon;
        hud.setVisible(hasWeapon);

        refreshRender(true);
        PlayerData.sendPacket(holder, true);
    }

    public void refreshArrows()
    {
        calculateArrows();
        updateOffsetX();

        refreshRender(true);
        PlayerData.sendPacket(holder, true);
    }

    public void refreshArrowsAdjust(int changeFactor)
    {
        arrows += changeFactor;
        if(arrows < 0)
            arrows = 0;
        updateOffsetX();
        refreshRender();
        PlayerData.sendPacket(holder, true);
    }

    public static boolean isArrow(Material type)
    {
        return type == Material.ARROW || type == Material.TIPPED_ARROW || type == Material.SPECTRAL_ARROW;
    }

    public static boolean isWeapon(Material type)
    {
        return type == Material.BOW || type == Material.CROSSBOW;
    }

    public static boolean isWeapon(ItemStack item)
    {
        if(item == null)
            return false;

        Material type = item.getType();
        return type == Material.BOW || type == Material.CROSSBOW;
    }

    public static boolean hasWeapon(Player player)
    {
        return isWeapon(player.getInventory().getItemInMainHand()) || isWeapon(player.getInventory().getItemInOffHand());
    }

    public void calculateHasWeapon()
    {
        hiddenNoWeapon = false;
        hasWeapon = isWeapon(player.getInventory().getItemInMainHand().getType())
                || isWeapon(player.getInventory().getItemInOffHand().getType());
    }

    private void updateOffsetX()
    {
        updateOffsetX(player.getInventory().getItemInOffHand().getType() != Material.AIR);
    }

    public void updateOffsetX(boolean isOffhandFull)
    {
        initialXOffset = isOffhandFull ? Main.settings.quiverOffsetWhenOffhandShown : Main.settings.quiverOffset;
    }
}
