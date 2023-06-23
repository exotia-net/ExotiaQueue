package net.exotia.plugins.exotiaqueue.listeners;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.User;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import net.exotia.plugins.exotiaqueue.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
    @Inject private UserService userService;
    @Inject private QueueService queueService;

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.getUser(player);
        if (user.getPosition() != 0) {
            int position = user.getPosition();
            Queue queue = this.queueService.getQueue(user.getServer());
            queue.removePlayer(player);

            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                User queuedUser = this.userService.getUser(onlinePlayer);
                if (queuedUser.getPosition() != 1 && queuedUser.getPosition() >= position && queuedUser.getServer().equalsIgnoreCase(queue.getServer())) {
                    int newPosition = queuedUser.getPosition()-1;
                    queuedUser.setPosition(newPosition);
                    this.userService.updateBossBar(queuedUser);
//                    MessageUtil.sendActionbar(onlinePlayer, "Twoja poazycja: " + newPosition + " na " + queue.getPlayers().size());
//                    onlinePlayer.sendMessage("Twoja poazycja: " + newPosition + " na " + queue.getPlayers().size());
                }
            });
        }
        this.userService.removePlayer(player.getUniqueId());
    }
}
