package neurons;

import java.util.ArrayList;
import java.util.List;

import neurons.layers.InputNeuronLayer;
import neurons.layers.NeuronLayer;
import neurons.layers.OutputLayerNetwork;

import activationfunction.ActivationFunctions;

public class FeedForwardNet {

	private final List<NeuronLayer> layers;

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
	FeedForwardNet(int nrOfInputs, int nrOfOutputs, int nrOfHiddenLayers,
			List<Integer> neuronsInHiddenLayers,
			List<ActivationFunctions> activationFunctions,
			ActivationFunctions outputActivationFunction) {
		layers = new ArrayList<>(nrOfHiddenLayers + 1);

		layers.add(new InputNeuronLayer(nrOfInputs, activationFunctions.get(0)));

		for (int i = 1; i < nrOfHiddenLayers; i++) {
			NeuronLayer layer = new NeuronLayer(neuronsInHiddenLayers.get(i), layers
					.get(i - 1).getNrOfNeurons(), activationFunctions.get(i));

			layers.add(layer);
		}
		
		layers.add(new OutputLayerNetwork(nrOfOutputs, layers.get(nrOfHiddenLayers - 1).getNrOfNeurons(), outputActivationFunction));
	}
	
	public double[] getResponse(double[] input) throws Exception{
		throw new Exception("Not yet implemented");
	}
}
