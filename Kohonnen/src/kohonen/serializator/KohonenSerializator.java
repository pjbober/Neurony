package kohonen.serializator;

import static kohonen.serializator.Formatters.THREE_PRECISION;
import static kohonen.serializator.Formatters.ZERO_PRECISION;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import kohonen.kohonen.KohonenNetwork;
import kohonen.kohonen.KohonenNeuron;

public abstract class KohonenSerializator {
	public static void saveToFile(String filePath, KohonenNetwork network) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					new File(filePath)));

			for (KohonenNeuron neuron : network.getNeurons()) {
				StringBuilder stringBuilder = new StringBuilder();
				int i;
				for (i = 0; i < network.getNumberOfInputs() - 1; ++i) {
					stringBuilder.append(
							THREE_PRECISION.format(neuron.getWeights()[i]))
							.append(" ");
				}
				stringBuilder.append(
						THREE_PRECISION.format(neuron.getWeights()[i])).append(
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

			for (KohonenNeuron neuron : network.getNeurons()) {
				StringBuilder stringBuilder = new StringBuilder();
				int i;
				for (i = 0; i < 2; ++i) {
					stringBuilder.append(
							ZERO_PRECISION.format(neuron
									.getKohonnenCoordinates()[i])).append(" ");
				}
				for (i = 0; i < network.getNumberOfInputs() - 1; ++i) {
					stringBuilder
							.append(ZERO_PRECISION.format(((neuron.getWeights()[i] + 1) * 255) / 2))
							.append(" ");
				}
				stringBuilder
						.append(ZERO_PRECISION.format(((neuron.getWeights()[i] + 1) * 255) / 2))
						.append("\n");
				bufferedWriter.write(stringBuilder.toString());
			}
			bufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
