package kohonen.trainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kohonen.kohonen.KohonenNetwork;
import kohonen.kohonen.KohonenNeuron;
import kohonen.metric.Metric;
import kohonen.metric.Metrices;
import utils.Utils;

public class SelfOrganizingMapTrainer {
	private int allStepsNumber;
	private double learningRate = 0.5;
	private KohonenNetwork network;
	private Metric metric = Metrices.EUCLIDEAN.getMetric();

	private boolean isLearningRateDynamic;
	private boolean isConscienceUsed;
	private boolean isShufflingUsed;

	private double timeConstant;
	private double networkInitialDiameter;

	private int currrentStep;
	private double currentOmega;
	double currentDecayRate;
	private KohonenNeuron currentWinningNeuron;

	private int epochs;
	private int currentEpoch;

	private String normalizedInputOutputFile;

	private double conscienceBeta = 0.5;
	double conscienceGamma = 0.75;

	private WinningNeuronFinder winningNeuronFinder;

	private Map<KohonenNeuron, List<KohonenNeuron>> neighboursMap = new HashMap<KohonenNeuron, List<KohonenNeuron>>();

	public SelfOrganizingMapTrainer(KohonenNetwork network) {
		this.network = network;
		this.winningNeuronFinder = new WinningNeuronFinder(this, metric);
		for (KohonenNeuron neuron : network.getNeurons()) {
			neighboursMap.put(neuron, new ArrayList<>(network.getNeurons()));
		}
	}

	private void updateNeuronsWeights(List<KohonenNeuron> neurons,
			double[] input) {
		for (KohonenNeuron neuron : neurons) {
			adjustNeuronWeights(neuron, input);
		}
	}

	private void adjustNeuronWeights(KohonenNeuron neuron, double[] input) {
		double[] weights = neuron.getWeights();
		double[] updatedWeights = new double[weights.length];
		for (int i = 0; i < weights.length; ++i) {
			updatedWeights[i] = weights[i] + getLearningRate()
					* getNeighbourFunctionValue(neuron)
					* (input[i] - weights[i]);
		}
		neuron.setWeights(updatedWeights);
	}

	private void updateCurrentOmega() {
		currentOmega = networkInitialDiameter
				* Math.exp(-(double) currrentStep / timeConstant);
	}

	private double getNeighbourFunctionValue(KohonenNeuron neuron) {
		return Math.exp(-(Math.pow(
				metric.getDistance(neuron.getWeights(),
						currentWinningNeuron.getWeights()), 2) / (2 * Math.pow(
				currentOmega, 2))));
	}

	private double getLearningRate() {
		if (isLearningRateDynamic == false) {
			return learningRate;
		} else {
			return learningRate * currentDecayRate;
		}
	}

	public List<KohonenNeuron> determineWinningNeuronNeighbours() {
		List<KohonenNeuron> possibleNeighbours = neighboursMap
				.get(currentWinningNeuron);
		List<KohonenNeuron> neighbours = new ArrayList<>();
		for (KohonenNeuron neuron : possibleNeighbours) {
			if (metric.getDistance(neuron.getKohonnenCoordinates(),
					currentWinningNeuron.getKohonnenCoordinates()) <= currentOmega) {
				neighbours.add(neuron);
			}
		}
		neighboursMap.put(currentWinningNeuron, neighbours);
		return neighbours;
	}

	private void updateBiases() {
		for (KohonenNeuron neuron : network.getNeurons()) {
			double bias = neuron.getBias();
			if (neuron != currentWinningNeuron) {
				neuron.setBias(bias + conscienceBeta * (1 - bias));
			} else {
				neuron.setBias(bias + conscienceBeta * (-bias));
			}
		}
	}

	private void updateDecayRate() {
		currentDecayRate = Math.exp(-(double) currrentStep / allStepsNumber);
	}

	public void update(double[] input) {
		// logger.info("Network update. Step = " + currrentStep
		// + ". Learning rate = " + getLearningRate()
		// + ". Neighbourhood function omega = " + currentOmega);
		winningNeuronFinder.findWinningNeuron(input, network);
		updateNeuronsWeights(determineWinningNeuronNeighbours(), input);
	}

	private void initializeBiases() {
		double initialBias = (double) 1 / network.getNeuronsNumber();
		for (KohonenNeuron neuron : network.getNeurons()) {
			neuron.setBias(initialBias);
		}
	}

	public void train(List<double[]> input, long seed) {
		allStepsNumber = input.size() * epochs;
		networkInitialDiameter = network.getKohonenAreaWidth() / 2;
		currentOmega = networkInitialDiameter != 1 ? networkInitialDiameter
				: Math.sqrt(2);
		timeConstant = allStepsNumber / Math.log(currentOmega);
		// logger.info("Training started. Number of all steps = " +
		// allStepsNumber
		// + ". Time constant = " + timeConstant);
		List<double[]> normalizedInput = new ArrayList<>(input.size());
		for (double[] inputElement : input) {
			normalizedInput.add(Utils.normalize(inputElement));
		}
		if (normalizedInputOutputFile != null) {
			new KohonnenInputIO(network).saveInput(normalizedInputOutputFile,
					normalizedInput);
		}
		Random random = new Random(seed);
		initializeBiases();
		for (currentEpoch = 0; currentEpoch < epochs; ++currentEpoch) {
			if (isShufflingUsed) {
				Collections.shuffle(normalizedInput, random);
			}
			for (double[] inputRecord : normalizedInput) {
				updateDecayRate();
				update(inputRecord);
				if (isConscienceUsed) {
					updateBiases();
				}
				++currrentStep;
				updateCurrentOmega();
			}
		}
	}

	public void train(String filePath) {
		train(new KohonnenInputIO(network).loadInput(filePath), System.currentTimeMillis());
	}

	public void train(String filePath, long seed) {
		train(new KohonnenInputIO(network).loadInput(filePath), seed);
	}

	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	public boolean isLearningRateDynamic() {
		return isLearningRateDynamic;
	}

	public void setLearningRateDynamic(boolean isLearningRateDynamic) {
		this.isLearningRateDynamic = isLearningRateDynamic;
	}

	public boolean isShufflingUsed() {
		return isShufflingUsed;
	}

	public void setShufflingUsed(boolean isShufflingUsed) {
		this.isShufflingUsed = isShufflingUsed;
	}

	public boolean isConscienceUsed() {
		return isConscienceUsed;
	}

	public void setConscienceUsed(boolean isConscienceUsed) {
		this.isConscienceUsed = isConscienceUsed;
	}

	public double getConscienceBeta() {
		return conscienceBeta;
	}

	public void setConscienceBeta(double conscienceBeta) {
		this.conscienceBeta = conscienceBeta;
	}

	public double getConscienceGamma() {
		return conscienceGamma;
	}

	public void setConscienceGamma(double conscienceGamma) {
		this.conscienceGamma = conscienceGamma;
	}

	public String getNormalizedInputOutputFile() {
		return normalizedInputOutputFile;
	}

	public void setNormalizedInputOutputFile(String normalizedInputOutputFile) {
		this.normalizedInputOutputFile = normalizedInputOutputFile;
	}

	public void setInitialLearningRate(double initialLearningRate) {
		this.learningRate = initialLearningRate;
	}

	public void setCurrentWinningNeuron(KohonenNeuron neuron) {
		this.currentWinningNeuron = neuron;
	}
}
