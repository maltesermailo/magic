package de.maltesermailo.magic.registry;

import org.bukkit.entity.Player;

public class Spell {
	
	private SpellBase base;
	private SpellTarget target;
	
	private SpellModifierList modifiers;
	
	public Spell(SpellBase base, SpellTarget target, SpellModifierList modifiers) {
		this.base = base;
		this.target = target;
		this.modifiers = modifiers;
	}
	
	public void cast(Player caster) {
		this.target.execute(caster, this.modifiers, (targets) -> {
			this.base.cast(caster, this.modifiers, targets);
		});
	}
	
	public SpellBase getBase() {
		return this.base;
	}
	
	public SpellTarget getTarget() {
		return this.target;
	}
	
	public SpellModifierList getModifiers() {
		return this.modifiers;
	}
	
}
