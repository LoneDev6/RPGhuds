package dev.lone.rpghuds.core.graphics;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;

import java.util.HashMap;
import java.util.HashSet;

import static dev.lone.rpghuds.utils.ItemsAdderWrapper.getFontImage;

public class CompassSettings extends HudSettings
{
    public final HashMap<Integer, FontImageWrapper> compassIcons = new HashMap<>();
    public final FontImageWrapper iconReached;
    public final FontImageWrapper iconDiffWorld;
    public final FontImageWrapper iconNothing;

    public CompassSettings(String namespacedID,
                           String compassPrefix,
                           HashSet<String> worlds
    )
    {
        super(namespacedID, worlds);

        iconReached = getFontImage(compassPrefix + "reached");
        iconDiffWorld = getFontImage(compassPrefix + "diff_world");
        iconNothing = getFontImage(compassPrefix + "nothing");

        compassIcons.put(0, getFontImage(compassPrefix + "16"));
        compassIcons.put(1, getFontImage(compassPrefix + "15"));
        compassIcons.put(2, getFontImage(compassPrefix + "14"));
        compassIcons.put(3, getFontImage(compassPrefix + "13"));
        compassIcons.put(4, getFontImage(compassPrefix + "12"));
        compassIcons.put(5, getFontImage(compassPrefix + "11"));
        compassIcons.put(6, getFontImage(compassPrefix + "10"));
        compassIcons.put(7, getFontImage(compassPrefix + "09"));
        compassIcons.put(8, getFontImage(compassPrefix + "08"));
        compassIcons.put(9, getFontImage(compassPrefix + "07"));
        compassIcons.put(10, getFontImage(compassPrefix + "06"));
        compassIcons.put(11, getFontImage(compassPrefix + "05"));
        compassIcons.put(12, getFontImage(compassPrefix + "04"));
        compassIcons.put(13, getFontImage(compassPrefix + "03"));
        compassIcons.put(14, getFontImage(compassPrefix + "02"));
        compassIcons.put(15, getFontImage(compassPrefix + "01"));
        compassIcons.put(16, getFontImage(compassPrefix + "00"));
        compassIcons.put(-16, getFontImage(compassPrefix + "00"));
        compassIcons.put(-15, getFontImage(compassPrefix + "31"));
        compassIcons.put(-14, getFontImage(compassPrefix + "30"));
        compassIcons.put(-13, getFontImage(compassPrefix + "29"));
        compassIcons.put(-12, getFontImage(compassPrefix + "28"));
        compassIcons.put(-11, getFontImage(compassPrefix + "27"));
        compassIcons.put(-10, getFontImage(compassPrefix + "26"));
        compassIcons.put(-9, getFontImage(compassPrefix + "25"));
        compassIcons.put(-8, getFontImage(compassPrefix + "24"));
        compassIcons.put(-7, getFontImage(compassPrefix + "23"));
        compassIcons.put(-6, getFontImage(compassPrefix + "22"));
        compassIcons.put(-5, getFontImage(compassPrefix + "21"));
        compassIcons.put(-4, getFontImage(compassPrefix + "20"));
        compassIcons.put(-3, getFontImage(compassPrefix + "19"));
        compassIcons.put(-2, getFontImage(compassPrefix + "18"));
        compassIcons.put(-1, getFontImage(compassPrefix + "17"));
    }

}
