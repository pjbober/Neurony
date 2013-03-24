package activationfunction.impl;

import activationfunction.ActivationFunction;

public class HardlimActivationFunction implements ActivationFunction {

	@Override
	public double getValue(double x) {
		return  (x >= 0) ? 1 : 0;
	}

}
