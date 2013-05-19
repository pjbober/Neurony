package feedForward.feedforward;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import feedForward.activationfunction.ActivationFunctions;
import feedForward.exceptions.BadVectorDimensionException;
import feedForward.neurons.layers.InputNeuronLayer;
import feedForward.neurons.layers.NeuronLayer;
import feedForward.neurons.layers.OutputLayerNetwork;

public class FeedForwardNet {
	@XStreamImplicit
	protected final List<NeuronLayer> layers;

	public List<NeuronLayer> getLayers() {
		return layers;
	}

	/**
	 * Creates a new feed forward neural net.
	 * 
	 * @param nrOfInputs
	 *            Number of inputs to the net (size of the input vector)
	 * @param nrOfHiddenLayers
	 *            Number of hidden layers.
	 * @param neuronsInLayer
	 *            Number of neurons in each layer starting with input layer.
	 * @param activationFunctions
	 *            Type of activation functions in each layer.
	 */
	public FeedForwardNet(int nrOfInputs, int nrOfOutputs,
			int nrOfHiddenLayers, List<Integer> neuronsInHiddenLayers,
			List<ActivationFunctions> activationFunctions,
			ActivationFunctions outputActivationFunction) {
		layers = new ArrayList<>(nrOfHiddenLayers + 2);

		layers.add(new InputNeuronLayer(nrOfInputs, activationFunctions.get(0)));

		for (int i = 1; i < nrOfHiddenLayers + 1; i++) {
			NeuronLayer layer = new NeuronLayer(
					neuronsInHiddenLayers.get(i - 1), layers.get(i - 1)
							.getNrOfNeurons(), activationFunctions.get(i - 1));
			layers.add(layer);
		}

		layers.add(new OutputLayerNetwork(nrOfOutputs, layers.get(
				nrOfHiddenLayers).getNrOfNeurons(), outputActivationFunction));
	}

	public double[] getResponse(double... input)
			throws BadVectorDimensionException {
		double[] values = input;

		for (int i = 0; i < layers.size(); i++) {
			values = layers.get(i).getValues(values);
		}

		return values;
	}

	/** Facade function for easier access to network layers. */
	public NeuronLayer getLayer(int i) {
		return layers.get(i);
	}

	/** Function for easier check of layers number. */
	public int getLayersNumber() {
		return layers.size();
	}
}