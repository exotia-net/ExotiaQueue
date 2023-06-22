package net.exotia.plugins.exotiaqueue.objects.queue;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Queue {
    private String server;
    private Set<UUID> players;

    public Queue(String server) {
        this.server = server;
        this.players = new HashSet<>();
    }

    public String getServer() {
        return server;
    }

    public Set<UUID> getPlayers() {
        return players;
    }
    public void removePlayer(Player player) {
        this.players.remove(player.getUniqueId());
    }
    public void addPlayer(Player player) {
        this.players.add(player.getUniqueId());
    }
}
