package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;

public interface Phase {

	void computePhase();

	boolean isPhaseDone();

	Cube getCube();
}
