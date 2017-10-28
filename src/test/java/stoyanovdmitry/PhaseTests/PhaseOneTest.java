package stoyanovdmitry.PhaseTests;

import org.junit.Assert;
import org.junit.Test;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.solver.Phase;
import stoyanovdmitry.solver.PhaseOne;

import java.util.ArrayList;

public class PhaseOneTest {

	@Test
	public void testFewCubes() {
		ArrayList<Phase> phases = new ArrayList<>();

		for (int i = 0; i < 100000; i++) {
			Cube tempCube = new Cube();
			tempCube.shuffle();
			phases.add(new PhaseOne(tempCube));
		}

		for (Phase phase : phases) {
			phase.computePhase();
		}

		Assert.assertTrue(true);
	}
}
