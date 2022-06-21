package de.maltesermailo.magic.effects.math;

public class MathFunctionLinear implements MathFunction {

	private double a, b;

	public MathFunctionLinear(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double calc(double x) {
		return this.a * x + this.b;
	}
}
