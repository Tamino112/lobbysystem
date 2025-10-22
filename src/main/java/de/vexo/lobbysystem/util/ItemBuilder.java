package de.vexo.lobbysystem.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static ItemStack simple(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color(name));
            meta.setLore(lore.stream().map(ItemBuilder::color).collect(Collectors.toList()));
            item.setItemMeta(meta);
        }
        return item;
    }
}
