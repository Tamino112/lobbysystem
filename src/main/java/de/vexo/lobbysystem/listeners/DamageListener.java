package de.vexo.lobbysystem.listeners;

import de.vexo.lobbysystem.LobbyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (!LobbyPlugin.get().isInLobbyWorld(p)) return;

        // Disable all non-void damage in the lobby
        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player victim)) return;
        if (!LobbyPlugin.get().isInLobbyWorld(victim)) return;

        if (LobbyPlugin.get().getShields().contains(victim.getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
