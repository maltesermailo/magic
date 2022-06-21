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

public class GlowSpell extends SpellBase {

    private static GlowSpell instance;
	
	static {
		GlowSpell.instance = new GlowSpell();
	}
	
	public static GlowSpell instance() {
		return GlowSpell.instance;
	}
	
	private GlowSpell() {
		super("glow", "Glühen",
				"Lässt das anvisierte Ziel glühen.");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		int durability = modifiers.getAmount(ModifierDurability.class);
		
		if (durability == 0) {
			durability = 1;
		}
		
        PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, (durability * 3) * 20, 1, false, false);
		
		targets.forEach(l -> l.addPotionEffect(glow));
	}
	
	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList(ModifierDurability.instance());
	}
	
}
