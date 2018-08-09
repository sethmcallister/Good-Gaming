package com.goodgaming.test.entity;

import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class WolfEntity extends EntityWolf {
    public WolfEntity(World world) {
        super(world);
    }

    public void follow(Player player) {
        Location location = player.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        getNavigation().a(x, y, z);
    }

    public boolean makeAngry(Player player, double damage) {
        EntityWolf wolf = this;
        wolf.setAngry(true);
        return false;
    }

    public static WolfEntity spawn(Location location) {
        WolfEntity wolfEntity = new WolfEntity(((CraftWorld) location.getWorld()).getHandle());
        wolfEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
        wolfEntity.world.addEntity(wolfEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return wolfEntity;
    }
}
