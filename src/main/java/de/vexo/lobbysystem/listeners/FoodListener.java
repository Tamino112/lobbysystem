package de.vexo.lobbysystem.listeners;

import de.vexo.lobbysystem.LobbyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.entity.Player;

public class FoodListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (LobbyPlugin.get().isInLobbyWorld(p)) {
                e.setCancelled(true);
                p.setFoodLevel(20);
            }
        }
    }
}
