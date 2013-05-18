package metric;

import metric.impl.EuclideanMetric;

public enum Metrices {
	EUCLIDEAN(EuclideanMetric.class);

	Class<? extends Metric> metric;

	private Metrices(Class<? extends Metric> metric) {
		this.metric = metric;
	}

	public Metric getMetric() {
		switch (this) {
		case EUCLIDEAN: {
			return new EuclideanMetric();
		}
		default:
			throw new RuntimeException("Unexpected enum value exception");
		}
	}
}
