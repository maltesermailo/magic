package de.maltesermailo.magic.effects.math;

public class MathFunctionConst implements MathFunction {

	private double a;

	public MathFunctionConst(double a) {
		this.a = a;
	}

	@Override
	public double calc(double x) {
		return this.a;
	}
}
