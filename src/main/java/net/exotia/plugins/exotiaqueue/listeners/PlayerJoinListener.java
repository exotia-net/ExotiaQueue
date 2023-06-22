package net.exotia.plugins.exotiaqueue.listeners;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.User;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @Inject private UserService userService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        User user = new User(event.getPlayer());
        this.userService.addUser(user);
    }
}
