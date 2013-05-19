package kohonen.trainers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kohonen.kohonen.KohonenNetwork;

import org.apache.log4j.Logger;

public class KohonnenInputIO {
	private Logger logger = Logger.getLogger(getClass());
	private KohonenNetwork network;

	public KohonnenInputIO(KohonenNetwork network) {
		this.network = network;
	}

	public void saveInput(String filePath, List<double[]> input) {
		int inputDImension = network.getNumberOfInputs();
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					new File(filePath)));
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
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					new File(filePath)));
			while ((line = bufferedReader.readLine()) != null) {
				double[] inputRecord = new double[inputDImension];
				int i = 0;
				for (String weight : line.split("\\s")) {
					try {
						inputRecord[i++] = Double.parseDouble(weight);
					} catch (NumberFormatException e) {
						logger.warn("Input not recognized");
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
