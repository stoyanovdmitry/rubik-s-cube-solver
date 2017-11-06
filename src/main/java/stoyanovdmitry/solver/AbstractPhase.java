package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;

public abstract class AbstractPhase implements Phase {

	Cube cube;
	private StringBuilder solveBuilder;
	StringBuilder solvePart;

	AbstractPhase() {
		solveBuilder = new StringBuilder();
		solvePart = new StringBuilder();
	}

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
	public final Cube getCube() {
		return cube;
	}

	@Override
	public final void setCube(Cube cube) {
		try {
			this.cube = cube.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		solveBuilder = new StringBuilder();
		solvePart = new StringBuilder();
	}

	final void applyPart() {
		solveBuilder.append(solvePart);
		getCube().rotateByPattern(solvePart.toString());
		solvePart = new StringBuilder();
	}

	public final String getPhaseSolve() {
		return solveBuilder.toString();
	}
}
