package dev.lone.rpghuds.core.settings;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;

import java.util.HashSet;

import static dev.lone.rpghuds.utils.ItemsAdderWrapper.getFontImage;

public class MoneySettings extends IconAmountSettings
{
    public final FontImageWrapper char_k;
    public final FontImageWrapper char_m;
    public final FontImageWrapper char_b;
    public final FontImageWrapper char_t;
    public final FontImageWrapper char_dot;
    public final FontImageWrapper char_comma;
    public final FontImageWrapper char_arrow_up;
    public final FontImageWrapper char_arrow_down;

    public MoneySettings(String namespacedID,
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
                         String char_k,
                         String char_m,
                         String char_b,
                         String char_t,
                         String char_dot,
                         String char_comma,
                         String char_arrow_up,
                         String char_arrow_down,
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

        this.char_k = getFontImage(char_k);
        this.char_m = getFontImage(char_m);
        this.char_b = getFontImage(char_b);
        this.char_t = getFontImage(char_t);
        this.char_dot = getFontImage(char_dot);
        this.char_comma = getFontImage(char_comma);
        this.char_arrow_up = getFontImage(char_arrow_up);
        this.char_arrow_down = getFontImage(char_arrow_down);

        charMap.put('k', this.char_k);
        charMap.put('K', this.char_k);
        charMap.put('m', this.char_m);
        charMap.put('M', this.char_m);
        charMap.put('b', this.char_b);
        charMap.put('B', this.char_b);
        charMap.put('t', this.char_t);
        charMap.put('T', this.char_t);
        charMap.put('.', this.char_dot);
        charMap.put(',', this.char_comma);
    }
}
