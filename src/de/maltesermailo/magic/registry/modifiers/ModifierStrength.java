package de.maltesermailo.magic.registry.modifiers;

import de.maltesermailo.magic.registry.SpellModifier;

public class ModifierStrength extends SpellModifier {

    private static ModifierStrength instance;
	
	static {
		ModifierStrength.instance = new ModifierStrength();
	}
	
	public static ModifierStrength instance() {
		return ModifierStrength.instance;
	}
	
	private ModifierStrength() {
		super("strength", "Stärke", 15,
				"Verstärkt den Zauber.");
	}

}
