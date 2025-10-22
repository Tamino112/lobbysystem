package de.vexo.lobbysystem.listeners;

import de.vexo.lobbysystem.LobbyPlugin;
import de.vexo.lobbysystem.util.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (LobbyPlugin.get().isSilentLobby() && LobbyPlugin.get().isInLobbyWorld(p)) {
            e.joinMessage(null);
        }

        // Setup player state for lobby
        if (LobbyPlugin.get().isInLobbyWorld(p)) {
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            if (LobbyPlugin.get().getConfig().getBoolean("items.give-on-join", true)) {
                giveLobbyItems(p);
            }
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setHealth(20.0);
            p.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (LobbyPlugin.get().isSilentLobby() && LobbyPlugin.get().isInLobbyWorld(e.getPlayer())) {
            e.quitMessage(null);
        }
    }

    private void giveLobbyItems(Player p) {
        var cfg = LobbyPlugin.get().getConfig();
        int nav = cfg.getInt("items.navigator-slot", 0);
        int hide = cfg.getInt("items.hideplayers-slot", 1);
        int extras = cfg.getInt("items.extras-slot", 4);
        int shield = cfg.getInt("items.shield-slot", 7);
        int profile = cfg.getInt("items.profile-slot", 8);

        if (nav >= 0) p.getInventory().setItem(nav, ItemBuilder.simple(Material.COMPASS, "&bNavigator", Arrays.asList("&7Klicke zum Öffnen")));
        if (hide >= 0) p.getInventory().setItem(hide, ItemBuilder.simple(Material.ENDER_EYE, "&dSpieler verstecken", Arrays.asList("&7Toggle")));
        if (extras >= 0) p.getInventory().setItem(extras, ItemBuilder.simple(Material.BLAZE_ROD, "&6Extras", Arrays.asList("&7Gadgets")));
        if (shield >= 0) p.getInventory().setItem(shield, ItemBuilder.simple(Material.SHIELD, "&aSchutzschild", Arrays.asList("&7Toggle")));
        if (profile >= 0) p.getInventory().setItem(profile, ItemBuilder.simple(Material.BOOK, "&eProfil", Arrays.asList("&7Info")));

        if (p.hasPermission("lobby.ytitems")) {
            p.getInventory().addItem(ItemBuilder.simple(Material.FIREWORK_ROCKET, "&cYouTuber Feuerwerk", Arrays.asList("&7Klick = Boom")));
        }
    }
}
