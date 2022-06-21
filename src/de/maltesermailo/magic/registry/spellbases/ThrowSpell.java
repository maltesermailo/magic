package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierStrength;

public class ThrowSpell extends SpellBase {

    private static ThrowSpell instance;
	
	static {
		ThrowSpell.instance = new ThrowSpell();
	}
	
	public static ThrowSpell instance() {
		return ThrowSpell.instance;
	}
	
	private ThrowSpell() {
		super("push", "Fernhalten", 
				"Schubst das anvisierte Ziel zurück.");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		double distance = (modifiers.getAmount(ModifierStrength.class) / 2) + 2.5D;
		
		for (LivingEntity target : targets) {
	    	Vector vector = caster.getLocation().toVector().multiply(-1).add(target.getLocation().toVector()).normalize();
	    	target.setVelocity(vector.multiply(distance));
	    }
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierStrength.instance());
	}

}
