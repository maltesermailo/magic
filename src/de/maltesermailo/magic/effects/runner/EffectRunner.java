package de.maltesermailo.magic.effects.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.effects.Effect;

public abstract class EffectRunner {
	
	private Effect effect;
	
	private List<Player> targets;
	
	public EffectRunner(Effect effect) {
		this.targets = new ArrayList<>();
		this.effect = effect;
	}
	
	public EffectRunner(Effect effect, Player target) {
		this(effect, Arrays.asList(target));
	}
	
	public EffectRunner(Effect effect, Player... targets) {
		this(effect, Arrays.asList(targets));
	}
	
	public EffectRunner(Effect effect, Collection<Player> targets) {
		this(effect);
		
		this.addTargets(targets);
	}
	
	public void addTarget(Player target) {
		this.targets.add(target);
	}
	
	public void addTargets(Player... targets) {
		this.addTargets(Arrays.asList(targets));
	}
	
	public void addTargets(Collection<Player> targets) {
		this.targets.addAll(targets);
	}
	
	public void removeTarget(Player target) {
		this.targets.remove(target);
	}
	
	public void clearTargets() {
		this.targets.clear();
	}
	
	public Effect getRunningEffect() {
		return this.effect;
	}
	
	public List<Player> getTargets() {
		return Collections.unmodifiableList(this.targets);
	}
	
	public List<Player> getClonedTargets() {
		List<Player> clonedList = new ArrayList<>();
		
		for (Player player : this.targets) {
			clonedList.add(player);
		}
		
		return clonedList;
	}
	
	protected void setTargets(List<Player> targets) {
		this.targets = targets;
	}
	
	public abstract void executeSimplified(Location startPoint);
	
}
