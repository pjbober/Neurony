package trainers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kohonen.KohonenNetwork;
import kohonen.KohonenNeuron;
import metric.Metric;
import metric.impl.EuclideanMetric;

import org.apache.log4j.Logger;

import utils.Utils;

public class SelfOrganizingMapTrainer {
	private Logger logger = Logger.getLogger(getClass());

	private int allStepsNumber;
	private double learningRate = 0.5;
	private KohonenNetwork network;
	private Metric metric = new EuclideanMetric();

	private boolean isLearningRateDynamic;
	private boolean isConscienceUsed;
	private boolean isShufflingUsed;

	private double timeConstant;
	private double networkInitialDiameter;

	private int currrentStep;
	private double currentOmega;
	private double currentDecayRate;
	private KohonenNeuron currentWinningNeuron;

	private int epochs;
	private int currentEpoch;

	private String normalizedInputOutputFile;

	private double conscienceBeta = 0.5;
	private double conscienceGamma = 0.75;

	private WinningNeuronFinder winningNeuronFinder = new WinningNeuronFinder();

	private Map<KohonenNeuron, List<KohonenNeuron>> neighboursMap = new HashMap<KohonenNeuron, List<KohonenNeuron>>();

	public SelfOrganizingMapTrainer(KohonenNetwork network) {
		this.network = network;
		for (KohonenNeuron neuron : network.getNeurons()) {
			neighboursMap.put(neuron, new ArrayList<>(network.getNeurons()));
		}
	}

	private class WinningNeuronFinder {
		double winningNeuronOutput;

		private double calculateNeuronDistance(double[] vector1,
				double[] vector2) {
			// double output = Utils.dotProduct(vector1, vector2);
			double output = metric.getQuasiDistance(vector1, vector2);
			// normalize
			// map to bipolar
			return output;
		}

		public void findWinningNeuron(double[] input,
				List<KohonenNeuron> neurons) {
			winningNeuronOutput = Double.MAX_VALUE;
			double neuronsNumber = network.getNeuronsNumber();
			for (KohonenNeuron neuron : neurons) {
				double distance = calculateNeuronDistance(input,
						neuron.getWeights());
				if (isConscienceUsed) {
					distance += currentDecayRate * conscienceGamma
							* (neuronsNumber * neuron.getBias() - 1);
				}
				if (distance < winningNeuronOutput) {
					winningNeuronOutput = distance;
					currentWinningNeuron = neuron;
				}
			}
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
		winningNeuronFinder.findWinningNeuron(input, network.getNeurons());
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
			new KohonnenImputIO().saveInput(normalizedInputOutputFile,
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
		train(new KohonnenImputIO().loadInput(filePath), Calendar.getInstance()
				.getTimeInMillis());
	}

	public void train(String filePath, long seed) {
		train(new KohonnenImputIO().loadInput(filePath), seed);
	}

	private class KohonnenImputIO {
		public void saveInput(String filePath, List<double[]> input) {
			int inputDImension = network.getNumberOfInputs();
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(
						new FileWriter(new File(filePath)));
				for (double[] inputElement : input) {
					StringBuilder stringBuilder = new StringBuilder();
					int i;
					for (i = 0; i < inputDImension - 1; ++i) {
						stringBuilder.append(inputElement[i]).append(" ");
					}
					stringBuilder.append(inputElement[i]).append("\n");
					bufferedWriter.write(stringBuilder.toString());
				}
				bufferedWriter.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public List<double[]> loadInput(String filePath) {
			List<double[]> input = new ArrayList<>();
			int inputDImension = network.getNumberOfInputs();
			String line;
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(new File(filePath)));
				while ((line = bufferedReader.readLine()) != null) {
					double[] inputRecord = new double[inputDImension];
					int i = 0;
					for (String weight : line.split("\\s")) {
						try {
							inputRecord[i++] = Double.parseDouble(weight);
						} catch (NumberFormatException e) {
							logger.warn("Imput not recognized");
						}
					}
					input.add(inputRecord);
				}
				bufferedReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return input;
		}
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
}
