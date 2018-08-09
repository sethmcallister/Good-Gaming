package com.goodgaming.test.handler;

import com.goodgaming.test.Main;
import com.goodgaming.test.dto.Economy;
import com.goodgaming.test.util.HikariUtil;
import com.goodgaming.test.util.SQLUtil;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class EconomyHandler {
    private final List<Economy> economyList;


    public EconomyHandler() {
        this.economyList = new ArrayList<>();
    }

    public Economy findByPlayer(final OfflinePlayer player) {
        for (Economy economy : economyList) {
            if (economy == null)
                continue;

            if (economy.getUuid().equals(player.getUniqueId()))
                return economy;
        }
        return null;
    }

    public List<Economy> findAll() {
        return this.economyList;
    }

    public Economy loadFromPlayer(final OfflinePlayer player) {
        return HikariUtil.select(Main.getInstance().getTableName(), player.getUniqueId());
    }

    public void updatePlayer(final OfflinePlayer player, final int balance) {
        HikariUtil.update(Main.getInstance().getTableName(), player.getUniqueId(), balance);
    }

    public void create(final OfflinePlayer player) {
        HikariUtil.insert(Main.getInstance().getTableName(), player.getUniqueId());
    }
}
