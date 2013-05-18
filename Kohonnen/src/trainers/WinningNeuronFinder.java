package trainers;

import kohonen.KohonenNetwork;
import kohonen.KohonenNeuron;
import metric.Metric;

public class WinningNeuronFinder {
	private final Metric metric;
	private final SelfOrganizingMapTrainer trainer;
	private double winningNeuronOutput;

	public WinningNeuronFinder(SelfOrganizingMapTrainer trainer, Metric metric) {
		this.metric = metric;
		this.trainer = trainer;
	}

	private double calculateNeuronDistance(double[] vector1, double[] vector2) {
		// double output = Utils.dotProduct(vector1, vector2);
		// normalize
		// map to bipolar
		return metric.getQuasiDistance(vector1, vector2);
	}

	public void findWinningNeuron(double[] input, KohonenNetwork network) {
		winningNeuronOutput = Double.MAX_VALUE;
		double neuronsNumber = network.getNeuronsNumber();
		
		for (KohonenNeuron neuron : network.getNeurons()) {
			double distance = calculateNeuronDistance(input,
					neuron.getWeights());
			if (trainer.isConscienceUsed()) {
				distance += trainer.currentDecayRate * trainer.conscienceGamma
						* (neuronsNumber * neuron.getBias() - 1);
			}
			if (distance < winningNeuronOutput) {
				winningNeuronOutput = distance;
				trainer.setCurrentWinningNeuron(neuron);
			}
		}
	}
}
