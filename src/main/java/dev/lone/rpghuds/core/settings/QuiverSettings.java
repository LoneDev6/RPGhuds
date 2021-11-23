package dev.lone.rpghuds.core.settings;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;

import java.util.HashSet;

import static dev.lone.rpghuds.utils.ItemsAdderWrapper.getFontImage;

public class QuiverSettings extends IconAmountSettings
{
    public final FontImageWrapper icon_half;
    public final FontImageWrapper icon_empty;
    public final int offsetOffhand;

    public QuiverSettings(String namespacedID,
                          String icon,
                          String icon_half,
                          String icon_empty,
                          String digit_0,
                          String digit_1,
                          String digit_2,
                          String digit_3,
                          String digit_4,
                          String digit_5,
                          String digit_6,
                          String digit_7,
                          String digit_8,
                          String digit_9,
                          String char_unknown,
                          int offsetMainHand,
                          int offsetOffhand,
                          HashSet<String> worlds
    )
    {
        super(
                namespacedID,
                icon,
                digit_0,
                digit_1,
                digit_2,
                digit_3,
                digit_4,
                digit_5,
                digit_6,
                digit_7,
                digit_8,
                digit_9,
                char_unknown,
                offsetMainHand,
                worlds
        );

        this.icon_half = getFontImage(icon_half);
        this.icon_empty = getFontImage(icon_empty);
        this.offsetOffhand = offsetOffhand;
    }
}
