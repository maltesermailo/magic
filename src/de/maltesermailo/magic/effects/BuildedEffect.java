package de.maltesermailo.magic.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class BuildedEffect extends Effect {

	private List<MathEffect> effects;

	public BuildedEffect() {
		this.effects = new ArrayList<>();
	}

	public void addEffect(MathEffect effect) {
		effect.calculate();
		this.effects.add(effect);
	}

	public void modifyAllEffects(Consumer<MathEffect> action) {
		for (MathEffect effect : this.effects) {
			action.accept(effect);
		}
	}

	@Override
	public void play(Location location, Collection<Player> players) {
		for (MathEffect effect : this.effects) {
			effect.play(location, players);
		}
	}

	@Override
	public BuildedEffect clone() {
		return (BuildedEffect) super.clone();
	}

	@Override
	public List<Vector> getRelativeLocations() {
		List<Vector> locations = new ArrayList<>();

		for (MathEffect effect : this.effects) {
			locations.addAll(effect.getRelativeLocations());
		}

		return Collections.unmodifiableList(locations);
	}

	public abstract void calculate();

}
