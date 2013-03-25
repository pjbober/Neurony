import utils.ArraysUtil;
import neurons.FeedForwardNet;
import neurons.FeedForwardNetCreator;
import activationfunction.ActivationFunctions;

public class Main {
	public static void main(String[] args) throws Exception {

		FeedForwardNet net = FeedForwardNetCreator.builder(20)
				.withSameNumberOfNeuronsInLayers(20)
				.withSameActivationFunction(ActivationFunctions.Linear)
				.withNrOfInputs(2).withNrOfOutputs(1).build();
		
		System.out.println(ArraysUtil.toString(net.getResponse(1,2)));
		
	}
}
