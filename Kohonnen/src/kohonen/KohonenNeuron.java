package kohonen;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
		NumberFormat numberFormat = new DecimalFormat("0.00");
		StringBuilder builder = new StringBuilder();

		builder.append("weights = (");
		for (int i = 0; i < weights.length - 1; ++i) {
			builder.append(numberFormat.format(weights[i]));
			builder.append(", ");
		}
		builder.append(numberFormat.format(weights[weights.length - 1]));
		builder.append(")");

		builder.append(", KohCoords = (");
		for (int i = 0; i < kohonnenCoordinates.length - 1; ++i) {
			builder.append(numberFormat.format(kohonnenCoordinates[i]));
			builder.append(", ");
		}
		builder.append(numberFormat
				.format(kohonnenCoordinates[weights.length - 1]));
		builder.append(")");

		builder.append(", bias = ");
		builder.append(numberFormat.format(bias));

		return builder.toString();
	}
}
