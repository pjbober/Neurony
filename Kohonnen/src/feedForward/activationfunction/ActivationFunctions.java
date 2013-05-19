package feedForward.activationfunction;

import feedForward.activationfunction.impl.HardlimActivationFunction;
import feedForward.activationfunction.impl.LinearActivationFunction;
import feedForward.activationfunction.impl.SigmoidActivationFunction;

public enum ActivationFunctions {
	Hardlim(HardlimActivationFunction.class),
	Linear(LinearActivationFunction.class),
	Sigmoid(SigmoidActivationFunction.class);
	
	private final Class<? extends ActivationFunction> functionClass;
	
	private ActivationFunctions(Class<? extends ActivationFunction> clazz){
		this.functionClass = clazz;
	}
	
	public ActivationFunction getFunction() {
		try {
			return functionClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
