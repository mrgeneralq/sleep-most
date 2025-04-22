package me.mrgeneralq.shared.messaging;

import me.mrgeneralq.shared.versioning.ServerVersion;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatColorUtils
{
    //Container of static methods
    private ChatColorUtils(){}

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String colorize(String text)
    {
        text = colorizeChatColors(text);

        if(ServerVersion.CURRENT_VERSION.supportsHexColors())
            text = colorizeHexColors(text);

        return text;
    }
    public static String colorizeChatColors(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static String colorizeHexColors(String text)
    {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(text);

        while(matcher.find())
        {
            String color = text.substring(matcher.start(), matcher.end());

            //replace the found color to what spigot supports
            text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString());

            //create a matcher for the updated message
            matcher = HEX_COLOR_PATTERN.matcher(text);
        }
        return text;
    }
}