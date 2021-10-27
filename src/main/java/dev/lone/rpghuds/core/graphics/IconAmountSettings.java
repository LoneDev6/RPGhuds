package dev.lone.rpghuds.core.graphics;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;

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
    public final FontImageWrapper unknown_char;
    private final FontImageWrapper digitK;
    private final FontImageWrapper digitM;
    private final FontImageWrapper digitB;
    private final FontImageWrapper digitT;
    private final FontImageWrapper digitDot;
    private final FontImageWrapper digitComma;

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
                              String unknown_char,
                              String digitK,
                              String digitM,
                              String digitB,
                              String digitT,
                              String digitDot,
                              String digitComma,
                              HashSet<String> worlds
    ) throws NullPointerException
    {
        super(namespacedID, worlds);
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
        this.unknown_char = getFontImage(unknown_char);
        this.digitK = getFontImage(digitK);
        this.digitM = getFontImage(digitM);
        this.digitB = getFontImage(digitB);
        this.digitT = getFontImage(digitT);
        this.digitDot = getFontImage(digitDot);
        this.digitComma = getFontImage(digitComma);
    }

    public List<FontImageWrapper> amountToImages(String amount, List<FontImageWrapper> list)
    {
        for (int i = 0; i < amount.length(); i++)
        {
            char c = amount.charAt(i);
            switch (c)
            {
                case '0':
                    list.add(digit_0);
                    break;
                case '1':
                    list.add(digit_1);
                    break;
                case '2':
                    list.add(digit_2);
                    break;
                case '3':
                    list.add(digit_3);
                    break;
                case '4':
                    list.add(digit_4);
                    break;
                case '5':
                    list.add(digit_5);
                    break;
                case '6':
                    list.add(digit_6);
                    break;
                case '7':
                    list.add(digit_7);
                    break;
                case '8':
                    list.add(digit_8);
                    break;
                case '9':
                    list.add(digit_9);
                    break;
                case 'k':
                case 'K':
                    list.add(digitK);
                    break;
                case 'm':
                case 'M':
                    list.add(digitM);
                    break;
                case 'b':
                case 'B':
                    list.add(digitB);
                    break;
                case 't':
                case 'T':
                    list.add(digitT);
                    break;
                case '.':
                    list.add(digitDot);
                    break;
                case ',':
                    list.add(digitComma);
                    break;
                default:
                    list.add(unknown_char);
                    break;
            }
        }
        return list;
    }
}
