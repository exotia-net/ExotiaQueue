package net.exotia.plugins.exotiaqueue.configuration;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.plugins.exotiaqueue.configuration.section.BossBarSection;

import java.util.List;

public class PluginConfiguration extends OkaeriConfig {
    private List<String> availableServers = List.of("survival", "test_instance");
    private BossBarSection bossBar = new BossBarSection();
    private String successfullyConnected = "&8&l>> &aPomyślnie połączono z serwerem {server}";
    private String playerAlreadyInQueue = "Jestes juz w kolejce na serwer {server}";

    public List<String> getAvailableServers() {
        return availableServers;
    }
    public String getSuccessfullyConnected() {
        return successfullyConnected;
    }
    public BossBarSection getBossBar() {
        return bossBar;
    }
    public String getPlayerAlreadyInQueue() {
        return playerAlreadyInQueue;
    }
}
