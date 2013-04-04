package utils;

import java.security.SecureRandom;

public class Random {
	private static final SecureRandom RANDOM = new SecureRandom();

	public static double getRandom() {
		// return RANDOM.nextDouble();
		return 0.5;
	}

	public static double[] getRandom(int count) {
		double[] rands = new double[count];

		for (int i = 0; i < count; i++) {
			rands[i] = 0.5; // RANDOM.nextDouble();
		}

		return rands;
	}
}
