package de.maltesermailo.magic.effects.visuals;

import java.util.List;

import org.bukkit.util.Vector;

import de.maltesermailo.magic.effects.MathEffect;
import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.math.MathFunction;
import de.maltesermailo.magic.effects.math.MathFunctionConst;

public class EffectCircle extends MathEffect {

	private float radius;
	private int rounds;

	private ParticleData data;

	public EffectCircle(int particleCount, float radius, ParticleData data) {
		this(particleCount, radius, 1, data);
	}

	public EffectCircle(int particleCount, float radius, int rounds, ParticleData data) {
		super(particleCount);

		this.radius = radius;
		this.rounds = rounds;
		this.data = data;
	}

	private EffectCircle(int particleCount, List<Vector> locations) {
		super(particleCount, locations);
	}

	@Override
	public ParticleData getData(int particleId) {
		return this.data;
	}

	@Override
	public MathFunction getCalculatorX() {
		return (n) -> {
			return this.getCalculatorRadius().calc(n)
					* Math.sin(this.rounds * 2 * Math.PI * (n / (this.getParticleCount())));
		};
	}

	@Override
	public MathFunction getCalculatorY() {
		return new MathFunctionConst(0);
	}

	@Override
	public MathFunction getCalculatorZ() {
		return (n) -> {
			return this.getCalculatorRadius().calc(n)
					* Math.cos(this.rounds * 2 * Math.PI * (n / this.getParticleCount()));
		};
	}

	@Override
	public EffectCircle clone() {
		return (EffectCircle) super.clone();
	}

	protected MathFunction getCalculatorRadius() {
		return new MathFunctionConst(this.radius);
	}

}
