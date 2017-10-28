package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;

public abstract class AbstractPhase implements Phase {

	private Cube cube;

	AbstractPhase(Cube cube) {
		try {
			this.cube = cube.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public Cube getCube() {
		return cube;
	}
}
