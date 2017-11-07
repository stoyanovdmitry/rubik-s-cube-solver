package stoyanovdmitry.solver.phases;

import stoyanovdmitry.cube.Cube;

public interface Phase {

	void computePhase();

	boolean isPhaseDone();

	Cube getCube();

	void setCube(Cube cube);

	String getPhaseSolve();
}
