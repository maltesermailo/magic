package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.visuals.EffectCircle;
import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.modifiers.ModifierDurability;
import de.maltesermailo.magic.registry.modifiers.ModifierStrength;

public class HealSpell extends SpellBase {

	private static HealSpell instance;

	static {
		HealSpell.instance = new HealSpell();
	}

	public static HealSpell instance() {
		return HealSpell.instance;
	}
	
	private EffectCircle effect;
	
	private HealSpell() {
		super("heal", "Heilung",
				"Heilt das anvisierte Ziel.");
		
		this.effect = new EffectCircle(10, 1.0F, ParticleData.PARTICLE_HEART);
		this.effect.calculate();
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		double strength = (modifiers.getAmount(ModifierStrength.class) / 4) - 1;
		
		if(strength < 0)
			strength = 0;
		
		int ticks = modifiers.getAmount(ModifierDurability.class) * 20 + 5 * 20;
		
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.REGENERATION, ticks, Double.valueOf(strength).intValue(), false, true, Color.YELLOW);
		
		targets.forEach((target) -> {
			if (target.isDead()) {
				return;
			}
			
			target.addPotionEffect(potionEffect);
			this.effect.play(target.getLocation().add(0, 1, 0));
		});
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierStrength.instance(), ModifierDurability.instance());
	}

}
