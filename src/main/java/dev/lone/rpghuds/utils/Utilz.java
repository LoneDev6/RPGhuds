package dev.lone.rpghuds.utils;

public class Utilz
{
    public static String color(String msg)
    {
        return msg.replace("&", "\u00A7");
    }

    public static int parseInt(String number, int defaultValue)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch (Exception ignored){}
        return defaultValue;
    }
}
