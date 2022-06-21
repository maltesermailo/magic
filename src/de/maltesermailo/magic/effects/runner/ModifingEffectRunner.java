package de.maltesermailo.magic.effects.runner;

import java.util.Collection;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

import de.maltesermailo.magic.effects.Effect;

public class ModifingEffectRunner extends ActivatableEffectRunner {
	
	private Consumer<Effect> action;
	
	public ModifingEffectRunner(Effect effect) {
		super(effect);
	}
	
	public ModifingEffectRunner(Effect effect, Player target) {
		super(effect, target);
	}
	
	public ModifingEffectRunner(Effect effect, Player... targets) {
		super(effect, targets);
	}
	
	public ModifingEffectRunner(Effect effect, Collection<Player> targets) {
		super(effect, targets);
	}
	
	public ModifingEffectRunner(EffectRunner runner) {
		super(runner);
	}
	
	public ModifingEffectRunner(EffectRunner runner, Consumer<Effect> action) {
		super(runner);
		
		this.action = action;
	}
	
	public void setEffectModifier(Consumer<Effect> action) {
		this.action = action;
	}
	
	public Consumer<Effect> getEffectModifier() {
		return this.action;
	}
	
	@Override
	protected void effectPlayed() {
		if (this.action == null) {
			return;
		}
		
		this.action.accept(this.getRunningEffect());
	}
	
}
