package de.vexo.lobbysystem.listeners;

import de.vexo.lobbysystem.LobbyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (LobbyPlugin.get().isInLobbyWorld(p)) {
            e.setCancelled(true);
            p.updateInventory();
        }
    }
}
