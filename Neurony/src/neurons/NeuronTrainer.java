package neurons;

import java.util.List;

import utils.Vectors;
import exceptions.BadVectorDimensionException;
import exceptions.NeuronTrainException;

public class NeuronTrainer {
	private final Neuron neuron;
	private double learningRate;
	private double momentum;
	private int epochs;

	public NeuronTrainer(Neuron neuron, int epochs) {
		this.neuron = neuron;
		this.learningRate = 0.01;
		this.momentum = 0.5;
		this.epochs = epochs;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public void train(List<double[]> inputs, double[] answers)
			throws NeuronTrainException {
		if (inputs.size() != answers.length) {
			throw new NeuronTrainException(
					"Number of sample inputs differs from given answers");
		}

		for (int k = 0; k < epochs; k++) {
			for (int i = 0; i < inputs.size(); i++) {
				double[] input = inputs.get(i);
				double delta = getDelta(answers[i], input);

				// http://www.cse.unsw.edu.au/~billw/mldict.html#gendeltarule
				neuron.multiplyWeightBy(momentum); // consider momentum
				neuron.multiplyBiasBy(momentum);
				neuron.modifyWeightsBy(Vectors.multiply(input, delta)); // add
																		// delta
				neuron.modifyBiasBy(delta);// input for bias is always equal to
											// 1
			}
		}
	}

	private double getDelta(double answer, double[] input)
			throws NeuronTrainException {
		try {
			double neuronAnswer = neuron.getValue(input);
			double deriv = neuron.getDerivtiveValue(input);
			return learningRate * (answer - neuronAnswer) * deriv;
		} catch (BadVectorDimensionException e) {
			throw new NeuronTrainException("Cannot calculate delta");
		}
	}
}