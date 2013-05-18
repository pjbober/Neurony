package serializator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import kohonen.KohonenNetwork;
import kohonen.KohonenNeuron;

public abstract class KohonenSerializator {
	public static void saveToFile(String filePath, KohonenNetwork network) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					new File(filePath)));

			DecimalFormat decimalFormat = new DecimalFormat("#.###");
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(
					Locale.US));
			for (KohonenNeuron neuron : network.getNeurons()) {
				StringBuilder stringBuilder = new StringBuilder();
				int i;
				for (i = 0; i < network.getNumberOfInputs() - 1; ++i) {
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

	public static void saveToFileWithKohonnenCoords(String filePath,
			KohonenNetwork network) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					new File(filePath)));

			DecimalFormat decimalFormat = new DecimalFormat("#");
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(
					Locale.US));
			for (KohonenNeuron neuron : network.getNeurons()) {
				StringBuilder stringBuilder = new StringBuilder();
				int i;
				for (i = 0; i < 2; ++i) {
					stringBuilder.append(
							decimalFormat.format(neuron
									.getKohonnenCoordinates()[i])).append(" ");
				}
				for (i = 0; i < network.getNumberOfInputs() - 1; ++i) {
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

}
