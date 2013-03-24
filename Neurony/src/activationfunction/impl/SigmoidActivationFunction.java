package activationfunction.impl;

import activationfunction.ActivationFunction;

public class SigmoidActivationFunction implements ActivationFunction {

	@Override
	public double getValue(double x) {
		return 1.0 / (1 + Math.exp(-x));
	}

}