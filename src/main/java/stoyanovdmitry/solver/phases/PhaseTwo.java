package stoyanovdmitry.solver.phases;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

class PhaseTwo extends AbstractPhase {

	PhaseTwo() {
		super();
	}

	public PhaseTwo(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {
			while (!isFaceDone(Face.FRONT)) {
				solvePart.append("D ");
				applyPart();
			}
			checkFace(Face.FRONT);
		}
	}

	private void checkFace(Face face) {

		switch (face) {
			case FRONT:
				if (isFaceDone(Face.FRONT) && isFaceDone(Face.BACK)) {
					solvePart.append("L' D L D L' D D L ");
					solvePart.append("D ");
					solvePart.append("R' D R D R' D D R ");
					applyPart();
				}
				else if (isFaceDone(Face.FRONT) && isFaceDone(Face.LEFT)) {
					solvePart.append("D ");
					solvePart.append("R' D R D R' D D R ");
					applyPart();
				}
				else if (isFaceDone(Face.FRONT) && isFaceDone(Face.RIGHT)) {
					solvePart.append("D ");
					solvePart.append("B' D B D B' D D B ");
					applyPart();
				}
				else {
					solvePart.append("L' D L D L' D D L ");
					applyPart();
				}
		}
	}

	@Override
	public boolean isPhaseDone() {

		boolean done = new PhaseOne(getCube()).isPhaseDone();

		if (!done) return false;

		if (!isFaceDone(Face.FRONT) ||
				!isFaceDone(Face.LEFT) ||
				!isFaceDone(Face.RIGHT) ||
				!isFaceDone(Face.BACK))
			done = false;

		return done;
	}

	private boolean isFaceDone(Face face) {

		String[][] faceCopy = getCube().getFaceCopy(face);
		return faceCopy[1][1].equals(faceCopy[2][1]);
	}
}
