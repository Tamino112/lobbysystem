package de.vexo.lobbysystem.commands;

import de.vexo.lobbysystem.LobbyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LobbyPlugin.get().reloadConfig();
        sender.sendMessage(color(LobbyPlugin.get().getConfig().getString("messages.prefix")) +
                color(LobbyPlugin.get().getConfig().getString("messages.reloaded")));
        return true;
    }

    private String color(String s) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', s == null ? "" : s);
    }
}
