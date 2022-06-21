package de.maltesermailo.magic.registry.spelltargets;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.SpellTarget;

public class SpellTargetSelf extends SpellTarget {
	
	private static SpellTargetSelf instance;
	
	static {
		SpellTargetSelf.instance = new SpellTargetSelf();
	}
	
	public static SpellTargetSelf instance() {
		return SpellTargetSelf.instance;
	}
	
	
	private SpellTargetSelf() {
		super("self", "Selbstverzaubern",
				"Der Zauber wirkt auf dich selbst.");
	}

	@Override
	public void execute(Player caster, SpellModifierList modifiers, Consumer<List<LivingEntity>> action) {
		action.accept(Arrays.asList(caster));
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList();
	}

}
