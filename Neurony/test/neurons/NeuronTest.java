package neurons;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import exceptions.BadVectorDimensionException;

import activationfunction.ActivationFunctions;

public class NeuronTest {
	@Test
	public void testConstructor() {
		Neuron neuron = new Neuron(2, ActivationFunctions.Linear);

		assertEquals(2, neuron.getWeights().length);
	}

	@Test
	public void testValue() throws BadVectorDimensionException {
		Neuron n = new Neuron(2, ActivationFunctions.Linear);
		n.multiplyWeightBy(0); // zeroes weights
		n.modifyWeightsBy(2, 3); // sets weights to 2 and 3
		n.multiplyBiasBy(0);
		
		assertEquals(5, n.getValue(1, 1), 0.1);
	}
}
