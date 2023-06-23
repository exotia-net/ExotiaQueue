package net.exotia.plugins.exotiaqueue.objects.user;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.configuration.PluginConfiguration;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserService {
    @Inject private PluginConfiguration configuration;
    @Inject private QueueService queueService;
    private final Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return this.users;
    }

    public User getUser(Player player) {
        return this.users.stream().filter(user -> user.getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }
    public void addUser(User user) {
        this.users.add(user);
    }
    public void removePlayer(UUID uuid) {
        this.users.removeIf(user -> user.getUniqueId() == uuid);
    }
    public void removePlayer(User user) {
        this.users.remove(user);
    }
    public boolean contains(Player player) {
        return this.users.stream().anyMatch(user -> user.getUniqueId() == player.getUniqueId());
    }

    /*
     * User BossBar
     */
    public void createBossBar(User user) {
        BossBar bossBar = Bukkit.createBossBar(this.formatTitle(user), BarColor.GREEN, BarStyle.SOLID);
        bossBar.setProgress(this.getProgress(user));
        bossBar.addPlayer(Bukkit.getPlayer(user.getUniqueId()));
        user.setBossBar(bossBar);
    }
    public void destroyBossBar(User user) {
        user.getBossBar().removeAll();
    }
    public void updateBossBar(User user) {
        if (user.getBossBar() == null) return;
        user.getBossBar().setTitle(this.formatTitle(user));
        user.getBossBar().setProgress(this.getProgress(user));
    }
    private double getProgress(User user) {
        Queue queue = this.queueService.getQueue(user.getServer());
        return (double) user.getPosition() / (queue == null ? 0 : queue.getPlayers().size());
    }
    private String formatTitle(User user) {
        Queue queue = this.queueService.getQueue(user.getServer());
        return this.configuration.getBossBar().getTitle()
                .replace("{position}", String.valueOf(user.getPosition()))
                .replace("{total}", String.valueOf(queue == null ? 0 : queue.getPlayers().size())
        );
    }
}
