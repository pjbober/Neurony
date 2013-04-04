package activationfunction;

public class SigmoidActivationFunction implements ActivationFunction {

	@Override
	public double getValue(double x) {
		return 1.0 / (1 + Math.exp(-x));
	}

	@Override
	public double getDerivativeValue(double x) {
		double exp = Math.exp(x);
		return exp / (Math.pow(exp + 1, 2));
	}
}
