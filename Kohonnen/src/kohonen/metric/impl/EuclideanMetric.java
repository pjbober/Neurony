package kohonen.metric.impl;

import kohonen.metric.Metric;

public class EuclideanMetric extends Metric {

	@Override
	public double getDistance(double[] x1, double[] x2) {
		return Math.sqrt(getQuasiDistance(x1, x2));
	}

	@Override
	public double getQuasiDistance(double[] x1, double[] x2) {
		double sum = 0;
		for (int i = 0; i < x1.length; ++i) {
			sum += Math.pow(x1[i] - x2[i], 2);
		}
		return sum;
	}
}
