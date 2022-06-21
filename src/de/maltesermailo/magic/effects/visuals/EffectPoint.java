package de.maltesermailo.magic.effects.visuals;

import de.maltesermailo.magic.effects.MathEffect;
import de.maltesermailo.magic.effects.ParticleData;
import de.maltesermailo.magic.effects.math.MathFunction;
import de.maltesermailo.magic.effects.math.MathFunctionConst;

public class EffectPoint extends MathEffect {

	private ParticleData data;

	public EffectPoint(int particleCount, ParticleData data) {
		super(particleCount);
		this.data = data;
	}

	@Override
	public MathFunction getCalculatorX() {
		return new MathFunctionConst(0.0D);
	}

	@Override
	public MathFunction getCalculatorY() {
		return new MathFunctionConst(0.0D);
	}

	@Override
	public MathFunction getCalculatorZ() {
		return new MathFunctionConst(0.0D);
	}

	@Override
	public ParticleData getData(int particleId) {
		return this.data;
	}
	
	@Override
	public EffectPoint clone() {
		return (EffectPoint) super.clone();
	}
	
}
