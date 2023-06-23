package net.exotia.plugins.exotiaqueue.commands;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.configuration.PluginConfiguration;
import net.exotia.plugins.exotiaqueue.objects.ProxyService;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.User;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import net.exotia.plugins.exotiaqueue.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueCommand implements CommandExecutor {
    @Inject private QueueService queueService;
    @Inject private UserService userService;
    @Inject private ProxyService proxyService;
    @Inject private PluginConfiguration configuration;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        User user = this.userService.getUser(player);

        if (user.getPosition() != 0) {
            MessageUtil.sendMessage(player, this.configuration.getPlayerAlreadyInQueue().replace("{server}", user.getServer()));
            return false;
        }

        String server = strings[0];
        if (this.queueService.getQueue(server) != null) {
            Queue queue = this.queueService.getQueue(server);
            queue.addPlayer(player);
            user.setServer(server);
            int position = queue.getPlayers().size();
            user.setPosition(position);
            this.userService.createBossBar(user);
            queue.getPlayers().forEach(queuedPlayer -> {
                User queuedUser = this.userService.getUser(Bukkit.getPlayer(queuedPlayer));
                this.userService.updateBossBar(queuedUser);
            });
//            player.sendMessage("Twoja poazycja: " + position + " na " + queue.getPlayers().size());
        }
        return false;
    }
}
