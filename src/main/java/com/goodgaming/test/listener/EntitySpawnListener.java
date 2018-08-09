package com.goodgaming.test.listener;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobSpawn(final EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Monster))
            return;

        event.setCancelled(true);
    }
}
