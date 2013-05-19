package feedForward.activationfunction.impl;

import feedForward.activationfunction.ActivationFunction;

public class LinearActivationFunction implements ActivationFunction {

	@Override
	public double getValue(double x) {
		return x;
	}

	@Override
	public double getDerivativeValue(double x) {
		return 1;
	}

}
