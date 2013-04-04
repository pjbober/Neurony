package activationfunction.impl;

import activationfunction.ActivationFunction;

public class SigmoidActivationFunction implements ActivationFunction {

	@Override
	public double getValue(double x) {
		return 1.0 / (1 + Math.exp(-x));
	}

	@Override
	public double getDerivativeValue(double x) {
		return Math.exp(x) / Math.pow(Math.exp(x) + 1, 2);
	}

}