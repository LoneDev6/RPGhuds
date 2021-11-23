package dev.lone.rpghuds.core.settings;

import java.util.HashSet;

import static dev.lone.rpghuds.utils.ItemsAdderWrapper.getFontImage;

public class ArrowTargetSettings extends IconAmountSettings
{
    public ArrowTargetSettings(String namespacedID,
                               String icon,
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
                               String char_percentage,
                               int initialOffsetX,
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
                initialOffsetX,
                worlds
        );

        this.charMap.put('%', getFontImage(char_percentage));
    }
}
