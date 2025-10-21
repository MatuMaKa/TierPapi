package xyz.matumaka.tiers.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration config;

    private String unranked;
    private String tierFormat;
    private int refreshTicks;
    private Map<Integer, String> prefixMap;
    private Map<Integer, String> suffixMap;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        // Load values
        unranked = config.getString("unranked", "-");
        tierFormat = config.getString("tier", "%prefix% %tier% %suffix%");
        refreshTicks = config.getInt("refresh", 72000);

        prefixMap = new HashMap<>();
        suffixMap = new HashMap<>();

        if (config.isConfigurationSection("tiers.prefix")) {
            for (String key : config.getConfigurationSection("tiers.prefix").getKeys(false)) {
                try {
                    prefixMap.put(Integer.parseInt(key), config.getString("tiers.prefix." + key, ""));
                } catch (NumberFormatException ignored) {}
            }
        }

        if (config.isConfigurationSection("tiers.suffix")) {
            for (String key : config.getConfigurationSection("tiers.suffix").getKeys(false)) {
                try {
                    suffixMap.put(Integer.parseInt(key), config.getString("tiers.suffix." + key, ""));
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    public String getUnranked() {
        return unranked;
    }

    public String getTierFormat() {
        return tierFormat;
    }

    public int getRefreshTicks() {
        return refreshTicks;
    }

    public String getPrefix(int tier) {
        return prefixMap.getOrDefault(tier, "");
    }

    public String getSuffix(int tier) {
        return suffixMap.getOrDefault(tier, "");
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
