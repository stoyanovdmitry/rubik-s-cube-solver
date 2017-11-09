package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.solver.phases.*;

import java.util.List;

public class Solver {

	private String solve;
	private List<Phase> phases;

	private Cube cube;

	public Solver(Cube cube) {
		this.cube = cube;

		phases = new PhaseFactory().emptyPhases();
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
