package com.goodgaming.test.command;

import com.goodgaming.test.Main;
import com.goodgaming.test.dto.Economy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("testproject.command.eco")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /eco give <Player> <Amount>");
            return true;
        }

        if (!args[0].equalsIgnoreCase("give")) {
            System.out.println("25");
            sender.sendMessage(ChatColor.RED + "Usage: /eco give <Player> <Amount>");
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        if (offlinePlayer == null) {
            sender.sendMessage(ChatColor.RED + String.format("No player with the Name '%s' could be found.", args[1]));
            return true;
        }
        if (!StringUtils.isNumeric(args[2])) {
            sender.sendMessage(ChatColor.RED + String.format("The argument %s is not a number.", args[2]));
            return true;
        }
        int amount = Integer.parseInt(args[2]);
        if (amount < 0) {
            sender.sendMessage(ChatColor.RED + String.format("The argument %s must be greater than 0.", amount));
            return true;
        }
        Economy economy = Main.getInstance().getEconomyHandler().findByPlayer(offlinePlayer);
        economy.getBalance().lazySet(economy.getBalance().get() + amount);

        sender.sendMessage(ChatColor.YELLOW + String.format("You have given $%s %s, they now have $%s", offlinePlayer.getName(), args[2], amount));
        if (offlinePlayer.isOnline()) {
            Bukkit.getPlayer(offlinePlayer.getUniqueId()).sendMessage(ChatColor.YELLOW + String.format("You have been given $%s by %s, you now have $%s.", args[2], sender.getName(), amount));
        }
        return true;
    }
}
