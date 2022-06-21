package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierStrength;

public class ExplosionSpell extends SpellBase {

    private static ExplosionSpell instance;
	
	static {
		ExplosionSpell.instance = new ExplosionSpell();
	}
	
	public static ExplosionSpell instance() {
		return ExplosionSpell.instance;
	}
	
	private ExplosionSpell() {
		super("explosion", "Explosion", 
				"Erzeugt eine Explosion am anvisiertem Block.");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		int tntAmount = modifiers.getAmount(ModifierStrength.class) + 1;
		
		Location loc = caster.getTargetBlock((Set<Material>)null, 20).getLocation();
		
		for(int i = 0; i < tntAmount; i++) {
			loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
		}
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierStrength.instance());
	}

}
