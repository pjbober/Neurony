package feedForward.neurons.layers;

import feedForward.activationfunction.ActivationFunctions;
import feedForward.exceptions.BadVectorDimensionException;
import feedForward.neurons.InputNeuron;

public class InputNeuronLayer extends NeuronLayer {

	public InputNeuronLayer(int nrOfNeurons,
			ActivationFunctions activationFunctions) {
		super(nrOfNeurons, 1, activationFunctions);
		
		this.neurons.clear();
		for (int i = 0; i < nrOfNeurons; i++) {
			this.neurons.add(new InputNeuron(1, activationFunctions));
		}
		
	}

	@Override
	public double[] getValues(double... input)
			throws BadVectorDimensionException {
		output = input;
		return output;
	}
}
