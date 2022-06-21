package de.maltesermailo.magic.effects.visuals;

import de.maltesermailo.magic.effects.BuildedEffect;
import de.maltesermailo.magic.effects.ParticleData;

public class EffectSphere extends BuildedEffect {

	private int particleCount;
	private float radius;
	private ParticleData data;

	public EffectSphere(int particleCount, float radius, ParticleData data) {
		this.particleCount = particleCount;
		this.radius = radius;
		this.data = data;
	}
	
	@Override
	public void calculate() {
		for (int i = 0; i < this.particleCount; i++) {
			double piVal = (double) (i) / (double) (this.particleCount) * Math.PI;

			double radius = Math.sin(piVal) * this.radius;

			float offsetY = (float) (Math.sin(piVal + Math.PI * 0.5)) * this.radius;
			
			EffectCircle circle = new EffectCircle(this.particleCount, (float) radius, 1, this.data);
			this.addEffect(circle);
			circle.moveY(offsetY);
		}
	}
	
	@Override
	public ParticleData getData(int particleId) {
		return this.data;
	}
	
	@Override
	public EffectSphere clone() {
		return (EffectSphere) super.clone();
	}
	
}
