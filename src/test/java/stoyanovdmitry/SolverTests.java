package stoyanovdmitry;

import org.junit.*;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.solver.*;

import java.util.ArrayList;
import java.util.List;

public class SolverTests {

	private static final int COUNT_OF_CUBES = 100;

	private static List<Phase> phaseOneList;
	private static List<Phase> phaseTwoList;
	private static List<Phase> phaseThreeList;
	private static List<Phase> phaseFourList;

	@BeforeClass
	public static void setUp() {

		phaseOneList = new ArrayList<>();
		for (int i = 0; i < COUNT_OF_CUBES; i++) {
			Cube tempCube = new Cube();
			tempCube.shuffle();
			phaseOneList.add(new PhaseOne(tempCube));
		}
		for (Phase phase : phaseOneList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
		}


		phaseTwoList = new ArrayList<>();
		for (Phase phase : phaseOneList) {
			phaseTwoList.add(new PhaseTwo(phase.getCube()));
		}
		for (Phase phase : phaseTwoList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
		}


		phaseThreeList = new ArrayList<>();
		for (Phase phase : phaseTwoList) {
			phaseThreeList.add(new PhaseThree(phase.getCube()));
		}
		for (Phase phase : phaseThreeList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
		}


		phaseFourList = new ArrayList<>();
		for (Phase phase : phaseThreeList) {
			phaseFourList.add(new PhaseFour(phase.getCube()));
		}
		for (Phase phase : phaseFourList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
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

	//	@Ignore
	@Test
	public void testPhaseThree() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseThreeList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}

	@Test
	public void testPhaseFour() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseFourList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}
}
