package stoyanovdmitry;

import org.junit.*;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;
import stoyanovdmitry.solver.*;

import java.util.ArrayList;
import java.util.List;

public class SolverTests {

	private static final int COUNT_OF_CUBES = 10000;

	private static List<Phase> phaseOneList;
	private static List<Phase> phaseTwoList;
	private static List<Phase> phaseThreeList;
	private static List<Phase> phaseFourList;
	private static List<Phase> phaseFiveList;
	private static List<Phase> phaseSixList;
	private static List<Phase> phaseSevenList;
	private static List<Phase> phaseEightList;

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


		phaseFiveList = new ArrayList<>();
		for (Phase phase : phaseFourList) {
			phaseFiveList.add(new PhaseFive(phase.getCube()));
		}
		for (Phase phase : phaseFiveList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
		}


		phaseSixList = new ArrayList<>();
		for (Phase phase : phaseFiveList) {
			phaseSixList.add(new PhaseSix(phase.getCube()));
		}
		for (Phase phase : phaseSixList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
		}


		phaseSevenList = new ArrayList<>();
		for (Phase phase : phaseSixList) {
			phaseSevenList.add(new PhaseSeven(phase.getCube()));
		}
		for (Phase phase : phaseSevenList) {
			phase.computePhase();
//			System.out.println(phase.getPhaseSolve());
//			System.out.println(phase.getCube());
		}


		phaseEightList = new ArrayList<>();
		for (Phase phase : phaseSevenList) {
			phaseEightList.add(new PhaseEight(phase.getCube()));
		}
		for (Phase phase : phaseEightList) {
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

	@Test
	public void testPhaseFive() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseFiveList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}

	@Test
	public void testPhaseSix() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseSixList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}

	@Test
	public void testPhaseSeven() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseSevenList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}

	@Test
	public void testPhaseEight() {

		List<Boolean> isAllPhasesDone = new ArrayList<>();

		for (Phase phase : phaseEightList) {
			isAllPhasesDone.add(phase.isPhaseDone());
		}

		Assert.assertFalse(isAllPhasesDone.contains(false));
	}

	@Test
	public void isPhaseFourYellow() {

		testPhaseFour();

		for (Phase phase : phaseFourList) {

			int counter = 0;

			String[][] faceUp = phase.getCube().getFaceCopy(Face.UP);

			if (faceUp[0][1].equals("Y")) counter++;
			if (faceUp[1][0].equals("Y")) counter++;
			if (faceUp[1][2].equals("Y")) counter++;
			if (faceUp[2][1].equals("Y")) counter++;

			Assert.assertTrue(counter != 1 && counter != 3);
		}
	}
}
