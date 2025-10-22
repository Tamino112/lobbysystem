package de.vexo.lobbysystem.listeners;

import de.vexo.lobbysystem.LobbyPlugin;
import de.vexo.lobbysystem.util.ItemBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CoreListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = e.getPlayer();
        if (!LobbyPlugin.get().isInLobbyWorld(p)) return;
        if (p.getItemInHand() == null) return;

        Material type = p.getItemInHand().getType();
        switch (type) {
            case COMPASS -> { e.setCancelled(true); LobbyPlugin.get().openNavigator(p); }
            case ENDER_EYE -> { e.setCancelled(true); toggleHidePlayers(p); }
            case BLAZE_ROD -> { e.setCancelled(true); LobbyPlugin.get().openExtras(p); }
            case SHIELD -> { e.setCancelled(true); toggleShield(p); }
            case BOOK -> { e.setCancelled(true); LobbyPlugin.get().openProfile(p); }
            case FIREWORK_ROCKET -> {
                if (p.hasPermission("lobby.ytitems")) {
                    e.setCancelled(true);
                    p.getWorld().spawn(p.getLocation(), org.bukkit.entity.Firework.class, fw -> {});
                }
            }
            default -> {}
        }
    }

    private void toggleHidePlayers(Player p) {
        var set = LobbyPlugin.get().getHiddenPlayers();
        String prefix = LobbyPlugin.get().getConfig().getString("messages.prefix", "");
        if (set.contains(p.getUniqueId())) {
            set.remove(p.getUniqueId());
            for (Player other : p.getServer().getOnlinePlayers()) p.showPlayer(LobbyPlugin.get(), other);
            p.sendMessage(ItemBuilder.color(prefix + LobbyPlugin.get().getConfig().getString("messages.players-shown", "")));
        } else {
            set.add(p.getUniqueId());
            for (Player other : p.getServer().getOnlinePlayers()) if (!other.equals(p)) p.hidePlayer(LobbyPlugin.get(), other);
            p.sendMessage(ItemBuilder.color(prefix + LobbyPlugin.get().getConfig().getString("messages.players-hidden", "")));
        }
    }

    private void toggleShield(Player p) {
        var set = LobbyPlugin.get().getShields();
        String prefix = LobbyPlugin.get().getConfig().getString("messages.prefix", "");
        if (set.contains(p.getUniqueId())) {
            set.remove(p.getUniqueId());
            p.sendMessage(ItemBuilder.color(prefix + LobbyPlugin.get().getConfig().getString("messages.shield-off", "")));
        } else {
            set.add(p.getUniqueId());
            p.sendMessage(ItemBuilder.color(prefix + LobbyPlugin.get().getConfig().getString("messages.shield-on", "")));
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        if (LobbyPlugin.get().isSilentLobby() && LobbyPlugin.get().isInLobbyWorld(p)) {
            e.setCancelled(true);
            String pref = LobbyPlugin.get().getConfig().getString("messages.prefix", "");
            p.sendMessage(ItemBuilder.color(pref + "&7Chat ist hier deaktiviert."));
        }
    }
}
