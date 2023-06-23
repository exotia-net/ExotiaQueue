package net.exotia.plugins.exotiaqueue;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import net.exotia.plugins.exotiaqueue.commands.QueueCommand;
import net.exotia.plugins.exotiaqueue.configuration.PluginConfiguration;
import net.exotia.plugins.exotiaqueue.listeners.BungeeMessagingListener;
import net.exotia.plugins.exotiaqueue.listeners.PlayerJoinListener;
import net.exotia.plugins.exotiaqueue.listeners.PlayerLeaveListener;
import net.exotia.plugins.exotiaqueue.objects.ProxyService;
import net.exotia.plugins.exotiaqueue.objects.queue.QueueService;
import net.exotia.plugins.exotiaqueue.objects.user.UserService;
import net.exotia.plugins.exotiaqueue.schedulers.QueueScheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class ExotiaQueue extends JavaPlugin {
    private final Injector injector = OkaeriInjector.create();
    private PluginConfiguration configuration;

    @Override
    public void onEnable() {
        this.injector.registerInjectable(this);
        this.setupConfiguration();
        this.injector.registerInjectable(this.injector.createInstance(ProxyService.class));

        QueueService queueService = new QueueService();
        this.configuration.getAvailableServers().forEach(queueService::addQueue);
        this.injector.registerInjectable(queueService);

        this.injector.registerInjectable(this.injector.createInstance(UserService.class));

        this.getCommand("queue").setExecutor(this.injector.createInstance(QueueCommand.class));
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, this.injector.createInstance(QueueScheduler.class), 0L, 15*20L);

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

    private void setupConfiguration() {
        PluginConfiguration configuration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile("configuration.yml");
            it.saveDefaults();
            it.load(true);
        });
        this.configuration = configuration;
        this.injector.registerInjectable(configuration);
    }
}
