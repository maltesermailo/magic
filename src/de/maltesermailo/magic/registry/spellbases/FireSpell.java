package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierDurability;

public class FireSpell extends SpellBase {

    private static FireSpell instance;
	
	static {
		FireSpell.instance = new FireSpell();
	}
	
	public static FireSpell instance() {
		return FireSpell.instance;
	}
	
	protected FireSpell() {
		super("fire", "Feuer",
				"Setzt das anvisierte Ziel in Feuer.");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		int durability = modifiers.getAmount(ModifierDurability.class) + 5;
		
		for (LivingEntity target : targets) {
			target.setFireTicks(durability * 20);
		}
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierDurability.instance());
	}

}
