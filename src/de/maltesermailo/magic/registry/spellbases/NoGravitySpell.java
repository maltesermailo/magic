package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierDurability;

public class NoGravitySpell extends SpellBase {

	
    private static NoGravitySpell instance;
	
	static {
		NoGravitySpell.instance = new NoGravitySpell();
	}
	
	public static NoGravitySpell instance() {
		return NoGravitySpell.instance;
	}
	
	private NoGravitySpell() {
		super("nogravity", "Gravitätsänderung",
				"Verändert die Gravität des anvisierten Zieles.");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		int durability = modifiers.getAmount(ModifierDurability.class);
		
		if (durability == 0) {
			durability = 1;
		}
		
        PotionEffect noGravity = new PotionEffect(PotionEffectType.LEVITATION, (durability * 3) * 20, 1, false, false);
		
		targets.forEach(l -> l.addPotionEffect(noGravity));
	}
	
	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierDurability.instance());
	}
}
