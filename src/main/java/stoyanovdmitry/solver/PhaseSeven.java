package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhaseSeven extends AbstractPhase {

	private List<Face> facesForCheck = Arrays.asList(Face.RIGHT, Face.BACK, Face.LEFT, Face.FRONT);

	PhaseSeven() {
		super();
	}

	public PhaseSeven(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {

			Corner corner = getCorrectCorner();

			if (corner == null) {
				replaceCorners(new Corner(Face.RIGHT));
				continue;
			}

			while (!isPhaseDone())
				replaceCorners(corner);
		}
	}

	private void replaceCorners(Corner corner) {

		Face face = corner.getFace();

		solvePart.append("U' R' U L U' R U L' ");   //if face.equals(Face.RIGHT);

		switch (face) {
			case BACK:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("R", "B")
						.replaceAll("L", "F")
				);
				break;
			case LEFT:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("R", "l")
						.replaceAll("L", "R")
						.replaceAll("l", "L")
				);
				break;
			case FRONT:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("R", "F")
						.replaceAll("L", "B")
				);
				break;
		}

		applyPart();
	}

	private Corner getCorrectCorner() {

		for (Face face : facesForCheck) {
			Corner corner = new Corner(face);
			if (corner.isCorrect()) return corner;
		}
		return null;
	}

	@Override
	public boolean isPhaseDone() {

		boolean done = new PhaseSix(cube).isPhaseDone();

		for (Face face : facesForCheck) {
			Corner corner = new Corner(face);
			if (!corner.isCorrect()) return false;
		}

		return done;
	}

	private class Corner {

		private List<String> colors;
		private Face face;

		Corner(Face face) {

			colors = new ArrayList<>();

			this.face = face;

			String[][] upFace = cube.getFaceCopy(Face.UP);
			String[][] frontFace = cube.getFaceCopy(face);
			String[][] rightFace;

			colors.add(frontFace[0][2]);

			switch (face) {
				case FRONT:
					rightFace = cube.getFaceCopy(Face.RIGHT);
					colors.add(upFace[2][2]);
					colors.add(rightFace[0][0]);
					break;
				case RIGHT:
					rightFace = cube.getFaceCopy(Face.BACK);
					colors.add(upFace[0][2]);
					colors.add(rightFace[0][0]);
					break;
				case BACK:
					rightFace = cube.getFaceCopy(Face.LEFT);
					colors.add(upFace[0][0]);
					colors.add(rightFace[0][0]);
					break;
				case LEFT:
					rightFace = cube.getFaceCopy(Face.FRONT);
					colors.add(upFace[2][0]);
					colors.add(rightFace[0][0]);
					break;
			}
		}

		Face getFace() {
			return face;
		}

		boolean isCorrect() {

			switch (face) {
				case FRONT:
					return colors.contains("R") && colors.contains("B");
				case RIGHT:
					return colors.contains("B") && colors.contains("O");
				case BACK:
					return colors.contains("O") && colors.contains("G");
				case LEFT:
					return colors.contains("G") && colors.contains("R");
			}

			return false;
		}
	}
}
