package net.exotia.plugins.exotiaqueue.schedulers;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.configuration.PluginConfiguration;
import net.exotia.plugins.exotiaqueue.objects.ProxyService;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.User;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import net.exotia.plugins.exotiaqueue.utils.MessageUtil;
import org.bukkit.Bukkit;

public class QueueScheduler implements Runnable {
    @Inject private QueueService queueService;
    @Inject private UserService userService;
    @Inject private ProxyService proxyService;
    @Inject private PluginConfiguration configuration;

    @Override
    public void run() {
        this.configuration.getAvailableServers().forEach(server -> {
            Queue queue = this.queueService.getQueue(server);
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (queue.getPlayers().contains(player.getUniqueId())) {
                    User user = this.userService.getUser(player);
                    if (user.getPosition() == 1) {
                        user.setServer(null);
                        user.setPosition(0);
                        MessageUtil.sendMessage(player, this.configuration.getSuccessfullyConnected().replace("{server}", server));
                        this.proxyService.sendPlayer(player, server);
                        this.userService.destroyBossBar(user);
                        queue.removePlayer(player);
                        this.notifyPlayers(queue);
                    }
                }
            });
        });
    }
    private void notifyPlayers(Queue queue) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userService.getUser(player);
            if (user.getServer() == null) return;
            if (user.getServer().equalsIgnoreCase(queue.getServer())) {
                int position = user.getPosition()-1;
                user.setPosition(position);
                this.userService.updateBossBar(user);
            }
        });
    }
}
