package kohonen.kohonen;

import static kohonen.serializator.Formatters.TWO_PRECISION_ZEROS;


public class KohonenNeuron {
	private int id;
	private double[] weights;
	private double[] kohonnenCoordinates;
	private double bias;

	public KohonenNeuron(double[] weights, double[] kohonnenCoordinates) {
		super();
		this.weights = weights;
		this.kohonnenCoordinates = kohonnenCoordinates;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public void setWeight(double weight, int index) {
		this.weights[index] = weight;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double[] getKohonnenCoordinates() {
		return kohonnenCoordinates;
	}

	public void setKohonnenCoordinates(double[] kohonnenCoordinates) {
		this.kohonnenCoordinates = kohonnenCoordinates;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("weights = (");
		for (int i = 0; i < weights.length - 1; ++i) {
			builder.append(TWO_PRECISION_ZEROS.format(weights[i]));
			builder.append(", ");
		}
		builder.append(TWO_PRECISION_ZEROS.format(weights[weights.length - 1]));
		builder.append(")");

		builder.append(", KohCoords = (");
		for (int i = 0; i < kohonnenCoordinates.length - 1; ++i) {
			builder.append(TWO_PRECISION_ZEROS.format(kohonnenCoordinates[i]));
			builder.append(", ");
		}
		builder.append(TWO_PRECISION_ZEROS
				.format(kohonnenCoordinates[weights.length - 1]));
		builder.append(")");

		builder.append(", bias = ");
		builder.append(TWO_PRECISION_ZEROS.format(bias));

		return builder.toString();
	}
}
