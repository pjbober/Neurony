package neurons;

import utils.Random;
import utils.Vectors;
import activationfunction.ActivationFunction;
import activationfunction.ActivationFunctions;
import exceptions.BadVectorDimensionException;

public class Neuron {
	private final int nrOfInputs;
	private final ActivationFunction activationFunction;
	
	private double bias;
	private double[] weights;
	
	public Neuron(int nrOfInputs, ActivationFunctions activationFunction) {
		this.nrOfInputs = nrOfInputs;
		this.activationFunction = activationFunction.getFunction();
		
		bias = Random.getRandom();
		weights = Random.getRandom(nrOfInputs);
	}
	
	public double getValue(double... input) throws BadVectorDimensionException{
		return activationFunction.getValue(Vectors.multiply(weights, input) + bias);
	}
}
