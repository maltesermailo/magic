package de.maltesermailo.magic.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellRegistry {
	
	private List<SpellBase> spellBases;
	private List<SpellTarget> spellTargets;
	private List<SpellModifier> spellModifiers;
	
	public SpellRegistry() {
		this.spellBases = new ArrayList<>();
		this.spellTargets = new ArrayList<>();
		this.spellModifiers = new ArrayList<>();
	}
	
	public void registerSpellBase(SpellBase base) {
		this.spellBases.add(base);
	}
	
	public void registerSpellTarget(SpellTarget target) {
		this.spellTargets.add(target);
	}
	
	public void registerSpellModifier(SpellModifier modifier) {
		this.spellModifiers.add(modifier);
	}
	
	public SpellBase getSpellBase(String systemName) {
		for (SpellBase base : this.spellBases) {
			if (base.getSystemName().equals(systemName)) {
				return base;
			}
		}
		
		return null;
	}
	
	public SpellTarget getSpellTarget(String systemName) {
		for (SpellTarget target : this.spellTargets) {
			if (target.getSystemName().equals(systemName)) {
				return target;
			}
		}
		
		return null;
	}
	
	public SpellModifier getSpellModifier(String systemName) {
		for (SpellModifier modifier : this.spellModifiers) {
			if (modifier.getSystemName().equals(systemName)) {
				return modifier;
			}
		}
		
		return null;
	}
	
	public List<SpellBase> getSpellBases() {
		return Collections.unmodifiableList(this.spellBases);
	}
	
	public List<SpellTarget> getSpellTargets() {
		return Collections.unmodifiableList(this.spellTargets);
	}
	
	public List<SpellModifier> getSpellModifiers() {
		return Collections.unmodifiableList(this.spellModifiers);
	}
	
}
