package neurons;

import org.junit.Test;

import exceptions.BadVectorDimensionException;
import exceptions.NeuronTrainException;

import utils.Lists;

import activationfunction.ActivationFunctions;

public class NeuronTrainerTest {
	@Test
	public void testIt() throws NeuronTrainException, BadVectorDimensionException {
		Neuron n = new Neuron(2, ActivationFunctions.Linear);
		NeuronTrainer trainer = new NeuronTrainer(n, 10000);
		
		double []input1 = {0, 0};
		double []input2 = {0, 1};
		double []input3 = {1, 0};
		double []input4 = {1, 1};
		double []answers = {0, 0, 0, 1};
		
		System.out.println(n.getValue(1,0));
		trainer.train(Lists.listOf(input1, input2, input3,input4), answers);
		System.out.println(n.getValue(1, 0));
	}
}
