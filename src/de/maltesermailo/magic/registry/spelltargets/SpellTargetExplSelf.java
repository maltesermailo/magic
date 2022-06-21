package de.maltesermailo.magic.registry.spelltargets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.registry.SpellModifier;
import de.maltesermailo.magic.registry.SpellModifierList;
import de.maltesermailo.magic.registry.SpellTarget;

public class SpellTargetExplSelf extends SpellTarget {

    private static SpellTargetExplSelf instance;
	
	static {
		SpellTargetExplSelf.instance = new SpellTargetExplSelf();
	}
	
	public static SpellTargetExplSelf instance() {
		return SpellTargetExplSelf.instance;
	}
	
	private SpellTargetExplSelf() {
		super("explself", "Umkreis-selbstverzaubern", 
				"Der Zaubert wirkt auf dich und alle im Umkreis.");
	}

	@Override
	public void execute(Player caster, SpellModifierList modifiers, Consumer<List<LivingEntity>> action) {
		List<LivingEntity> nearby = new ArrayList<>();
		
		// TODO Add Range modifier
		
		for (Entity e : caster.getNearbyEntities(10, 10, 10)) {
			if (e instanceof LivingEntity) {
				LivingEntity le = (LivingEntity)e;
				nearby.add(le);
			}
		}
		
		nearby.add(caster);
		
		action.accept(nearby);
	}

	@Override
	public List<SpellModifier> getValidModifiers() {
		return Arrays.asList();
	}

}
