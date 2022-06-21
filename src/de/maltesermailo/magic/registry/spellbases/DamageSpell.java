package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.visuals.EffectCircle;
import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierStrength;

public class DamageSpell extends SpellBase {

    private static DamageSpell instance;
	
	static {
		DamageSpell.instance = new DamageSpell();
	}
	
	public static DamageSpell instance() {
		return DamageSpell.instance;
	}
	
	
	private EffectCircle effect;
	
	private DamageSpell() {
		super("damage", "Schaden",
				"Macht dem anvisierten Ziel Schaden.");
		
		this.effect = new EffectCircle(10, 1.0F, ParticleData.PARTICLE_DEATH_HEART);
		this.effect.calculate();
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		int damage = modifiers.getAmount(ModifierStrength.class) + 4;
		
		for (LivingEntity target : targets) {
			target.setLastDamage(0);
			target.damage(damage, caster);
			
			this.effect.play(target.getLocation().add(0, 1, 0));
		}
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierStrength.instance());
	}

}
