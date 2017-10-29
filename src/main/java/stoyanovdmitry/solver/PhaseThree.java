package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

import java.util.List;
import java.util.Arrays;

public class PhaseThree extends AbstractPhase {

	private List<Face> facesForCheck = Arrays.asList(Face.FRONT, Face.RIGHT, Face.BACK, Face.LEFT);

	public PhaseThree(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {

			Corner corner = findCorner();
			if (corner == null) {
				replaceDownCorner();
				continue;
			}
			replaceCorner(corner);
		}
	}

	private void replaceCorner(Corner corner) { //todo need to realize algorithm of replacing of upper right corner

	}

	private void replaceDownCorner() {

		Face face = findDownCorner();

		if (face == null) return;

		switch (face) {
			case FRONT:
				solvePart.append("R U' R' ");
				applyPart();
				break;
			case RIGHT:
				solvePart.append("B U' B' ");
				applyPart();
				break;
			case BACK:
				solvePart.append("L U' L' ");
				applyPart();
				break;
			case LEFT:
				solvePart.append("F U' F' ");
				applyPart();
				break;
		}
	}

	private Face findDownCorner() {

		for (Face face : facesForCheck) {
			boolean done = isCornerDone(face);
			if (!done) return face;
		}
		return null;
	}

	private Corner findCorner() {

		for (Face face : facesForCheck) {
			Corner corner = new Corner(face);
			if (corner.hasWhite()) return corner;
		}
		return null;
	}

	@Override
	public boolean isPhaseDone() {
		return false;
	}

	private boolean isCornerDone(Face face) {

		String[][] downFace = cube.getFaceCopy(Face.DOWN);
		String[][] frontFace = cube.getFaceCopy(face);
		String[][] rightFace;

		switch (face) {
			case FRONT:
				rightFace = cube.getFaceCopy(Face.RIGHT);
				if (downFace[0][2].equals("W")
						&& frontFace[2][2].equals(frontFace[1][1])
						&& rightFace[2][0].equals(rightFace[1][1]))
					return true;
				break;
			case RIGHT:
				rightFace = cube.getFaceCopy(Face.BACK);
				if (downFace[0][2].equals("W")
						&& frontFace[2][2].equals(frontFace[1][1])
						&& rightFace[2][0].equals(rightFace[1][1]))
					return true;
				break;
			case BACK:
				rightFace = cube.getFaceCopy(Face.LEFT);
				if (downFace[0][2].equals("W")
						&& frontFace[2][2].equals(frontFace[1][1])
						&& rightFace[2][0].equals(rightFace[1][1]))
					return true;
				break;
			case LEFT:
				rightFace = cube.getFaceCopy(Face.FRONT);
				if (downFace[0][2].equals("W")
						&& frontFace[2][2].equals(frontFace[1][1])
						&& rightFace[2][0].equals(rightFace[1][1]))
					return true;
				break;
		}

		return false;
	}

	private class Corner {

		private String up;
		private String front;
		private String right;
		private Face face;

		Corner(Face face) {

			String[][] upFace = cube.getFaceCopy(Face.UP);
			String[][] frontFace = cube.getFaceCopy(face);
			String[][] rightFace;
			front = frontFace[0][2];

			switch (face) {
				case FRONT:
					rightFace = cube.getFaceCopy(Face.RIGHT);
					up = upFace[2][2];
					right = rightFace[0][0];
					break;
				case RIGHT:
					rightFace = cube.getFaceCopy(Face.BACK);
					up = upFace[0][2];
					right = rightFace[0][0];
					break;
				case BACK:
					rightFace = cube.getFaceCopy(Face.LEFT);
					up = upFace[0][0];
					right = rightFace[0][0];
					break;
				case LEFT:
					rightFace = cube.getFaceCopy(Face.FRONT);
					up = upFace[2][0];
					right = rightFace[0][0];
					break;
			}
		}

		String getUp() {
			return up;
		}

		String getFront() {
			return front;
		}

		String getRight() {
			return right;
		}

		Face getFace() {
			return face;
		}

		boolean hasWhite() {
			return up.equals("W") || front.equals("W") || right.equals("W");
		}
	}
}
