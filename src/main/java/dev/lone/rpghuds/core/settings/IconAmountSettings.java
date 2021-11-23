package dev.lone.rpghuds.core.settings;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static dev.lone.rpghuds.utils.ItemsAdderWrapper.getFontImage;

public abstract class IconAmountSettings extends HudSettings
{
    public final FontImageWrapper icon;
    public final FontImageWrapper digit_0;
    public final FontImageWrapper digit_1;
    public final FontImageWrapper digit_2;
    public final FontImageWrapper digit_3;
    public final FontImageWrapper digit_4;
    public final FontImageWrapper digit_5;
    public final FontImageWrapper digit_6;
    public final FontImageWrapper digit_7;
    public final FontImageWrapper digit_8;
    public final FontImageWrapper digit_9;
    public final FontImageWrapper char_unknown;

    final HashMap<Character, FontImageWrapper> charMap = new HashMap<>();

    IconAmountSettings(String namespacedID,
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
                              int initialOffsetX,
                              HashSet<String> worlds
    ) throws NullPointerException
    {
        super(namespacedID, initialOffsetX, worlds);
        this.icon = getFontImage(icon);
        this.digit_0 = getFontImage(digit_0);
        this.digit_1 = getFontImage(digit_1);
        this.digit_2 = getFontImage(digit_2);
        this.digit_3 = getFontImage(digit_3);
        this.digit_4 = getFontImage(digit_4);
        this.digit_5 = getFontImage(digit_5);
        this.digit_6 = getFontImage(digit_6);
        this.digit_7 = getFontImage(digit_7);
        this.digit_8 = getFontImage(digit_8);
        this.digit_9 = getFontImage(digit_9);
        this.char_unknown = getFontImage(char_unknown);


        charMap.put('0', this.digit_0);
        charMap.put('1', this.digit_1);
        charMap.put('2', this.digit_2);
        charMap.put('3', this.digit_3);
        charMap.put('4', this.digit_4);
        charMap.put('5', this.digit_5);
        charMap.put('6', this.digit_6);
        charMap.put('7', this.digit_7);
        charMap.put('8', this.digit_8);
        charMap.put('9', this.digit_9);
    }

    /**
     * Appends the FontImages representation of the provided amount String to the provided FontImages list.
     * @param amount amount string (example: 25.3M)
     * @param list FontImages list of the HUD.
     */
    public void appendAmountToImages(String amount, List<FontImageWrapper> list)
    {
        FontImageWrapper img;
        char c;
        for (int i = 0; i < amount.length(); i++)
        {
            c = amount.charAt(i);
            img = charMap.get(c);
            if(img == null)
                list.add(char_unknown);
            else
                list.add(img);
        }
    }
}
