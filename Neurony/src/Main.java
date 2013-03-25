import activationfunction.ActivationFunctions;
import neurons.FeedForwardNet;
import neurons.FeedForwardNetCreator;
import exceptions.BadVectorDimensionException;

public class Main {
	public static void main(String[] args) throws BadVectorDimensionException {

		FeedForwardNet net = FeedForwardNetCreator.builder(3).withSameNumberOfNeuronsInLayers(200).withSameActivationFunction(ActivationFunctions.Linear).withNrOfInputs(2).withNrOfOutputs(1).build();
	}
}
