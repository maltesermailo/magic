package de.maltesermailo.magic.registry.modifiers;

import de.maltesermailo.magic.registry.SpellModifier;

public class ModifierDurability extends SpellModifier {

    private static ModifierDurability instance;
	
	static {
		ModifierDurability.instance = new ModifierDurability();
	}
	
	public static ModifierDurability instance() {
		return ModifierDurability.instance;
	}
	
	private ModifierDurability() {
		super("durability", "Anhaltung", 5,
				"Vergrößert die Zeit, die der Zauber anhält.");
	}

}
