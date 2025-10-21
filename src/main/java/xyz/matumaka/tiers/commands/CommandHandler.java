package xyz.matumaka.tiers.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collections;

public class CommandHandler implements TabCompleter {

    private final JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public java.util.List<String> onTabComplete(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    public void registerCommands() {
        register("tierpapi", new TierpapiCommand());
    }

    private void register(String name, CommandExecutor executor) {
        if (plugin.getCommand(name) != null) {
            plugin.getCommand(name).setExecutor(executor);
            if (executor instanceof TabCompleter) {
                plugin.getCommand(name).setTabCompleter((TabCompleter) executor);
            }
        } else {
            plugin.getLogger().warning("Command /" + name + " is missing from plugin.yml");
        }
    }
}