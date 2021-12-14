package de.blocki.enhancedplugins.enhancedcoordinates;

import de.blocki.enhancedplugins.enhancedcoordinates.commands.CoordCommand;
import de.blocki.enhancedplugins.enhancedcoordinates.utils.ConfigManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnhancedCoordinates extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        setDefaultConfig();

        CoordCommand cmd = new CoordCommand(ConfigManager.get("prefix").replace("&", "§") + " ");

        getCommand("coords").setExecutor(cmd);
        getCommand("coords").setTabCompleter(cmd);

    }

    private void setDefaultConfig(){
        ConfigManager.setDef("prefix", "&7[&6AC&7]");
        ConfigManager.setDef("round-location", true);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
