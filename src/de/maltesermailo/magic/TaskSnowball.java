package de.maltesermailo.magic;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetProjectile;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;

public class TaskSnowball implements Runnable {
	
	@Override
	public void run() {
		for (Snowball snowball : SpellTargetProjectile.getBalls().keySet()) {
			if (snowball.isDead()) {
				continue;
			}
			
			Location loc = snowball.getLocation();
			
			PacketPlayOutWorldParticles packet = ParticleData.coloredDust(Color.AQUA).createPacket(loc.getX(), loc.getY(), loc.getZ());
			
			for (Entity e : snowball.getNearbyEntities(50.0D, 50.0D, 50.0D)) {
				if (e instanceof Player) {
					Player p = (Player) e;
					
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				}
			}
		}
	}
	
}
