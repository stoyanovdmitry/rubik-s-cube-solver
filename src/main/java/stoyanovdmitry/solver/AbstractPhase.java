package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;

public abstract class AbstractPhase implements Phase {

	Cube cube;
	private StringBuilder solveBuilder;
	StringBuilder solvePart;

	AbstractPhase(Cube cube) {
		try {
			this.cube = cube.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		solveBuilder = new StringBuilder();
		solvePart = new StringBuilder();
	}

	@Override
	public Cube getCube() {
		return cube;
	}


	void applyPart() {
		solveBuilder.append(solvePart);
		getCube().rotateByPattern(solvePart.toString());
		solvePart = new StringBuilder();
	}
}
