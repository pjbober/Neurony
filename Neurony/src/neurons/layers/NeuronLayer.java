package neurons.layers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neurons.Neuron;

import exceptions.BadVectorDimensionException;

import activationfunction.ActivationFunctions;

public class NeuronLayer {
	private final int nrOfNeurons;
	private final int nrOfInputPerNeuron;

	List<Neuron> neurons;

	public NeuronLayer(int nrOfNeurons, int nrOfInputPerNeuron,
			ActivationFunctions activationFunctions) {
		this.nrOfNeurons = nrOfNeurons;
		this.nrOfInputPerNeuron = nrOfInputPerNeuron;

		neurons = new ArrayList<>(nrOfNeurons);
		for (int i = 0; i < nrOfNeurons; i++) {
			neurons.add(new Neuron(nrOfInputPerNeuron, activationFunctions));
		}
	}

	public double[] getValues(double... input)
			throws BadVectorDimensionException {
		if (input.length != nrOfInputPerNeuron * nrOfNeurons) {
			throw new BadVectorDimensionException();
		}

		double[] values = new double[nrOfNeurons];

		for (int i = 0; i < nrOfNeurons; i++) {
			values[i] = neurons.get(i).getValue(
					Arrays.copyOfRange(input, i * nrOfInputPerNeuron, (i + 1)
							* nrOfInputPerNeuron));
		}

		return values;
	}

	public int getNrOfNeurons() {
		return nrOfNeurons;
	}
}
