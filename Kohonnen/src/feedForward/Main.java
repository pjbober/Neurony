package feedForward;
import utils.ArraysUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import feedForward.activationfunction.ActivationFunctions;
import feedForward.feedforward.FeedForwardNet;
import feedForward.feedforward.FeedForwardNetCreator;

public class Main {
	public static void main(String[] args) throws Exception {

		FeedForwardNet net = FeedForwardNetCreator.builder(2)
				.withSameNumberOfNeuronsInLayers(2)
				.withSameActivationFunction(ActivationFunctions.Linear)
				.withNrOfInputs(2).withNrOfOutputs(1).build();
		
		System.out.println(ArraysUtil.toString(net.getResponse(1,2)));
		
		//saving data to xml
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		
		System.out.println(xstream.toXML(net));
	}
}
