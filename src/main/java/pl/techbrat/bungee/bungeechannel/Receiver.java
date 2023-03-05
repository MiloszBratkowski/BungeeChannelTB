package pl.techbrat.bungee.bungeechannel;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Receiver implements Listener {
    private BungeeChannelTB plugin;
    public Receiver(BungeeChannelTB plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        if (event.getTag().equals("techbrat:channel")) {
            plugin.debugLog("Received data from Spigot server: " + new String(event.getData()));
            for (ServerInfo server : plugin.getProxy().getServers().values()) {
                plugin.debugLog("Sent data to "+server.getName());
                server.sendData("techbrat:channel", event.getData(), false);
            }
        }
    }
}
