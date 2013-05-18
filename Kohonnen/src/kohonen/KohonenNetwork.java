package kohonen;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class KohonenNetwork {
	private List<KohonenNeuron> neurons;
	private int numberOfInputs;
	private final int neuronsNumber;
	private double kohonenAreaWidth = 200;
	final private double leftDownKohonnenCornerX;
	final private double leftDownKohonnenCornerY;

	public List<KohonenNeuron> getNeurons() {
		return neurons;
	}

	public KohonenNetwork(int numberOfinputs, int neuronsPerRow,
			double kohonenAreaWidth) {
		this(System.currentTimeMillis(), numberOfinputs, neuronsPerRow,
				kohonenAreaWidth);
	}

	public KohonenNetwork(long seed, int numberOfInputs, int neuronsPerRow,
			double kohonenAreaWidth) {
		this.neuronsNumber = neuronsPerRow * neuronsPerRow;
		this.numberOfInputs = numberOfInputs;
		this.kohonenAreaWidth = kohonenAreaWidth;
		leftDownKohonnenCornerX = -kohonenAreaWidth / 2;
		leftDownKohonnenCornerY = -kohonenAreaWidth / 2;
		neurons = new ArrayList<>(neuronsNumber);
		Random randomGenerator = new Random(seed);
		double interval = (double) kohonenAreaWidth / (neuronsPerRow - 1);
		for (int i = 0; i < neuronsPerRow; ++i) {
			for (int j = 0; j < neuronsPerRow; ++j) {
				double[] weights = new double[numberOfInputs];
				for (int k = 0; k < numberOfInputs; ++k) {
					weights[k] = randomGenerator.nextDouble() * 2 - 1;
				}
				neurons.add(new KohonenNeuron(weights, new double[] {
						leftDownKohonnenCornerX + i * interval,
						leftDownKohonnenCornerY + j * interval }));
			}
		}
	}

	public double getDiameter() {
		return kohonenAreaWidth * Math.sqrt(kohonenAreaWidth);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		DecimalFormat format = new DecimalFormat("#.###");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		for (KohonenNeuron neuron : neurons) {
			builder.append("[").append(format.format(neuron.getWeights()[0]))
					.append(", ").append(format.format(neuron.getWeights()[1]))
					.append("]").append(" ");
		}
		return builder.toString();
	}

	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	public int getNeuronsNumber() {
		return neuronsNumber;
	}

	
	public double getKohonenAreaWidth() {
		return kohonenAreaWidth;
	}
}
