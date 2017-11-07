package stoyanovdmitry.solver.phases;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

import java.util.Arrays;
import java.util.List;

class PhaseFour extends AbstractPhase {

	private List<Face> facesForCheck = Arrays.asList(Face.FRONT, Face.RIGHT, Face.BACK, Face.LEFT);

	PhaseFour() {
		super();
	}

	PhaseFour(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {

			Edge edge = findEdge();
			if (edge == null) {
				replaceWrongEdge();
				continue;
			}
			replaceEdge(edge);
		}
	}

	private void replaceEdge(Edge edge) {

		if (edge.hasYellow()) return;

		Face face = setUpEdgeInRightPosition(edge);

		String toRightEdge = "U R U' R' U' F' U F ";
		String toLeftEdge = "U' L' U L U F U' F' ";

		String edgeUp = edge.getUp();

		switch (face) {
			case FRONT:
				if (edgeUp.equals("B"))
					solvePart.append(toRightEdge);
				else if (edgeUp.equals("G"))
					solvePart.append(toLeftEdge);
				break;
			case RIGHT:
				if (edgeUp.equals("O"))
					solvePart.append(toRightEdge
							.replaceAll("R", "B")
							.replaceAll("F", "R")
					);
				else if (edgeUp.equals("R"))
					solvePart.append(toLeftEdge
							.replaceAll("F", "R")
							.replaceAll("L", "F")
					);
				break;
			case BACK:
				if (edgeUp.equals("G"))
					solvePart.append(toRightEdge
							.replaceAll("R", "L")
							.replaceAll("F", "B")
					);
				else if (edgeUp.equals("B"))
					solvePart.append(toLeftEdge
							.replaceAll("F", "B")
							.replaceAll("L", "R")
					);
				break;
			case LEFT:
				if (edgeUp.equals("R"))
					solvePart.append(toRightEdge
							.replaceAll("F", "L")
							.replaceAll("R", "F")
					);
				else if (edgeUp.equals("O"))
					solvePart.append(toLeftEdge
							.replaceAll("L", "B")
							.replaceAll("F", "L")
					);
				break;
		}

		applyPart();
	}

	private Face setUpEdgeInRightPosition(Edge edge) {

		Face face = edge.getFace();
		String front = edge.getFront();

		switch (face) {
			case FRONT:
				switch (front) {
					case "B":
						solvePart.append("U' ");
						applyPart();
						return Face.RIGHT;
					case "O":
						solvePart.append("U' U' ");
						applyPart();
						return Face.BACK;
					case "G":
						solvePart.append("U ");
						applyPart();
						return Face.LEFT;
				}
				break;
			case RIGHT:
				switch (front) {
					case "O":
						solvePart.append("U' ");
						applyPart();
						return Face.BACK;
					case "G":
						solvePart.append("U' U' ");
						applyPart();
						return Face.LEFT;
					case "R":
						solvePart.append("U ");
						applyPart();
						return Face.FRONT;
				}
				break;
			case BACK:
				switch (front) {
					case "G":
						solvePart.append("U' ");
						applyPart();
						return Face.LEFT;
					case "R":
						solvePart.append("U' U' ");
						applyPart();
						return Face.FRONT;
					case "B":
						solvePart.append("U ");
						applyPart();
						return Face.RIGHT;
				}
				break;
			case LEFT:
				switch (front) {
					case "R":
						solvePart.append("U' ");
						applyPart();
						return Face.FRONT;
					case "B":
						solvePart.append("U' U' ");
						applyPart();
						return Face.RIGHT;
					case "O":
						solvePart.append("U ");
						applyPart();
						return Face.BACK;
				}
				break;
		}

		return face;
	}

	private void replaceWrongEdge() {

		Face face = findRightEdge();

		if (face == null) return;

		solvePart.append("R U' R' U' F' U F ");

		switch (face) {
			case RIGHT:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("R", "B")
						.replaceAll("F", "R")
				);
				break;
			case BACK:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("R", "L")
						.replaceAll("F", "B")
				);
				break;
			case LEFT:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("F", "L")
						.replaceAll("R", "F")
				);
				break;
		}

		applyPart();
	}

	private Face findRightEdge() {

		for (Face face : facesForCheck) {
			boolean done = isRightEdgeDone(face);
			if (!done) return face;
		}
		return null;
	}

	private boolean isRightEdgeDone(Face face) {

		String[][] frontFace = cube.getFaceCopy(face);
		String[][] rightFace = null;

		switch (face) {
			case FRONT:
				rightFace = cube.getFaceCopy(Face.RIGHT);
				break;
			case RIGHT:
				rightFace = cube.getFaceCopy(Face.BACK);
				break;
			case BACK:
				rightFace = cube.getFaceCopy(Face.LEFT);
				break;
			case LEFT:
				rightFace = cube.getFaceCopy(Face.FRONT);
				break;
		}

		return rightFace != null
				&& frontFace[1][1].equals(frontFace[1][2])
				&& rightFace[1][1].equals(rightFace[1][0]);
	}

	private Edge findEdge() {

		for (Face face : facesForCheck) {
			Edge edge = new Edge(face);
			if (!edge.hasYellow()) return edge;
		}
		return null;
	}

	@Override
	public boolean isPhaseDone() {

		for (Face face : facesForCheck) {
			String[][] faceArr = cube.getFaceCopy(face);
			String[] centralRow = faceArr[1];

			for (String sticker : centralRow) {
				if (!sticker.equals(faceArr[1][1])) return false;
			}
		}

		return new PhaseThree(cube).isPhaseDone();
	}

	private class Edge {

		private String up;
		private String front;
		private Face face;

		private Edge(Face face) {

			this.face = face;

			String[][] upFace = cube.getFaceCopy(Face.UP);
			String[][] frontFace = cube.getFaceCopy(face);

			front = frontFace[0][1];

			switch (face) {
				case FRONT:
					up = upFace[2][1];
					break;
				case RIGHT:
					up = upFace[1][2];
					break;
				case BACK:
					up = upFace[0][1];
					break;
				case LEFT:
					up = upFace[1][0];
					break;
			}
		}

		private String getUp() {
			return up;
		}

		private String getFront() {
			return front;
		}

		private Face getFace() {
			return face;
		}

		private List<String> getCollors() {
			return Arrays.asList(up, front);
		}

		private boolean hasYellow() {
			return up.equals("Y") || front.equals("Y");
		}
	}
}
