package de.vexo.lobbysystem.gui;

import de.vexo.lobbysystem.LobbyPlugin;
import de.vexo.lobbysystem.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class ExtrasGUI {

    /**
     * Öffnet das Extras-Menü für den Spieler.
     */
    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ItemBuilder.color("&bExtras"));

        inv.setItem(11, ItemBuilder.simple(Material.FEATHER, "&bSpeed", List.of("&7Aktiviere oder deaktiviere Speed")));
        inv.setItem(13, ItemBuilder.simple(Material.RABBIT_FOOT, "&bSprungkraft", List.of("&7Aktiviere oder deaktiviere Jump Boost")));
        inv.setItem(15, ItemBuilder.simple(Material.SHIELD, "&bSchutzschild", List.of("&7Aktiviere oder deaktiviere dein Schild")));

        p.openInventory(inv);
    }

    /**
     * Überprüft, ob das Inventar das Extras-Menü ist.
     */
    public static boolean isExtrasInv(String title) {
        return ItemBuilder.color(title).equals(ItemBuilder.color("&bExtras"));
    }

    /**
     * Reagiert auf Klicks im Extras-Menü.
     */
    public static void handleClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

        e.setCancelled(true);

        UUID uuid = p.getUniqueId();
        LobbyPlugin plugin = LobbyPlugin.get();
        Material type = e.getCurrentItem().getType();

        String prefix = ItemBuilder.color(plugin.getConfig().getString("messages.prefix", "&8[&bLobby&8]&r "));

        // SPEED TOGGLE
        if (type == Material.FEATHER) {
            if (plugin.getSpeed().contains(uuid)) {
                plugin.getSpeed().remove(uuid);
                p.removePotionEffect(PotionEffectType.SPEED);
                p.sendMessage(prefix + ItemBuilder.color(plugin.getConfig().getString("messages.speed-off")));
            } else {
                plugin.getSpeed().add(uuid);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                p.sendMessage(prefix + ItemBuilder.color(plugin.getConfig().getString("messages.speed-on")));
            }
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        // JUMP TOGGLE
        else if (type == Material.RABBIT_FOOT) {
            if (plugin.getJump().contains(uuid)) {
                plugin.getJump().remove(uuid);
                p.removePotionEffect(PotionEffectType.JUMP_BOOST);
                p.sendMessage(prefix + ItemBuilder.color(plugin.getConfig().getString("messages.jump-off")));
            } else {
                plugin.getJump().add(uuid);
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 1, false, false));
                p.sendMessage(prefix + ItemBuilder.color(plugin.getConfig().getString("messages.jump-on")));
            }
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        // SHIELD TOGGLE
        else if (type == Material.SHIELD) {
            if (plugin.getShields().contains(uuid)) {
                plugin.getShields().remove(uuid);
                p.sendMessage(prefix + ItemBuilder.color(plugin.getConfig().getString("messages.shield-off")));
            } else {
                plugin.getShields().add(uuid);
                p.sendMessage(prefix + ItemBuilder.color(plugin.getConfig().getString("messages.shield-on")));
            }
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }
    }
}
