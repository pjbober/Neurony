package neurons.layers;

import java.util.ArrayList;
import java.util.List;

import neurons.Neuron;
import activationfunction.ActivationFunctions;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import exceptions.BadVectorDimensionException;

public class NeuronLayer {
	private final int nrOfNeurons;
	private final int nrOfInputPerNeuron;
	/**
	 * Ancillary variable preserving lastest layer output. Used for error back
	 * propagation.
	 */
	protected double[] output;

	@XStreamImplicit
	private List<Neuron> neurons;

	public NeuronLayer(int nrOfNeurons, int nrOfInputPerNeuron,
			ActivationFunctions activationFunctions) {
		this.nrOfNeurons = nrOfNeurons;
		this.nrOfInputPerNeuron = nrOfInputPerNeuron;
		output = new double[nrOfNeurons];

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

		for (int i = 0; i < nrOfNeurons; i++) {
			output[i] = neurons.get(i).getValue(input);
		}

		return output;
	}

	public int getNrOfNeurons() {
		return nrOfNeurons;
	}

	@Override
	public String toString() {
		return "Layer of " + nrOfNeurons + " neurons, " + nrOfInputPerNeuron
				+ " inputs per neuron";
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public double[] getOutput() {
		return output;
	}
}
