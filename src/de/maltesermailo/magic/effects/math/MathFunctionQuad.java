package de.maltesermailo.magic.effects.math;

public class MathFunctionQuad implements MathFunction {

	private double a, b, c;

	public MathFunctionQuad(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public double calc(double x) {
		return this.a * x * x + this.b * x + this.c;
	}
}
