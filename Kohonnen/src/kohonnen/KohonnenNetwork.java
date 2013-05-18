package kohonnen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class KohonnenNetwork {
	private List<KohonnenNeuron> neurons;
	private int numberOfInputs;
	private final int neuronsNumber;
	private double kohonenAreaWidth = 200;
	final private double leftDownKohonnenCornerX;
	final private double leftDownKohonnenCornerY;

	public List<KohonnenNeuron> getNeurons() {
		return neurons;
	}

	public KohonnenNetwork(int numberOfinputs, int neuronsPerRow,
			double kohonenAreaWidth) {
		this(Calendar.getInstance().getTimeInMillis(), numberOfinputs,
				neuronsPerRow, kohonenAreaWidth);
	}

	public KohonnenNetwork(long seed, int numberOfInputs, int neuronsPerRow,
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
				neurons.add(new KohonnenNeuron(weights, new double[] {
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
		for (KohonnenNeuron neuron : neurons) {
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

	public void saveToFile(String filePath) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					new File(filePath)));

			DecimalFormat decimalFormat = new DecimalFormat("#.###");
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(
					Locale.US));
			for (KohonnenNeuron neuron : getNeurons()) {
				StringBuilder stringBuilder = new StringBuilder();
				int i;
				for (i = 0; i < getNumberOfInputs() - 1; ++i) {
					stringBuilder.append(
							decimalFormat.format(neuron.getWeights()[i]))
							.append(" ");
				}
				stringBuilder.append(
						decimalFormat.format(neuron.getWeights()[i])).append(
						"\n");
				bufferedWriter.write(stringBuilder.toString());
			}
			bufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void saveToFileWithKohonnenCoords(String filePath) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					new File(filePath)));

			DecimalFormat decimalFormat = new DecimalFormat("#");
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(
					Locale.US));
			for (KohonnenNeuron neuron : getNeurons()) {
				StringBuilder stringBuilder = new StringBuilder();
				int i;
				for (i = 0; i < 2; ++i) {
					stringBuilder.append(
							decimalFormat.format(neuron
									.getKohonnenCoordinates()[i])).append(" ");
				}
				for (i = 0; i < getNumberOfInputs() - 1; ++i) {
					stringBuilder
							.append(decimalFormat.format(((neuron.getWeights()[i] + 1) * 255) / 2))
							.append(" ");
				}
				stringBuilder
						.append(decimalFormat.format(((neuron.getWeights()[i] + 1) * 255) / 2))
						.append("\n");
				bufferedWriter.write(stringBuilder.toString());
			}
			bufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public double getKohonenAreaWidth() {
		return kohonenAreaWidth;
	}
}
