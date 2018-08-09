package com.goodgaming.test;

import com.goodgaming.test.command.BalanceCommand;
import com.goodgaming.test.command.EcoCommand;
import com.goodgaming.test.dto.Economy;
import com.goodgaming.test.handler.EconomyHandler;
import com.goodgaming.test.listener.EntitySpawnListener;
import com.goodgaming.test.listener.PlayerLoginListener;
import com.goodgaming.test.listener.PlayerQuitListener;
import com.goodgaming.test.util.HikariUtil;
import com.goodgaming.test.util.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    private static Main instance;
    private final String dbName = "Economy";
    private final String tableName = "Players";
    private EconomyHandler economyHandler;

    @Override
    public void onLoad() {
        instance = this;
        Bukkit.getLogger().info("Loading plugin");
        this.economyHandler = new EconomyHandler();

        // Don't use Hikari as it's configured to work with a predefined database
        if (!SQLUtil.doesDatabaseExist(dbName)) {
            Bukkit.getLogger().info("Database %s does not exist");
            SQLUtil.createDatabase(dbName);
        }

        // Use Hikari as it's configured to work with a predefined database
        if (!HikariUtil.doesTableExist(tableName)) {
            Bukkit.getLogger().info("Database %s does not have a table called Players");
            HikariUtil.createTable(tableName);
        }
    }

    @Override
    public void onEnable() {
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("eco").setExecutor(new EcoCommand());

        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            Economy economy = this.economyHandler.findByPlayer(player);
            this.economyHandler.updatePlayer(player, economy.getBalance().get());
        }
    }

    public String getDbName() {
        return dbName;
    }

    public static Main getInstance() {
        return instance;
    }

    public String getTableName() {
        return tableName;
    }

    public EconomyHandler getEconomyHandler() {
        return economyHandler;
    }
}
