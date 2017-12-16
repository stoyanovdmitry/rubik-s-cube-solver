package stoyanovdmitry;

import org.junit.Assert;
import org.junit.Test;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.solver.Solver;
import stoyanovdmitry.solver.phases.PhaseNum;

import java.util.List;
import java.util.Arrays;

public class PersonalTests {

	@Test
	public void testSolverClass() {

		Cube cube1 = new Cube();
		cube1.shuffle();

		Cube cube2 = null;
		try {
			cube2 = cube1.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Solver solver = new Solver(cube1);

		String solve = solver.getSolve();

		cube2.rotateByPattern(solve);

		Assert.assertEquals(solver.getCube(), cube2);

//		System.out.println(solve.split(" ").length);
	}

	@Test
	synchronized public void testThreads() {

		Thread thread = new Thread(() -> {
			doSomething();
		});
		try {

			System.out.println(thread.isAlive());

			thread.start();
			thread.wait();
			System.out.println(thread.isAlive());

//			thread.wait();
			System.out.println(thread.isAlive());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSolveString() {

		Cube cube = new Cube();
		cube.shuffle();

		Solver solver = new Solver(cube);

		String solve = solver.getSolve();
		List<PhaseNum> phaseNumList = solver.getSolveStepPhases();
		List<String> solveStep = Arrays.asList(solve.split(" "));

		System.out.println(phaseNumList.size());
		System.out.println(solveStep.size());

		for (int i = 0; i < phaseNumList.size(); i++) {
			System.out.printf("%s %s \n", solveStep.get(i), phaseNumList.get(i));
		}
	}

	synchronized private void doSomething() {
		for (int i = 0; i < 1000; i++) {
			int k = i;
		}
	}
}
