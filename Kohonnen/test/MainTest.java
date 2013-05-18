import kohonnen.KohonnenNetwork;

import org.junit.Ignore;
import org.junit.Test;

import treiners.SelfOrganizingMapTreiner;

public class MainTest {
	@Test
	@Ignore
	public void test() {
		KohonnenNetwork network = new KohonnenNetwork(2, 10, 200);
		network.saveToFile("C:/Users/Admin/Desktop/net_before.txt");
		SelfOrganizingMapTreiner trainer = new SelfOrganizingMapTreiner(network);
		trainer.setLearningRateDynamic(true);
		trainer.setShufflingUsed(true);
		trainer.setConscienceUsed(true);
		trainer.setEpochs(25000);
		trainer.setConscienceBeta(0.0001);
		trainer.setConscienceGamma(0.0085);
		trainer.setNormalizedInputOutputFile("C:/Users/Admin/Desktop/norm_input.txt");
		trainer.train("C:/Users/Admin/Desktop/input.txt");
		network.saveToFile("C:/Users/Admin/Desktop/net_after.txt");
	}

	@Test
	public void test2() {
		KohonnenNetwork network = new KohonnenNetwork(3, 11, 20);
		network.saveToFileWithKohonnenCoords("C:/Users/Admin/Desktop/net_before2.txt");
		SelfOrganizingMapTreiner trainer = new SelfOrganizingMapTreiner(network);
		trainer.setLearningRateDynamic(true);
		trainer.setShufflingUsed(true);
		trainer.setConscienceUsed(false);
		trainer.setEpochs(25000);
		trainer.setConscienceBeta(0.9);
		trainer.setConscienceGamma(0.25);
		trainer.setNormalizedInputOutputFile("C:/Users/Admin/Desktop/norm_input2.txt");
		trainer.train("C:/Users/Admin/Desktop/input2.txt");
		network.saveToFileWithKohonnenCoords("C:/Users/Admin/Desktop/net_after2.txt");
	}
}
