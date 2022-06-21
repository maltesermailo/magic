package de.maltesermailo.magic.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.maltesermailo.magic.Magic;
import de.maltesermailo.magic.effects.runner.SimpleEffectRunner;

public abstract class Effect implements Cloneable {

	protected List<Vector> locations;

	public Effect() {
		this(new ArrayList<>());
	}

	protected Effect(List<Vector> locations) {
		this.locations = locations;
	}

	@Override
	public Effect clone() {
		try {
			Effect clone = (Effect) super.clone();
			clone.locations = new ArrayList<>();

			for (Vector vector : this.locations) {
				clone.locations.add(vector.clone());
			}

			return clone;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public void play(Location location) {
		this.play(location, Bukkit.getOnlinePlayers().stream().collect(Collectors.toList()));
	}

	public void play(Location location, Player player) {
		this.play(location, Arrays.asList(player));
	}

	public void play(Location location, Collection<Player> players) {
		new SimpleEffectRunner(this, players).execute(location);
	}

	public int getLength() {
		return this.locations.size();
	}

	public Vector getParticleLocation(int id) {
		return this.locations.get(id);
	}

	public ParticlePacket getParticlePacket(int id) {
		return new ParticlePacket(this.getData(id), this.getParticleLocation(id));
	}

	public Effect randomnize(double offset) {
		return this.randomnize(offset, offset, offset);
	}

	public Effect randomnize(double offsetX, double offsetY, double offsetZ) {
		return this.randomnize(offsetX, offsetY, offsetZ, Magic.random());
	}

	public Effect randomnize(double offsetX, double offsetY, double offsetZ, Random random) {
		for (Vector vector : this.locations) {
			vector.setX(vector.getX() + (random.nextDouble() * offsetX * 2) - offsetX);
			vector.setY(vector.getY() + (random.nextDouble() * offsetY * 2) - offsetY);
			vector.setZ(vector.getZ() + (random.nextDouble() * offsetZ * 2) - offsetZ);
		}

		return this;
	}

	public Effect moveX(double offsetX) {
		return this.move(new Vector(offsetX, 0, 0));
	}

	public Effect moveY(double offsetY) {
		return this.move(new Vector(0, offsetY, 0));
	}

	public Effect moveZ(double offsetZ) {
		return this.move(new Vector(0, 0, offsetZ));
	}

	public Effect move(Vector move) {
		return this.modifyLocations((vector) -> {
			vector.setX(vector.getX() + move.getX());
			vector.setY(vector.getY() + move.getY());
			vector.setZ(vector.getZ() + move.getZ());
		});
	}

	public Effect reflect() {
		this.reflectOnX();
		this.reflectOnY();
		this.reflectOnZ();
		return this;
	}

	public Effect reflectOnX() {
		return this.modifyLocations((vector) -> {
			vector.setX(vector.getX() * -1);
		});
	}

	public Effect reflectOnY() {
		return this.modifyLocations((vector) -> {
			vector.setY(vector.getY() * -1);
		});
	}

	public Effect reflectOnZ() {
		return this.modifyLocations((vector) -> {
			vector.setZ(vector.getZ() * -1);
		});
	}

	public Effect rotateX(double angle) {
		double radians = Math.toRadians(angle);
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);

		return this.modifyLocations((vector) -> {
			double y = cos * vector.getY() + (-sin) * vector.getZ();
			double z = sin * vector.getY() + cos * vector.getZ();
			vector.setY(y);
			vector.setZ(z);
		});
	}

	public Effect rotateY(double angle) {
		double radians = Math.toRadians(angle);
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);

		return this.modifyLocations((vector) -> {
			double x = cos * vector.getX() + (-sin) * vector.getZ();
			double z = sin * vector.getX() + cos * vector.getZ();
			vector.setX(x);
			vector.setZ(z);
		});
	}

	public Effect rotateZ(double angle) {
		double radians = Math.toRadians(angle);
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);

		return this.modifyLocations((vector) -> {
			double x = cos * vector.getX() + (-sin) * vector.getY();
			double z = sin * vector.getX() + cos * vector.getY();
			vector.setX(x);
			vector.setZ(z);
		});
	}

	public Effect modifyLocations(Consumer<Vector> action) {
		for (Vector vector : this.locations) {
			action.accept(vector);
		}

		return this;
	}

	public List<Vector> getRelativeLocations() {
		return Collections.unmodifiableList(this.locations);
	}

	public abstract ParticleData getData(int particleId);

}
