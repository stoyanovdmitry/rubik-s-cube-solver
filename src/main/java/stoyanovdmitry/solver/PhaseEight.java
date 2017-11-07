package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

import java.util.Arrays;
import java.util.List;

public class PhaseEight extends AbstractPhase {

	private List<Face> facesForCheck = Arrays.asList(Face.FRONT, Face.RIGHT, Face.BACK, Face.LEFT);
	private Face workingFace;

	PhaseEight() {
		super();
	}

	public PhaseEight(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {
			Corner corner = findWrongCorner();

			if (workingFace == null)
				workingFace = corner.getFace();

			replace(corner);
		}
	}

	private void replace(Corner corner) {

		if (!corner.getFace().equals(workingFace))
			setUpCornerInRightPlace(corner);

		Corner workingCorner = new Corner(workingFace);

		while (!workingCorner.isCorrect()) {
			solvePart.append("R' D' R D ");

			switch (workingFace) {
				case RIGHT:
					solvePart = new StringBuilder(solvePart.toString()
							.replaceAll("R", "B"));
				case BACK:
					solvePart = new StringBuilder(solvePart.toString()
							.replaceAll("R", "L"));
				case LEFT:
					solvePart = new StringBuilder(solvePart.toString()
							.replaceAll("R", "F"));
			}

			applyPart();

			workingCorner = new Corner(workingFace);
		}

		if (isUpDone()) replaceRows();
	}

	private void replaceRows() {

		String[][] frontFace = cube.getFaceCopy(Face.FRONT);
		String centralSticker = frontFace[1][1];

		while (!frontFace[0][1].equals(centralSticker)) {
			solvePart.append("U ");
			applyPart();
			frontFace = cube.getFaceCopy(Face.FRONT);
		}

		while (!frontFace[2][1].equals(centralSticker)) {
			solvePart.append("D' ");
			applyPart();
			frontFace = cube.getFaceCopy(Face.FRONT);
		}
	}

	private boolean isUpDone() {

		for (String[] row : cube.getFaceCopy(Face.UP)) {
			for (String sticker : row) {
				if (!sticker.equals("Y"))
					return false;
			}
		}
		return true;
	}

	private void setUpCornerInRightPlace(Corner corner) {

		while (!corner.getFace().equals(workingFace)) {
			solvePart.append("U' ");
			applyPart();

			corner = findWorkingCorner(corner);
		}

	}

	private Corner findWorkingCorner(Corner corner) {

		for (Face face : facesForCheck) {
			Corner wantedCorner = new Corner(face);
			if (wantedCorner.equals(corner)) return wantedCorner;
		}

		return null;
	}

	private Corner findWrongCorner() {

		for (Face face : facesForCheck) {
			Corner corner = new Corner(face);
			if (!corner.isCorrect()) return corner;
		}

		return null;
	}

	@Override
	public boolean isPhaseDone() {

		for (Face face : Face.values()) {

			String[][] faceCopy = cube.getFaceCopy(face);
			String centralSticker = faceCopy[1][1];

			for (String[] row : faceCopy) {
				for (String sticker : row) {
					if (!sticker.equals(centralSticker))
						return false;
				}
			}
		}

		return true;
	}

	private class Corner {

		private String up;
		private String front;
		private String right;
		private Face face;

		Corner(Face face) {

			this.face = face;

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

		boolean isCorrect() {
			return up.equals("Y");
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Corner corner = (Corner) o;

			if (!getUp().equals(corner.getUp())) return false;
			if (!getFront().equals(corner.getFront())) return false;
			return getRight().equals(corner.getRight());
		}

		@Override
		public int hashCode() {
			int result = getUp().hashCode();
			result = 31 * result + getFront().hashCode();
			result = 31 * result + getRight().hashCode();
			return result;
		}
	}
}
