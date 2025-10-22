package de.vexo.lobbysystem;

import de.vexo.lobbysystem.commands.*;
import de.vexo.lobbysystem.gui.*;
import de.vexo.lobbysystem.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LobbyPlugin extends JavaPlugin {

    private static LobbyPlugin instance;

    // Spielerstatus-Sets
    private final Set<UUID> hiddenPlayers = new HashSet<>();
    private final Set<UUID> shields = new HashSet<>();
    private final Set<UUID> speed = new HashSet<>();
    private final Set<UUID> jump = new HashSet<>();

    // SilentLobby-Status
    private boolean silentLobby;

    public static LobbyPlugin get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.silentLobby = getConfig().getBoolean("silent-lobby-enabled", true);

        // Commands registrieren
        getCommand("silentlobby").setExecutor(new SilentLobbyCommand());
        getCommand("lobbyreload").setExecutor(new ReloadCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());

        // Listener registrieren
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new CoreListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemDropListener(), this);

        // BungeeCord-Kommunikation aktivieren
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Start-Log
        logBanner("GESTARTET");
    }

    @Override
    public void onDisable() {
        // Stop-Log
        logBanner("GESTOPPT");
    }

    /**
     * Farbige Start-/Stop-Meldung
     */
    private void logBanner(String state) {
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String GREEN = "\u001B[32m";
        final String CYAN = "\u001B[36m";
        final String YELLOW = "\u001B[33m";

        getLogger().info(BLUE + "====================================" + RESET);
        getLogger().info(GREEN + "        PLUGIN " + state + RESET);
        getLogger().info(CYAN + "        Plugin: LobbySystem" + RESET);
        getLogger().info(YELLOW + "        Version: 1.1.0" + RESET);
        getLogger().info(CYAN + "        Autor: vexo5566" + RESET);
        getLogger().info(BLUE + "====================================" + RESET);
    }

    /**
     * Überprüft, ob der Spieler in der Lobby-Welt ist
     */
    public boolean isInLobbyWorld(Player p) {
        String lobbyWorld = getConfig().getString("lobby-world", "world");
        World w = Bukkit.getWorld(lobbyWorld);
        return w != null && p.getWorld().equals(w);
    }

    // Getter & Setter

    public boolean isSilentLobby() {
        return silentLobby;
    }

    public void setSilentLobby(boolean value) {
        this.silentLobby = value;
        getConfig().set("silent-lobby-enabled", value);
        saveConfig();
    }

    public Set<UUID> getHiddenPlayers() {
        return hiddenPlayers;
    }

    public Set<UUID> getShields() {
        return shields;
    }

    public Set<UUID> getSpeed() {
        return speed;
    }

    public Set<UUID> getJump() {
        return jump;
    }

    // GUI-Öffner

    public void openNavigator(Player p) {
        NavigatorGUI.open(p);
    }

    public void openExtras(Player p) {
        ExtrasGUI.open(p);
    }

    public void openProfile(Player p) {
        ProfileGUI.open(p);
    }
}
