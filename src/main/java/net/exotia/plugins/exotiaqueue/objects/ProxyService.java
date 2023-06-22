package net.exotia.plugins.exotiaqueue.objects;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ProxyService {
    @Inject private Plugin plugin;
    public boolean sendPlayer(Player player, String server) {
        try {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            output.writeUTF("Connect");
            output.writeUTF(server.toLowerCase());
            player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
}
