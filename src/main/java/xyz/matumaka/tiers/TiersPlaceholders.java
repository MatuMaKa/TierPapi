package xyz.matumaka.tiers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.matumaka.tiers.tiers.mctiers;
import xyz.matumaka.tiers.tiers.pvptiers;
import xyz.matumaka.tiers.tiers.subtiers;

public final class TiersPlaceholders extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new mctiers().register();
            new pvptiers().register();
            new subtiers().register();
        } else {
            getLogger().warning("PlaceholderAPI not found! Disabling Tiers-Placeholders plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {}
}
