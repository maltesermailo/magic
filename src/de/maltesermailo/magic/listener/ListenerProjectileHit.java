package de.maltesermailo.magic.listener;

import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import de.maltesermailo.magic.registry.spelltargets.SpellTargetExpl;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetProjectile;

public class ListenerProjectileHit implements Listener {
	
	@EventHandler
	public void onEntity(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Snowball) {
			SpellTargetProjectile.acceptSnowball((Snowball) e.getEntity(), null);
			SpellTargetExpl.acceptSnowball((Snowball) e.getEntity(), null);
		}
	}
	
}
