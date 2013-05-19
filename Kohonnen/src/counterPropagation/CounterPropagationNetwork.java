package counterPropagation;

import feedForward.activationfunction.ActivationFunctions;
import feedForward.feedforward.FeedForwardNet;
import feedForward.feedforward.FeedForwardNetCreator;
import kohonen.kohonen.KohonenNetwork;

public class CounterPropagationNetwork {
	private final KohonenNetwork kohonenLayer;
	private final FeedForwardNet backPropagationLayer;

	public CounterPropagationNetwork(int nrOfInputs, int neuronsPerRow,
			int nrOfOutputs, int nrOfNeuronsInBackPropagation,
			double kohonenAreaWidth) {
		kohonenLayer = new KohonenNetwork(nrOfInputs, neuronsPerRow,
				kohonenAreaWidth);
		backPropagationLayer = FeedForwardNetCreator.builder(1)
				.withNrOfInputs(kohonenLayer.getNeuronsNumber())
				.withNrOfOutputs(nrOfOutputs)
				.withSameNumberOfNeuronsInLayers(nrOfNeuronsInBackPropagation)
				.withSameActivationFunction(ActivationFunctions.Linear).build();
	}
	
}
