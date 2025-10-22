package de.vexo.lobbysystem.gui;

import de.vexo.lobbysystem.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class ProfileGUI {

    private static final String TITLE = ItemBuilder.color("&eProfil");

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, TITLE);

        long ticks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
        long seconds = ticks / 20;
        long minutes = seconds / 60;

        inv.setItem(4, ItemBuilder.simple(Material.PLAYER_HEAD, "&a" + p.getName(),
                Arrays.asList("&7UUID: " + p.getUniqueId(),
                        "&7Spielzeit: " + minutes + " Min")));

        p.openInventory(inv);
    }

    public static boolean isProfile(String title) {
        return ItemBuilder.color(title).equals(TITLE);
    }
}
