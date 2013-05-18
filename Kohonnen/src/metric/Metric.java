package metric;

public abstract class Metric {
	public abstract double getDistance(double[] x1, double[] x2);

	public abstract double getQuasiDistance(double[] x1, double[] x2);
}
