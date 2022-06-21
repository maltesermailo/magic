package de.maltesermailo.magic.registry.modifiers;

import de.maltesermailo.magic.registry.SpellModifier;

public class ModifierCooldown extends SpellModifier {

    private static ModifierCooldown instance;
	
	static {
		ModifierCooldown.instance = new ModifierCooldown();
	}
	
	public static ModifierCooldown instance() {
		return ModifierCooldown.instance;
	}
	
	private ModifierCooldown() {
		super("cooldown", "Cooldown", 6,
				"Verringert den Cooldown deines Stabs.");
	}

}
