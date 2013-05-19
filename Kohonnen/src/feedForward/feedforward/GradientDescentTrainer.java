package feedForward.feedforward;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import feedForward.exceptions.BadVectorDimensionException;
import feedForward.neurons.Neuron;
import feedForward.neurons.layers.NeuronLayer;

/**
 * Formulas used inside class can be found in book "Larning and Soft Computing",
 * Vojislav Keckman, 2001, chpt. 4
 */
public class GradientDescentTrainer {
	FeedForwardNet network;
	double momentum = 0.9;
	double learningRate = 0.01;
	int epochs = 1000;

	public GradientDescentTrainer() {
	}

	public GradientDescentTrainer(FeedForwardNet network) {
		this.network = network;
	}

	/** */
	private void propagateError(double[] networkAnswer, double[] desiredAnswer) {
		int lastLayerIndex = network.getLayersNumber() - 1;
		int neuronIndex = 0;
		double[] previousLayerOutput = network.getLayer(lastLayerIndex - 1)
				.getOutput();

		/* Determine error for output layer */
		for (Neuron neuron : network.getLayers().get(lastLayerIndex)
				.getNeurons()) {
			try {
				neuron.setError((desiredAnswer[neuronIndex] - networkAnswer[neuronIndex])
						* neuron.getDerivtiveValue(previousLayerOutput));
			} catch (BadVectorDimensionException e) {
				throw new RuntimeException(e);
			}
			++neuronIndex;
		}

		/* Determine error for hidden layers */
		for (int layerIndex = lastLayerIndex - 1; layerIndex > 0; --layerIndex) {
			NeuronLayer layer = network.getLayers().get(layerIndex);
			NeuronLayer nextLayer = network.getLayers().get(layerIndex + 1);
			neuronIndex = 0;
			previousLayerOutput = network.getLayer(layerIndex - 1).getOutput();

			for (Neuron neuron : layer.getNeurons()) {
				neuron.setError(0);
				for (Neuron nextLayerNeuron : nextLayer.getNeurons()) {
					neuron.setError(neuron.getError()
							+ nextLayerNeuron.getError()
							* nextLayerNeuron.getWeights()[neuronIndex]);
				}
				try {
					neuron.setError(neuron.getError()
							* neuron.getDerivtiveValue(previousLayerOutput));
				} catch (BadVectorDimensionException e) {
					throw new RuntimeException(e);
				}
				++neuronIndex;
			}
		}
	}

	private void adjustWeights() throws BadVectorDimensionException {
		int layersNumber = network.getLayersNumber();
		for (int layerIndex = 1; layerIndex < layersNumber; ++layerIndex) {
			NeuronLayer layer = network.getLayer(layerIndex);
			NeuronLayer previousLayer = network.getLayer(layerIndex - 1);
			for (Neuron neuron : layer.getNeurons()) {
				double[] weights = neuron.getWeights();
				neuron.modifyBiasBy(learningRate * neuron.getError());
				for (int weightIndex = 0; weightIndex < weights.length; ++weightIndex) {
					double weightMomentum;
					// TODO settings previous weights could be realized inside
					// neuron object

					// calculate momentum
					double[] previousWeights = neuron.getPreviousWeights();
					if (previousWeights != null) {
						weightMomentum = momentum
								* (weights[weightIndex] - previousWeights[weightIndex]);
					} else {
						weightMomentum = 0;
					}

					// remember weights
					neuron.setPreviousWeights(Arrays.copyOf(weights,
							weights.length));

					weights[weightIndex] = weights[weightIndex] + learningRate
							* neuron.getError()
							* previousLayer.getOutput()[weightIndex]
							+ weightMomentum;
				}
			}
		}
	}

	public void train(List<double[]> inputs, List<double[]> answers) {
		Iterator<double[]> answersIterator = answers.iterator();
		int epochsCount = 0;

		while (epochsCount < epochs) {
			answersIterator = answers.iterator();
			for (double[] input : inputs) {
				double[] answer = answersIterator.next();
				try {
					propagateError(network.getResponse(input), answer);
					adjustWeights();
				} catch (BadVectorDimensionException e) {
					throw new RuntimeException(e);
				}
			}
			++epochsCount;
		}
	}

	public FeedForwardNet getNetwork() {
		return network;
	}

	public void setNetwork(FeedForwardNet network) {
		this.network = network;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}
}