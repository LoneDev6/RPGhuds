package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.core.graphics.MoneySettings;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import static dev.lone.rpghuds.utils.ItemsAdderWrapper.getFontImage;

public class MoneyHud extends PAPIHud<MoneySettings>
{
    private final PlayerHudsHolderWrapper holder;
    private final PlayerCustomHudWrapper hud;

    private final Player player;

    private final FontImageWrapper shift_1;
    private final FontImageWrapper shift_2;

    private String prevAmount;

    public MoneyHud(String placeholder,
                    long refreshIntervalTicks,
                    PlayerHudsHolderWrapper holder,
                    MoneySettings graphics) throws NullPointerException
    {
        super(placeholder, refreshIntervalTicks);
        this.holder = holder;
        this.hudSettings = graphics;

        this.hud = graphics.newInstanceByPlayer(holder);
        this.player = holder.getPlayer();

        this.shift_1 = getFontImage("rpghuds:shift_1");
        this.shift_2 = getFontImage("rpghuds:shift_2");
    }

    @Override
    public RenderAction refreshRender()
    {
        hud.setVisible(!hidden);
        if (hidden)
            return RenderAction.SKIP;

        if (!hudSettings.worlds.contains(player.getWorld().getName()))
        {
            hud.setVisible(false);
            return RenderAction.SKIP;
        }

        //TODO: better abstract logic: HudDataProvider ???
        String amount = PlaceholderAPI.setPlaceholders(holder.getPlayer(), placeholder);
        if (amount.equals(prevAmount))
            return RenderAction.SAME_AS_BEFORE;

        imgsBuffer.clear();

        hudSettings.amountToImages(amount, imgsBuffer);
        imgsBuffer.add(shift_1);
        imgsBuffer.add(hudSettings.icon);

        hud.setFontImages(imgsBuffer);

        int offset = hud.getInitialOffsetX();
        for (FontImageWrapper img : imgsBuffer)
            offset -= img.getWidth();
        hud.setOffsetX(offset);

        prevAmount = amount;

        return RenderAction.RENDERED;
    }

    @Override
    public void deleteRender()
    {
        hud.clearFontImagesAndRefresh();
    }
}
