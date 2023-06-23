package net.exotia.plugins.exotiaqueue.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    private static final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(implementColors(message));
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(implementColors(message));
    }

    public static String implementColors(String message) {
        if (message == null) return null;
        for (Matcher matcher = hexPattern.matcher(message); matcher.find(); matcher = hexPattern.matcher(message)) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
        }
        return ChatColor.translateAlternateColorCodes('&', message.replace("<<", "«").replace(">>", "»"));
    }

    public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> implementColors(List<String> message) {
        if (message == null) return null;
        List<String> messages = new ArrayList<>();
        message.forEach(s -> messages.add(implementColors(s)));
        return messages;
    }
    public static String removeColor(String message) {
        return ChatColor.stripColor(message);
    }
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message, String subtitle) {
        player.sendTitle(implementColors(message), implementColors(subtitle), fadeIn, stay, fadeOut);
    }
    public static void sendActionbar(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(implementColors(message)));
    }
    public static void clearTitle(Player player) {
        sendTitle(player, 0, 0, 0, "", "");
    }
}