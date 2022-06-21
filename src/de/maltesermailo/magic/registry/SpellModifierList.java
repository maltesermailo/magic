package de.maltesermailo.magic.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SpellModifierList implements Iterable<SpellModifier> {
	
	private List<SpellModifier> modifiers;
	
	public SpellModifierList() {
		this(new ArrayList<>());
	}
	
	public SpellModifierList(List<SpellModifier> modifiers) {
		this.modifiers = modifiers;
	}
	
	@Override
	public Iterator<SpellModifier> iterator() {
		return this.modifiers.iterator();
	}
	
	public void addModifier(SpellModifier modifier) {
		this.modifiers.add(modifier);
	}
	
	public int getAmount(Class<? extends SpellModifier> modifierClass) {
		int amount = 0;
		
		for (SpellModifier modifier : this.modifiers) {
			if (modifierClass.isAssignableFrom(modifier.getClass())) {
				amount = amount + 1;
			}
		}
		
		return amount;
	}
	
	public List<SpellModifier> asArrayList() {
		return Collections.unmodifiableList(this.modifiers);
	}
	
}
