package de.maltesermailo.magic.effects.visuals;

import org.bukkit.util.Vector;

import de.maltesermailo.magic.effects.MathEffect;
import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.math.MathFunction;

public class EffectLine extends MathEffect {

	private Vector direction;
	private float length;
	private ParticleData data;

	public EffectLine(int particleCount, Vector direction, float length, ParticleData data) {
		super(particleCount);

		this.direction = direction;
		this.length = length;
		this.data = data;
	}

	@Override
	public MathFunction getCalculatorX() {
		return (n) -> {
			return (Math.max(n / getParticleCount() * this.length, 0.000001D)) * this.direction.getX();
		};
	}

	@Override
	public MathFunction getCalculatorY() {
		return (n) -> {
			return (Math.max(n / getParticleCount() * this.length, 0.000001D)) * this.direction.getY();
		};
	}

	@Override
	public MathFunction getCalculatorZ() {
		return (n) -> {
			return (Math.max(n / getParticleCount() * this.length, 0.000001D)) * this.direction.getZ();
		};
	}

	@Override
	public ParticleData getData(int particleId) {
		return this.data;
	}

	@Override
	public EffectLine clone() {
		return (EffectLine) super.clone();
	}

}
