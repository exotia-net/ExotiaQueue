package net.exotia.plugins.exotiaqueue.configuration.section;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public class BossBarSection extends OkaeriConfig {
    private String title = "Twoja pozycja: {position}/{total}";
    private BarColor barColor = BarColor.GREEN;
    private BarStyle barStyle = BarStyle.SOLID;

    public String getTitle() {
        return title;
    }

    public BarColor getBarColor() {
        return barColor;
    }

    public BarStyle getBarStyle() {
        return barStyle;
    }
}
