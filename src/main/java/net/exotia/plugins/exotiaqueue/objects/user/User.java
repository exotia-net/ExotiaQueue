package net.exotia.plugins.exotiaqueue.objects.user;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {
    private static final String BAR_TITLE = "Twoja pozycja: {position}/{total}";
    private UUID uniqueId;
    private int position;
    private BossBar bossBar;
    private String server;

    public User(Player player) {
        this.uniqueId = player.getUniqueId();
        this.position = 0;
    }

    public void createBossBar(Queue queue) {
        this.bossBar = Bukkit.createBossBar(this.formatTitle(queue), BarColor.GREEN, BarStyle.SOLID);
        this.bossBar.setProgress(this.getProgress(queue));
        this.bossBar.addPlayer(Bukkit.getPlayer(this.uniqueId));
    }
    public void destroyBossBar() {
        this.bossBar.removeAll();
    }
    public void updateBossBar(Queue queue) {
        if (this.bossBar == null) return;
        this.bossBar.setTitle(this.formatTitle(queue));
        this.bossBar.setProgress(this.getProgress(queue));
    }
    private double getProgress(Queue queue) {
        return (double) this.position /(queue == null ? 0 : queue.getPlayers().size());
    }
    private String formatTitle(Queue queue) {
        return BAR_TITLE
                .replace("{position}", String.valueOf(this.position))
                .replace("{total}", String.valueOf(queue == null ? 0 : queue.getPlayers().size())
        );
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getPosition() {
        return position;
    }

    public String getServer() {
        return server;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
