package neurons.layers;

import activationfunction.ActivationFunctions;
import exceptions.BadVectorDimensionException;

public class InputNeuronLayer extends NeuronLayer {

	public InputNeuronLayer(int nrOfNeurons,
			ActivationFunctions activationFunctions) {
		super(nrOfNeurons, 1, activationFunctions);
	}

	@Override
	public double[] getValues(double... input)
			throws BadVectorDimensionException {
		output = input;
		return output;
	}
}
