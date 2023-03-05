package pl.techbrat.bungee.bungeechannel;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Level;

public final class BungeeChannelTB extends Plugin {

    private Configuration config;

    @Override
    public void onEnable() {
        getLogger().info("Enabling plugin...");
        getProxy().registerChannel( "techbrat:channel" );
        this.getProxy().getPluginManager().registerListener(this, new Receiver(this));
        getLogger().info( "Bungee channel for bungee-spigot plugins enabled!");
        makeConfig();
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogger().info("Plugin successfully enabled!");
    }
    @Override
    public void onDisable() {
        getProxy().unregisterChannel("techbrat:channel");
        getLogger().info("Plugin disabled!");
    }

    private void makeConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
                getLogger().info("Created config folder.");
            }

            File config = new File(getDataFolder(), "config.yml");

            if (!config.exists()) {
                getResourceAsStream("config.yml").transferTo(Files.newOutputStream(config.toPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDebug() {
        return config.getBoolean("debug");
    }

    public boolean debugLog(String debug) {
        if (isDebug()) {
            getLogger().log(Level.INFO, debug);
        }
        return isDebug();
    }

}
