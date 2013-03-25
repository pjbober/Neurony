package neurons.layers;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import neurons.Neuron;
import activationfunction.ActivationFunctions;
import exceptions.BadVectorDimensionException;

public class NeuronLayer {
	private final int nrOfNeurons;
	private final int nrOfInputPerNeuron;

	@XStreamImplicit
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
		if (input.length != nrOfInputPerNeuron) {
			throw new BadVectorDimensionException("Expected "
					+ nrOfInputPerNeuron + " values, but got " + input.length);
		}

		double[] values = new double[nrOfNeurons];

		for (int i = 0; i < nrOfNeurons; i++) {
			values[i] = neurons.get(i).getValue(input);
		}

		return values;
	}

	public int getNrOfNeurons() {
		return nrOfNeurons;
	}

	@Override
	public String toString() {
		return "Layer of " + nrOfNeurons + " neurons, " + nrOfInputPerNeuron
				+ " inputs per neuron";
	}
}
