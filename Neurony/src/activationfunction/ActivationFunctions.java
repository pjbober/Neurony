package activationfunction;

import activationfunction.impl.HardlimActivationFunction;
import activationfunction.impl.LinearActivationFunction;
import activationfunction.impl.SigmoidActivationFunction;

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
