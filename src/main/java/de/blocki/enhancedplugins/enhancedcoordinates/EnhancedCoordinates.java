package de.blocki.enhancedplugins.enhancedcoordinates;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnhancedCoordinates extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
