package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;

import java.util.List;

public class Solver {

	private String solve;
	private List<Phase> phases;

	private Cube cube;

	public Solver(Cube cube) {
		this.cube = cube;
	}

	public String getSolve() {

		if(solve == null) solveCube();
		return solve;
	}

	private void solveCube() {

		//todo realize method
	}
}
