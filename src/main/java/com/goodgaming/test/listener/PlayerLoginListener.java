package com.goodgaming.test.listener;

import com.goodgaming.test.Main;
import com.goodgaming.test.dto.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {
    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        Player player = event.getPlayer();
        System.out.println("Player uuid = " + player.getUniqueId());
        Economy economy = Main.getInstance().getEconomyHandler().loadFromPlayer(player);
        if (economy.getBalance().get() == -1) {
            Bukkit.getLogger().info("Inserting into DB as it does not exist");
            Main.getInstance().getEconomyHandler().create(player);
            economy.getBalance().set(0);
        }
        Bukkit.getLogger().info("eco uuid == " + economy.getUuid());
        Main.getInstance().getEconomyHandler().findAll().add(economy);
    }
}
