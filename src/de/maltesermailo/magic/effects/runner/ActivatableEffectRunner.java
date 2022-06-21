package de.maltesermailo.magic.effects.runner;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.maltesermailo.magic.Magic;
import de.maltesermailo.magic.effects.Effect;

public class ActivatableEffectRunner extends EffectRunner {
	
	private boolean running;
	
	private EffectRunner runner;
	
	private BukkitTask task;
	
	public ActivatableEffectRunner(Effect effect) {
		this(new SimpleEffectRunner(effect));
	}
	
	public ActivatableEffectRunner(Effect effect, Player target) {
		this(new SimpleEffectRunner(effect, target));
	}
	
	public ActivatableEffectRunner(Effect effect, Player... targets) {
		this(new SimpleEffectRunner(effect, targets));
	}
	
	public ActivatableEffectRunner(Effect effect, Collection<Player> targets) {
		this(new SimpleEffectRunner(effect, targets));
	}
	
	public ActivatableEffectRunner(EffectRunner runner) {
		super(runner.getRunningEffect(), runner.getClonedTargets());
		
		this.runner = runner;
		this.running = false;
	}
	
	@Override
	public void executeSimplified(Location startPoint) {
		this.start(startPoint, 20);
	}
	
	public void start(Location startPoint, long delay) {
		if (this.running) {
			return;
		}
		
		this.running = true;
		this.task = Bukkit.getScheduler().runTaskTimer(Magic.instance(), () -> {
			this.runner.setTargets(this.getTargets());
			this.runner.executeSimplified(startPoint);
			this.effectPlayed();
		}, 0L, 5);
	}
	
	public void stop() {
		if (!this.running) {
			return;
		}
		
		this.task.cancel();
		this.task = null;
		this.running = false;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	protected void effectPlayed() {
		
	}
	
}
