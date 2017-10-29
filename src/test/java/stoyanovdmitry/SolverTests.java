package stoyanovdmitry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.solver.Phase;
import stoyanovdmitry.solver.PhaseOne;
import stoyanovdmitry.solver.PhaseTwo;

import java.util.ArrayList;
import java.util.List;

public class SolverTests {

	private List<Phase> phaseOneList;
	private List<Phase> phaseTwoList;

	@Before
	public void beforeTest() {

		phaseOneList= new ArrayList<>();

		for (int i = 0; i < 1000; i++) {
			Cube tempCube = new Cube();
			tempCube.shuffle();
			phaseOneList.add(new PhaseOne(tempCube));
		}

		for (Phase phase : phaseOneList) {
			phase.computePhase();
		}

		phaseTwoList = new ArrayList<>();

		for (Phase phase : phaseOneList) {
			phaseTwoList.add(new PhaseTwo(phase.getCube()));
		}

		for (Phase phase : phaseTwoList) {
			phase.computePhase();
		}
	}

	@Test
	public void testPhaseOne() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseOneList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}

	@Test
	public void testPhaseTwo() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseTwoList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}
}
