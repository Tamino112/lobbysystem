package de.vexo.lobbysystem.commands;

import de.vexo.lobbysystem.LobbyPlugin;
import de.vexo.lobbysystem.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(ItemBuilder.color("&cNur Spieler!"));
            return true;
        }
        var cfg = LobbyPlugin.get().getConfig();
        String world = cfg.getString("spawn.world");
        if (world == null || Bukkit.getWorld(world) == null) {
            p.sendMessage(ItemBuilder.color(cfg.getString("messages.spawn-not-set")));
            return true;
        }

        Location loc = new Location(
                Bukkit.getWorld(world),
                cfg.getDouble("spawn.x"),
                cfg.getDouble("spawn.y"),
                cfg.getDouble("spawn.z"),
                (float) cfg.getDouble("spawn.yaw"),
                (float) cfg.getDouble("spawn.pitch")
        );
        p.teleport(loc);
        p.sendMessage(ItemBuilder.color(cfg.getString("messages.teleport-spawn")));
        return true;
    }
}
