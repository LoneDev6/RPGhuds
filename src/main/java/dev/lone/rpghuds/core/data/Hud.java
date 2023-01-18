package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.core.settings.HudSettings;

import java.util.ArrayList;
import java.util.List;

public abstract class Hud<T extends HudSettings>
{
    final public T hudSettings;
    boolean hidden;

    public final PlayerHudsHolderWrapper holder;
    final PlayerCustomHudWrapper hud;
    int initialXOffset;

    final List<FontImageWrapper> imgsBuffer;

    public Hud(PlayerHudsHolderWrapper holder, T settings)
    {
        this.imgsBuffer = new ArrayList<>();
        this.holder = holder;
        this.hud = settings.newInstanceByPlayer(holder);
        this.hudSettings = settings;
        this.initialXOffset = settings.initialOffsetX;
        hud.setOffsetX(initialXOffset);
    }

    public abstract RenderAction refreshRender(boolean force);
    public abstract RenderAction refreshRender();

    public abstract void deleteRender();

    public void hide(boolean hide)
    {
        hud.setVisible(!hide);
        refreshRender(); // Is this call needed?
    }

    /**
     * Call this if:
     * - HUD is on the left part of the screen and has text on the left side of the HUD
     * - HUD is on the right part of the screen and has text on the right side of the HUD
     */
    public void adjustOffset()
    {
        adjustOffset(initialXOffset);
    }

    /**
     * Call this if:
     * - HUD is on the left part of the screen and has text on the left side of the HUD
     * - HUD is on the right part of the screen and has text on the right side of the HUD
     *
     * @param initialOffset initial X offset of the HUD.
     */
    public void adjustOffset(int initialOffset)
    {
        int offset = initialOffset;
        for (FontImageWrapper img : imgsBuffer)
            offset -= img.getWidth();
        hud.setOffsetX(offset);

        // To refresh calculations
        // (to fix this https://github.com/LoneDev6/RPGhuds/issues/11)
        PlayerData.sendPacket(holder, true);
    }

    enum RenderAction
    {
        SEND_REFRESH,
        SAME_AS_BEFORE,
        HIDDEN
    }
}
