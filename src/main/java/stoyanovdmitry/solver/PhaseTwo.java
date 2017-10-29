package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

public class PhaseTwo extends AbstractPhase {

	PhaseTwo(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

	}

	@Override
	public boolean isPhaseDone() {

		boolean done = new PhaseOne(getCube()).isPhaseDone();

		if (!done) return false;

		String[][] frontFace = getCube().getFaceCopy(Face.FRONT);
		String[][] leftFace = getCube().getFaceCopy(Face.LEFT);
		String[][] rightFace = getCube().getFaceCopy(Face.RIGHT);
		String[][] backFace = getCube().getFaceCopy(Face.BACK);

		if (!frontFace[1][1].equals(frontFace[2][1])
				|| !leftFace[1][1].equals(frontFace[2][1])
				|| !rightFace[1][1].equals(rightFace[2][1])
				|| !backFace[1][1].equals(backFace[2][1]))
			done = false;

		return done;
	}
}
