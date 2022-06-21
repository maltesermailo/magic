package de.maltesermailo.magic.registry.modifiers;

import de.maltesermailo.magic.registry.SpellModifier;

public class ModifierCasting extends SpellModifier {

    private static ModifierCasting instance;
	
	static {
		ModifierCasting.instance = new ModifierCasting();
	}
	
	public static ModifierCasting instance() {
		return ModifierCasting.instance;
	}
	
	private ModifierCasting() {
		super("casting", "Zaubergeschwindigkeit", 60, 
				"Macht die Ausführungszeit für manche Zauber geringer. (z.B. Teleportation)");
	}

}
