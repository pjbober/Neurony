import utils.ArraysUtil;
import neurons.NeuronLayer;
import activationfunction.ActivationFunctions;
import exceptions.BadVectorDimensionException;

public class Main {
	public static void main(String[] args) throws BadVectorDimensionException {

		NeuronLayer layer = new NeuronLayer(4, 1, ActivationFunctions.Linear);

		System.out.println(ArraysUtil.toString(layer.getValues(1, 1, 1, 1)));
	}
}
