package de.vexo.lobbysystem.commands;

import de.vexo.lobbysystem.LobbyPlugin;
import de.vexo.lobbysystem.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(ItemBuilder.color("&cNur Spieler!"));
            return true;
        }
        if (!p.hasPermission("lobby.admin")) {
            p.sendMessage(ItemBuilder.color(LobbyPlugin.get().getConfig().getString("messages.no-perm")));
            return true;
        }

        Location loc = p.getLocation();
        var cfg = LobbyPlugin.get().getConfig();
        cfg.set("spawn.world", loc.getWorld().getName());
        cfg.set("spawn.x", loc.getX());
        cfg.set("spawn.y", loc.getY());
        cfg.set("spawn.z", loc.getZ());
        cfg.set("spawn.yaw", loc.getYaw());
        cfg.set("spawn.pitch", loc.getPitch());
        LobbyPlugin.get().saveConfig();

        p.sendMessage(ItemBuilder.color(LobbyPlugin.get().getConfig().getString("messages.spawn-set")));
        return true;
    }
}
