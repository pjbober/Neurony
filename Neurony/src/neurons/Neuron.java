package neurons;

import utils.Random;
import utils.Vectors;
import activationfunction.ActivationFunction;
import activationfunction.ActivationFunctions;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import exceptions.BadVectorDimensionException;

public class Neuron {
	private final int nrOfInputs;
	private final ActivationFunctions activationFunction;

	@XStreamOmitField
	private ActivationFunction function;

	private double bias;
	/** Error used for back propagation */
	private double error;
	private double[] weights;
	private double[] previousWeights;

	public Neuron(int nrOfInputs, ActivationFunctions activationFunction) {
		this.nrOfInputs = nrOfInputs;
		this.activationFunction = activationFunction;
		this.function = activationFunction.getFunction();

		bias = Random.getRandom();
		weights = Random.getRandom(nrOfInputs);
	}

	public double getValue(double... input) throws BadVectorDimensionException {
		return function.getValue(Vectors.multiply(weights, input) + bias);
	}

	public double getDerivtiveValue(double... input)
			throws BadVectorDimensionException {
		return function.getDerivativeValue(Vectors.multiply(weights, input)
				+ bias);
	}

	/**
	 * Modifies weights by given deltas. Each weight is changed by adding given
	 * value, e.g. when weights are [1, 2] and you invoke modifyWeightsBy(2,3)
	 * you get weights modified to: [1 + 2, 2 + 3] = [3, 5]
	 * 
	 * @param input
	 *            Deltas
	 */
	void modifyWeightsBy(double... input) {
		for (int i = 0; i < weights.length; i++) {
			weights[i] += input[i];
		}
	}

	public void modifyBiasBy(double value) {
		bias += value;
	}

	/**
	 * Multiplies each weight by given value.
	 * 
	 * @param value
	 */
	void multiplyWeightBy(double value) {
		for (int i = 0; i < weights.length; i++) {
			weights[i] *= value;
		}
	}

	void multiplyBiasBy(double value) {
		bias *= value;
	}

	public double[] getWeights() {
		return weights;
	}

	public double[] getPreviousWeights() {
		return previousWeights;
	}

	public void setPreviousWeights(double[] previousWeights) {
		this.previousWeights = previousWeights;
	}
	
	public void setWeights(double[] weights){
			this.weights = weights;
	}

	double getBias() {
		return bias;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	private Object readResolve() {
		function = activationFunction.getFunction();
		return this;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

}
