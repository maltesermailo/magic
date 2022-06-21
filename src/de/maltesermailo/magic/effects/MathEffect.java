package de.maltesermailo.magic.effects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.util.Vector;

import de.maltesermailo.magic.effects.math.MathFunction;

public abstract class MathEffect extends Effect {

	private boolean calculated;

	private int particleCount;

	public MathEffect(int particleCount) {
		this(particleCount, new ArrayList<>());
	}

	public MathEffect(int particleCount, List<Vector> locations) {
		super(locations);

		this.particleCount = particleCount;
	}

	@Override
	public MathEffect clone() {
		return (MathEffect) super.clone();
	}

	@Override
	public Effect modifyLocations(Consumer<Vector> action) {
		if (!this.calculated) {
			throw new RuntimeException("This effect is not calculated!");
		}

		return super.modifyLocations(action);
	}

	public void calculate() {
		if (this.calculated) {
			throw new RuntimeException("This effect is already calculated!");
		}

		for (int i = 0; i < this.particleCount; i++) {
			Vector v = new Vector(this.getCalculatorX().calc(i), this.getCalculatorY().calc(i), this.getCalculatorZ().calc(i));
			this.locations.add(v);
		}

		this.calculated = true;
	}

	public int getParticleCount() {
		return this.particleCount;
	}

	public abstract MathFunction getCalculatorX();

	public abstract MathFunction getCalculatorY();

	public abstract MathFunction getCalculatorZ();

}
