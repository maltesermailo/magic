package de.maltesermailo.magic.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.maltesermailo.magic.registry.spelltargets.SpellTargetExpl;
import de.maltesermailo.magic.registry.spelltargets.SpellTargetProjectile;

public class ListenerEntityDamageByEntity implements Listener {

	@EventHandler
	public void onEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball && e.getEntity() instanceof LivingEntity) {
			SpellTargetProjectile.acceptSnowball((Snowball) e.getDamager(), (LivingEntity) e.getEntity());
			SpellTargetExpl.acceptSnowball((Snowball) e.getDamager(), (LivingEntity) e.getEntity());
		}
	}

}