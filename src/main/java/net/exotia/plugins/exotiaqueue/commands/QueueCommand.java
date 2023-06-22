package net.exotia.plugins.exotiaqueue.commands;

import eu.okaeri.injector.annotation.Inject;
import net.exotia.plugins.exotiaqueue.objects.ProxyService;
import net.exotia.plugins.exotiaqueue.objects.queue.Queue;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.User;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QueueCommand implements CommandExecutor {
    @Inject private QueueService queueService;
    @Inject private UserService userService;
    @Inject private ProxyService proxyService;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
//        UUID uuid = player.getUniqueId();
//        this.proxyService.sendPlayer(player, strings[0]);
//        if (this.queueService.getPlayers().containsKey(uuid)) {
//            player.sendMessage("Jestes juz w kolejce!");
//            return false;
//        }
//        this.queueService.addPlayer(uuid, strings[0]);
//        player.sendMessage("Dolaczyles do kolejki na serwer " + strings[0]);

        User user = this.userService.getUser(player);
        if (user.getPosition() != 0) {
            player.sendMessage("Jestes juz w kolejce na serwer " + user.getServer());
            return false;
        }
        String server = strings[0];
        if (this.queueService.getQueue(server) != null) {
            Queue queue = this.queueService.getQueue(server);
            queue.addPlayer(player);
            user.setServer(server);
            int position = queue.getPlayers().size();
            user.setPosition(position);
            user.createBossBar(queue);
            player.sendMessage("Twoja poazycja: " + position + " na " + queue.getPlayers().size());
        }
        return false;
    }
}
