package com.goodgaming.test.listener;

import com.goodgaming.test.Main;
import com.goodgaming.test.dto.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Economy economy = Main.getInstance().getEconomyHandler().findByPlayer(player);
        Main.getInstance().getEconomyHandler().updatePlayer(player, economy.getBalance().get());
    }
}
