package de.maltesermailo.magic.effects.runner;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.effects.Effect;

public class SimpleEffectRunner extends EffectRunner {

	public SimpleEffectRunner(Effect effect) {
		super(effect);
	}
	
	public SimpleEffectRunner(Effect effect, Player target) {
		super(effect, target);
	}
	
	public SimpleEffectRunner(Effect effect, Player... targets) {
		super(effect, targets);
	}
	
	public SimpleEffectRunner(Effect effect, Collection<Player> targets) {
		super(effect, targets);
	}

	@Override
	public void executeSimplified(Location startPoint) {
		this.execute(startPoint);
	}
	
	public void execute(Location startPoint) {
		List<Player> targets = this.getClonedTargets();
		Effect effect = this.getRunningEffect().clone().move(startPoint.toVector());
		
		for (int i = 0; i < effect.getLength(); i++) {
			for (Player target : targets) {
				effect.getParticlePacket(i).send(target);
			}
		}
	}
	
}
