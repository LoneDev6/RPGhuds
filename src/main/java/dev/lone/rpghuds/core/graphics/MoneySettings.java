package dev.lone.rpghuds.core.graphics;

import java.util.HashSet;

public class MoneySettings extends IconAmountSettings
{
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
                         String unknown_char,
                         String digitK,
                         String digitM,
                         String digitB,
                         String digitT,
                         String digitDot,
                         String digitComma,
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
                unknown_char,
                digitK,
                digitM,
                digitB,
                digitT,
                digitDot,
                digitComma,
                worlds
        );
    }

}
