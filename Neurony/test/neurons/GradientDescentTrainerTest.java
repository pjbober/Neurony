package neurons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.ArraysUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import activationfunction.ActivationFunctions;
import exceptions.BadVectorDimensionException;

public class GradientDescentTrainerTest {

	 @Test
	public void trainTest() throws BadVectorDimensionException {
		FeedForwardNet net = FeedForwardNetCreator.builder(1)
				.withSameNumberOfNeuronsInLayers(2)
				.withSameActivationFunction(ActivationFunctions.Sigmoid)
				.withNrOfInputs(2).withNrOfOutputs(1).build();

		GradientDescentTrainer gradientDescentTrainer = new GradientDescentTrainer(
				net);
		gradientDescentTrainer.setEpochs(100000);
		gradientDescentTrainer.setLearningRate(0.01);
		gradientDescentTrainer.setMomentum(0.5);

		List<double[]> inputs = new ArrayList<>();
		List<double[]> outputs = new ArrayList<>();
		inputs.add(new double[] { 1, 1 });
		inputs.add(new double[] { 1, 0 });
		inputs.add(new double[] { 0, 1 });
		inputs.add(new double[] { 0, 0 });
		outputs.add(new double[] { 1 });
		outputs.add(new double[] { 0 });
		outputs.add(new double[] { 0 });
		outputs.add(new double[] { 1 });

		System.out.println(net.getResponse(inputs.get(0))[0]);
		System.out.println(net.getResponse(inputs.get(1))[0]);
		System.out.println(net.getResponse(inputs.get(2))[0]);
		System.out.println(net.getResponse(inputs.get(3))[0]);
		gradientDescentTrainer.train(inputs, outputs);
		System.out.println();
		System.out.println(net.getResponse(inputs.get(0))[0]);
		System.out.println(net.getResponse(inputs.get(1))[0]);
		System.out.println(net.getResponse(inputs.get(2))[0]);
		System.out.println(net.getResponse(inputs.get(3))[0]);
	}

	List<double[]> inputs = new ArrayList<>();
	List<double[]> outputs = new ArrayList<>();

	@Before
	public void prepare() {
		inputs.add(new double[] { 1, 1 });
		inputs.add(new double[] { 1, 0 });
		inputs.add(new double[] { 0, 1 });
		inputs.add(new double[] { 0, 0 });
		outputs.add(new double[] { 1 });
		outputs.add(new double[] { 0 });
		outputs.add(new double[] { 0 });
		outputs.add(new double[] { 0 });
	}

//	@Test
	public void ANDTest() throws BadVectorDimensionException {
		XStream x = new XStream(new DomDriver());
		x.autodetectAnnotations(true);
		FeedForwardNet net = (FeedForwardNet) x
				.fromXML(new File("test/Net.xml"));

		System.out.println(ArraysUtil.toString(net.getResponse(1, 1)));
		
		GradientDescentTrainer gradientDescentTrainer = new GradientDescentTrainer(
				net);
		gradientDescentTrainer.setEpochs(10000);
		gradientDescentTrainer.train(inputs, outputs);
		
		System.out.println(ArraysUtil.toString(net.getResponse(1,1)));
	}
}
