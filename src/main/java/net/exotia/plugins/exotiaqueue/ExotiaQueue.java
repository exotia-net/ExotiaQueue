package net.exotia.plugins.exotiaqueue;

import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import net.exotia.plugins.exotiaqueue.commands.QueueCommand;
import net.exotia.plugins.exotiaqueue.listeners.BungeeMessagingListener;
import net.exotia.plugins.exotiaqueue.listeners.PlayerJoinListener;
import net.exotia.plugins.exotiaqueue.listeners.PlayerLeaveListener;
import net.exotia.plugins.exotiaqueue.objects.ProxyService;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import net.exotia.plugins.exotiaqueue.schedulers.QueueScheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Stream;

public final class ExotiaQueue extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this);
        this.injector.registerInjectable(this.injector.createInstance(ProxyService.class));
        QueueService queueService = new QueueService();
        List.of("survival", "test_instance").forEach(queueService::addQueue);
        this.injector.registerInjectable(queueService);
        this.injector.registerInjectable(this.injector.createInstance(UserService.class));

        this.getCommand("queue").setExecutor(this.injector.createInstance(QueueCommand.class));
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, this.injector.createInstance(QueueScheduler.class), 0L, 120L);

        Stream.of(
                this.injector.createInstance(PlayerLeaveListener.class),
                this.injector.createInstance(PlayerJoinListener.class)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessagingListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
