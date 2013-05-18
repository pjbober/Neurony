package utils;

public class Utils {
	public static double vectorLength(double[] input) {
		double sum = 0;
		for (double inputElement : input) {
			sum += inputElement * inputElement;
		}
		return Math.sqrt(sum);
	}

	public static double calculateNormalizationFactor(double[] input) {
		double length = vectorLength(input);
		return length != 0 ? 1 / length : 1;
	}

	public static double dotProduct(double[] vector1, double[] vector2) {
		double innerProduct = 0;
		for (int i = 0; i < vector1.length; ++i) {
			innerProduct += vector1[i] * vector2[i];
		}
		return innerProduct;
	}

	public static double[] normalize(final double[] vector) {
		double normalizationFactor = Utils.calculateNormalizationFactor(vector);
		double[] normalizedVector = vector.clone();
		for (int i = 0; i < vector.length; ++i) {
			normalizedVector[i] = vector[i] * normalizationFactor;
		}
		return normalizedVector;
	}
}
