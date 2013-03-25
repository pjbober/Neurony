package neurons.layers;

import exceptions.BadVectorDimensionException;
import activationfunction.ActivationFunctions;

public class InputNeuronLayer extends NeuronLayer {

	public InputNeuronLayer(int nrOfNeurons,
			ActivationFunctions activationFunctions) {
		super(nrOfNeurons, 1, activationFunctions);
	}

	@Override
	public double[] getValues(double... input)
			throws BadVectorDimensionException {
		return input;
	}
}
