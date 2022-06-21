package de.maltesermailo.magic.effects.runner;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.maltesermailo.magic.Magic;
import de.maltesermailo.magic.effects.Effect;

public class FlowingEffectRunner extends EffectRunner {
	
	public FlowingEffectRunner(Effect effect) {
		super(effect);
	}
	
	public FlowingEffectRunner(Effect effect, Player target) {
		super(effect, target);
	}
	
	public FlowingEffectRunner(Effect effect, Player... targets) {
		super(effect, targets);
	}
	
	public FlowingEffectRunner(Effect effect, Collection<Player> targets) {
		super(effect, targets);
	}
	
	@Override
	public void executeSimplified(Location startPoint) {
		this.executeFlowed(startPoint, 1);
	}
	
	public void executeFlowed(Location startPoint, long delay) {
		this.executeFlowed(startPoint, delay, 1);
	}
	
	public void executeFlowed(Location startPoint, long delay, int amountPerRepeat) {
		if (amountPerRepeat <= 0) {
			return;
		}
		
		List<Player> targets = this.getClonedTargets();
		Effect effect = this.getRunningEffect().clone().move(startPoint.toVector());
		
		int[] array = new int[] { 0, 0 };
		
		array[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Magic.instance(), () -> {
			for (int i = 0; i < amountPerRepeat; i++) {
				if (array[1] >= effect.getLength()) {
					Bukkit.getScheduler().cancelTask(array[0]);
					return;
				}
				
				for (Player target : targets) {
					effect.getParticlePacket(array[1]).send(target);
				}
				
				array[1] = array[1] + 1;
			}
		}, 0L, delay);
	}
	
}
