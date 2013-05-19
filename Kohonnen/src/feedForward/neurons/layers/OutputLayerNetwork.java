package feedForward.neurons.layers;

import feedForward.activationfunction.ActivationFunctions;

public class OutputLayerNetwork extends NeuronLayer {

	public OutputLayerNetwork(int nrOfNeurons, int nrOfInputPerNeuron,
			ActivationFunctions activationFunctions) {
		super(nrOfNeurons, nrOfInputPerNeuron, activationFunctions);
	}

}
