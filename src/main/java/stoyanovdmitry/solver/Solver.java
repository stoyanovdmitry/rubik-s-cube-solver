package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;

import java.util.ArrayList;
import java.util.List;

public class Solver {

	private String solve;
	private List<Phase> phases;

	private Cube cube;

	public Solver(Cube cube) {
		this.cube = cube;

		phases = new ArrayList<>();

		phases.add(new PhaseOne());
		phases.add(new PhaseTwo());
		phases.add(new PhaseThree());
		phases.add(new PhaseFour());
		phases.add(new PhaseFive());
		phases.add(new PhaseSix());
		phases.add(new PhaseSeven());
		phases.add(new PhaseEight());
	}

	public String getSolve() {
		if (solve == null) solveCube();
		return solve;
	}

	public Cube getCube() {
		return cube;
	}

	private void solveCube() {

		solve = "";

		for (Phase phase : phases) {

			phase.setCube(cube);
			phase.computePhase();
			solve += phase.getPhaseSolve();
			this.cube = phase.getCube();
		}
	}
}
