package de.maltesermailo.magic.effects.visuals;

import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.math.MathFunction;
import de.maltesermailo.magic.effects.math.MathFunctionLinear;

public class EffectHelix extends EffectCircle {

	private float height;

	public EffectHelix(int particleCount, float radius, int rounds, float height, ParticleData data) {
		super(particleCount, radius, rounds, data);
		this.height = height;
	}
	
	@Override
	public MathFunction getCalculatorY() {
		int count = this.getParticleCount();
		
		return new MathFunctionLinear(this.height / count, 1 / count);
	}
	
	@Override
	public EffectHelix clone() {
		return (EffectHelix) super.clone();
	}
	
}
