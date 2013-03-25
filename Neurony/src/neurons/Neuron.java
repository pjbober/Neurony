package neurons;

import utils.Random;
import utils.Vectors;
import activationfunction.ActivationFunction;
import activationfunction.ActivationFunctions;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import exceptions.BadVectorDimensionException;

public class Neuron {
	private final int nrOfInputs;
	private final ActivationFunctions activationFunction;
	@XStreamOmitField
	private ActivationFunction function;
	
	private double bias;
	private double[] weights;
	
	public Neuron(int nrOfInputs, ActivationFunctions activationFunction) {
		this.nrOfInputs = nrOfInputs;
		this.activationFunction = activationFunction;
		this.function = activationFunction.getFunction();
		
		bias = Random.getRandom();
		weights = Random.getRandom(nrOfInputs);
	}
	
	public double getValue(double... input) throws BadVectorDimensionException{
		return function.getValue(Vectors.multiply(weights, input) + bias);
	}
	
	private Object readResolve(){
		function = activationFunction.getFunction();
		return this;		
	}
}
