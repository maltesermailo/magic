package de.maltesermailo.magic.effects.math;

public class MathFunctionExpon implements MathFunction {

	private double a, b, c, d;

	public MathFunctionExpon() {
		this(1, 1, 0, 0);
	}

	public MathFunctionExpon(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public double calc(double x) {
		return this.a * Math.pow(Math.E, x * this.b + this.c) + this.d;
	}

}
