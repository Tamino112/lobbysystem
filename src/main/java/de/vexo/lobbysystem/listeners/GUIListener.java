package de.vexo.lobbysystem.listeners;

import de.vexo.lobbysystem.gui.ExtrasGUI;
import de.vexo.lobbysystem.gui.NavigatorGUI;
import de.vexo.lobbysystem.gui.ProfileGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (NavigatorGUI.isNavigator(e.getView().getTitle())) {
            e.setCancelled(true);
            NavigatorGUI.handleClick(e);
        } else if (ExtrasGUI.isExtrasInv(e.getView().getTitle())) {
            e.setCancelled(true);
            ExtrasGUI.handleClick(e);
        } else if (ProfileGUI.isProfile(e.getView().getTitle())) {
            e.setCancelled(true);
        }
    }
}
