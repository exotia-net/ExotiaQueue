package net.exotia.plugins.exotiaqueue.schedulers;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.objects.ProxyService;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.User;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class QueueScheduler implements Runnable {
    @Inject private QueueService queueService;
    @Inject private UserService userService;
    @Inject private ProxyService proxyService;

    @Override
    public void run() {
        List.of("survival", "test_instance").forEach(server -> {
            Queue queue = this.queueService.getQueue(server);
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (queue.getPlayers().contains(player.getUniqueId())) {
                    User user = this.userService.getUser(player);
                    if (user.getPosition() == 1) {
                        user.setServer(null);
                        user.setPosition(0);
                        player.sendMessage("Polaczono z serwerem " + server);
                        // Tu gracz ma byc przesylany na serwer
                        user.destroyBossBar();
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
                user.updateBossBar(queue);
                player.sendMessage("Twoja poazycja: " + position + " na " + queue.getPlayers().size());
            }
        });
    }
}