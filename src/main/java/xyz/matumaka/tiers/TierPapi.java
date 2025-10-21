package xyz.matumaka.tiers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.matumaka.tiers.commands.CommandHandler;
import xyz.matumaka.tiers.managers.ConfigManager;
import xyz.matumaka.tiers.tiers.Mctiers;
import xyz.matumaka.tiers.tiers.Novatiers;
import xyz.matumaka.tiers.tiers.Pvptiers;
import xyz.matumaka.tiers.tiers.Subtiers;

public final class TierPapi extends JavaPlugin {

    private static TierPapi instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Mctiers().register();
            new Pvptiers().register();
            new Subtiers().register();
            new Novatiers().register();

            instance = this;

            configManager = new ConfigManager(this);
            new CommandHandler(this).registerCommands();
            TierPapi.getInstance().reloadConfig();
        } else {
            getLogger().warning("PlaceholderAPI not found! Disabling Tiers-Placeholders plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {}

    public static TierPapi getInstance() { return instance; }

}
