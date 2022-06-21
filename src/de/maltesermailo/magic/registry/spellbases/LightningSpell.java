package de.maltesermailo.magic.registry.spellbases;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.registry.SpellBase;
import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;

public class LightningSpell extends SpellBase {

    private static LightningSpell instance;
	
	static {
		LightningSpell.instance = new LightningSpell();
	}
	
	public static LightningSpell instance() {
		return LightningSpell.instance;
	}
	
	
	private LightningSpell() {
		super("lightning", "Blitz",
				"Lässt einen Blitz auf die anvisierten Ziele los.");
	}

	@Override
	public void cast(Player caster, SpellModifierList modifiers, List<LivingEntity> targets) {
		targets.forEach(t -> t.getLocation().getWorld().strikeLightning(t.getLocation()));
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList();
	}

}
