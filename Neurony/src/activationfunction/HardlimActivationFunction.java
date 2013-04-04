package activationfunction;

public class HardlimActivationFunction implements ActivationFunction {

	@Override
	public double getValue(double x) {
		return (x >= 0) ? 1 : 0;
	}

	@Override
	public double getDerivativeValue(double x) {
		return 0;
	}
}
