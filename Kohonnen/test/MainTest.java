import kohonen.KohonenNetwork;

import org.junit.Ignore;
import org.junit.Test;

import serializator.KohonenSerializator;
import trainers.SelfOrganizingMapTrainer;

public class MainTest {
	@Test
	@Ignore
	public void test() {
		KohonenNetwork network = new KohonenNetwork(2, 10, 200);
		KohonenSerializator.saveToFile("C:/Users/Admin/Desktop/net_before.txt",
				network);
		SelfOrganizingMapTrainer trainer = new SelfOrganizingMapTrainer(network);
		trainer.setLearningRateDynamic(true);
		trainer.setShufflingUsed(true);
		trainer.setConscienceUsed(true);
		trainer.setEpochs(25000);
		trainer.setConscienceBeta(0.0001);
		trainer.setConscienceGamma(0.0085);
		trainer.setNormalizedInputOutputFile("C:/Users/Admin/Desktop/norm_input.txt");
		trainer.train("C:/Users/Admin/Desktop/input.txt");
		KohonenSerializator.saveToFile("C:/Users/Admin/Desktop/net_after.txt",
				network);
	}

	@Test
	public void test2() {
		KohonenNetwork network = new KohonenNetwork(3, 11, 20);
		KohonenSerializator.saveToFileWithKohonnenCoords(
				"C:/Users/Admin/Desktop/net_before2.txt", network);
		SelfOrganizingMapTrainer trainer = new SelfOrganizingMapTrainer(network);
		trainer.setLearningRateDynamic(true);
		trainer.setShufflingUsed(true);
		trainer.setConscienceUsed(false);
		trainer.setEpochs(25000);
		trainer.setConscienceBeta(0.9);
		trainer.setConscienceGamma(0.25);
		trainer.setNormalizedInputOutputFile("C:/Users/Admin/Desktop/norm_input2.txt");
		trainer.train("C:/Users/Admin/Desktop/input2.txt");
		KohonenSerializator.saveToFileWithKohonnenCoords(
				"C:/Users/Admin/Desktop/net_after2.txt", network);
	}
}
