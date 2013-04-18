package neurons;

import activationfunction.ActivationFunctions;

public class InputNeuron extends Neuron {

	public InputNeuron(int nrOfInputs, ActivationFunctions activationFunction) {
		super(nrOfInputs, activationFunction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setError(double error) {
		System.err.println(error);
	}

	@Override
	public void setPreviousWeights(double[] previousWeights) {
	}

	@Override
	public void modifyBiasBy(double value) {
	}

	@Override
	void multiplyBiasBy(double value) {
	}

	@Override
	void modifyWeightsBy(double... input) {
	}

	@Override
	void multiplyWeightBy(double value) {
	}
}
