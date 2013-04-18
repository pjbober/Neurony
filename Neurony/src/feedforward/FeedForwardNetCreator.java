package feedforward;

import java.util.Arrays;

import activationfunction.ActivationFunctions;

public class FeedForwardNetCreator {

	private final ActivationFunctions[] activationFunctions;
	private final Integer[] nrOfNeuronsInHiddenLayers;
	private ActivationFunctions outputActivationFunction;
	private int nrOfHiddenLayers;
	private int nrOfInputs;
	private int nrOfOutputs;

	private FeedForwardNetCreator(int nrOfHiddenLayers) {
		this.nrOfHiddenLayers = nrOfHiddenLayers;
		activationFunctions = new ActivationFunctions[nrOfHiddenLayers + 1];
		nrOfNeuronsInHiddenLayers = new Integer[nrOfHiddenLayers];
	}

	public FeedForwardNetCreator withNrOfInputs(int nrOfInputs) {
		this.nrOfInputs = nrOfInputs;

		return this;
	}

	public FeedForwardNetCreator withNrOfOutputs(int nrOfOutputs) {
		this.nrOfOutputs = nrOfOutputs;

		return this;
	}

	/***
	 * Applies one activation function for all layers.
	 * 
	 * @param function
	 *            Type of function.
	 * @return Feed forward net creator.
	 */
	public FeedForwardNetCreator withSameActivationFunction(
			ActivationFunctions function) {
		for (int i = 0; i < nrOfHiddenLayers; i++) {
			activationFunctions[i] = function;
		}

		outputActivationFunction = function;

		return this;
	}

	public FeedForwardNetCreator withActivationFunctionInHiddenLayer(int layer,
			ActivationFunctions function) {
		activationFunctions[layer] = function;

		return this;
	}

	public FeedForwardNetCreator withOutputActivationFunction(
			ActivationFunctions function) {
		outputActivationFunction = function;

		return this;
	}

	public FeedForwardNetCreator withSameNumberOfNeuronsInLayers(
			int nrOfNeuronsInLayers) {
		for (int i = 0; i < nrOfHiddenLayers; i++) {
			this.nrOfNeuronsInHiddenLayers[i] = nrOfNeuronsInLayers;
		}

		return this;
	}

	public FeedForwardNetCreator withNumberOfNeuronsInHiddenLayer(int layer,
			int neurons) {
		this.nrOfNeuronsInHiddenLayers[layer] = neurons;

		return this;
	}

	public FeedForwardNet build() {
		return new FeedForwardNet(nrOfInputs, nrOfOutputs, nrOfHiddenLayers,
				Arrays.asList(nrOfNeuronsInHiddenLayers),
				Arrays.asList(activationFunctions), outputActivationFunction);
	}

	public static FeedForwardNetCreator builder(int nrOfHiddenLayers) {
		return new FeedForwardNetCreator(nrOfHiddenLayers);
	}
}
