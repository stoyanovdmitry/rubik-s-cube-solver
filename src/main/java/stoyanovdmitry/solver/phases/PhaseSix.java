package stoyanovdmitry.solver.phases;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

class PhaseSix extends AbstractPhase {

	PhaseSix() {
		super();
	}

	PhaseSix(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {
			while (!isFaceDone(Face.FRONT)) {
				solvePart.append("U ");
				applyPart();
			}
			checkFace(Face.FRONT);
		}
	}

	private void checkFace(Face face) {

		switch (face) {
			case FRONT:
				if (isFaceDone(Face.FRONT) && isFaceDone(Face.BACK)) {
					solvePart.append("R U R' U R U U R' ");
					solvePart.append("U ");
					solvePart.append("L U L' U L U U L' ");
				}
				else if (isFaceDone(Face.FRONT) && isFaceDone(Face.LEFT)) {
					solvePart.append("U ");
					solvePart.append("B U B' U B U U B' ");
				}
				else if (isFaceDone(Face.FRONT) && isFaceDone(Face.RIGHT)) {
					solvePart.append("U ");
					solvePart.append("L U L' U L U U L' ");
				}
				else {
					solvePart.append("R U R' U R U U R' ");
				}
				applyPart();
		}
	}

	@Override
	public boolean isPhaseDone() {

		boolean done = new PhaseFive(getCube()).isPhaseDone();

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
		return faceCopy[1][1].equals(faceCopy[0][1]);
	}
}
