package com.goodgaming.test.command;

import com.goodgaming.test.Main;
import com.goodgaming.test.dto.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /balance");
            return true;
        }

        Player player = (Player)sender;
        Economy economy = Main.getInstance().getEconomyHandler().findByPlayer(player);
        if (economy == null) {
            player.sendMessage(ChatColor.YELLOW + "Balance: " + ChatColor.WHITE + "$" + 0);
            return true;
        }
        player.sendMessage(ChatColor.YELLOW + "Balance: " + ChatColor.WHITE + "$" + economy.getBalance());
        return true;
    }
}
