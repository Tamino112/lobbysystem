package de.vexo.lobbysystem.gui;

import de.vexo.lobbysystem.LobbyPlugin;
import de.vexo.lobbysystem.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class NavigatorGUI {

    public static void open(Player p) {
        LobbyPlugin plugin = LobbyPlugin.get();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("navigator");

        if (section == null) {
            p.sendMessage("§cNavigator nicht konfiguriert!");
            return;
        }

        String title = ItemBuilder.color(section.getString("title", "&bNavigator"));
        Inventory inv = Bukkit.createInventory(null, 27, title);

        ConfigurationSection slots = section.getConfigurationSection("slots");
        if (slots != null) {
            Set<String> keys = slots.getKeys(false);
            for (String key : keys) {
                ConfigurationSection s = slots.getConfigurationSection(key);
                if (s == null) continue;

                Material mat = Material.matchMaterial(s.getString("material", "STONE"));
                String name = s.getString("name", "&7Unbekannt");
                List<String> lore = s.getStringList("lore");
                int slot = Integer.parseInt(key);

                inv.setItem(slot, ItemBuilder.simple(mat, name, lore));
            }
        }

        p.openInventory(inv);
    }

    public static boolean isNavigator(String title) {
        LobbyPlugin plugin = LobbyPlugin.get();
        String cfgTitle = ItemBuilder.color(plugin.getConfig().getString("navigator.title", "&bNavigator"));
        return ItemBuilder.color(title).equals(cfgTitle);
    }

    public static void handleClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

        e.setCancelled(true);
        LobbyPlugin plugin = LobbyPlugin.get();
        ConfigurationSection slots = plugin.getConfig().getConfigurationSection("navigator.slots");
        if (slots == null) return;

        for (String key : slots.getKeys(false)) {
            ConfigurationSection s = slots.getConfigurationSection(key);
            if (s == null) continue;

            Material mat = Material.matchMaterial(s.getString("material", "STONE"));
            if (mat == null || e.getCurrentItem().getType() != mat) continue;

            String type = s.getString("type", "spawn");
            String target = s.getString("target", "");

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

            switch (type.toLowerCase()) {
                case "server" -> {
                    // BungeeCord-Server
                    p.sendMessage(ItemBuilder.color(plugin.getConfig().getString("messages.prefix")) +
                            "§7Verbinde zu §b" + target + "§7...");
                    plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            "send " + p.getName() + " " + target);
                }

                case "world" -> {
                    if (Bukkit.getWorld(target) != null) {
                        p.teleport(Bukkit.getWorld(target).getSpawnLocation());
                        p.sendMessage(ItemBuilder.color(plugin.getConfig().getString("messages.teleport-spawn")));
                    } else {
                        p.sendMessage("§cWelt nicht gefunden: " + target);
                    }
                }

                case "spawn" -> {
                    p.performCommand("spawn");
                }

                case "command" -> {
                    // Führt den angegebenen Command als Spieler aus
                    if (target.isEmpty()) {
                        p.sendMessage("§cKein Command definiert.");
                        return;
                    }
                    p.closeInventory();
                    Bukkit.getScheduler().runTask(plugin, () -> p.performCommand(target));
                }

                default -> {
                    p.sendMessage("§cUnbekannter Typ: " + type);
                }
            }
            break;
        }
    }
}
