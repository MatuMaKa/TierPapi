package xyz.matumaka.tiers.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import xyz.matumaka.tiers.TierPapi;

import java.util.ArrayList;
import java.util.List;

public class TierpapiCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "TierPapi Commands:");
            sender.sendMessage(ChatColor.YELLOW + "/tierpapi reload - Reloads the TierPapi plugin.");
            sender.sendMessage(ChatColor.YELLOW + "/tierpapi help - Sends you the list of all TierPapi commands.");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("tierpapi.command.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }

            TierPapi.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "TierPapi has been reloaded!");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.YELLOW + "TierPapi Commands:");
            sender.sendMessage(ChatColor.YELLOW + "/tierpapi reload - Reloads the TierPapi plugin.");
            sender.sendMessage(ChatColor.YELLOW + "/tierpapi help - Sends you the list of all TierPapi commands.");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /tierpapi help for help.");
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("reload");
            suggestions.add("help");
        }
        return suggestions;
    }
}