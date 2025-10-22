package de.vexo.lobbysystem.commands;

import de.vexo.lobbysystem.LobbyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SilentLobbyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean current = LobbyPlugin.get().isSilentLobby();
        if (args.length == 0 || args[0].equalsIgnoreCase("toggle")) {
            LobbyPlugin.get().setSilentLobby(!current);
        } else if (args[0].equalsIgnoreCase("on")) {
            LobbyPlugin.get().setSilentLobby(true);
        } else if (args[0].equalsIgnoreCase("off")) {
            LobbyPlugin.get().setSilentLobby(false);
        } else {
            sender.sendMessage("/silentlobby [on|off|toggle]");
            return true;
        }
        String key = LobbyPlugin.get().isSilentLobby() ? "messages.silent-on" : "messages.silent-off";
        String msg = LobbyPlugin.get().getConfig().getString("messages.prefix", "") +
                LobbyPlugin.get().getConfig().getString(key, "");
        sender.sendMessage(color(msg));
        return true;
    }

    private String color(String s) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', s == null ? "" : s);
    }
}
