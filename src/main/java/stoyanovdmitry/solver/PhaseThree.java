package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

import java.util.ArrayList;
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

	private void replaceCorner(Corner corner) {

		if (!corner.hasWhite()) return;

		Face face = setUpCornerInRightPosition(corner);

		if (corner.getUp().equals("W"))
			solvePart.append("R U' R' U' U' R U R' ");
		else if (corner.getFront().equals("W"))
			solvePart.append("F' U' F ");
		else if (corner.getRight().equals("W"))
			solvePart.append("R U R' ");
		else return;

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

	private Face setUpCornerInRightPosition(Corner corner) { //todo need to add applyPart()

		Face face = corner.getFace();
		List<String> nonWhiteStickers = corner.getNonWhiteStickers();

		switch (face) {
			case FRONT:
				if (nonWhiteStickers.contains("B") && nonWhiteStickers.contains("O")) {
					solvePart.append("U' ");
					return Face.RIGHT;
				}
				else if (nonWhiteStickers.contains("O") && nonWhiteStickers.contains("G")) {
					solvePart.append("U' U' ");
					return Face.BACK;
				}
				else if (nonWhiteStickers.contains("G") && nonWhiteStickers.contains("R")) {
					solvePart.append("U ");
					return Face.LEFT;
				}
			case RIGHT:
				if (nonWhiteStickers.contains("O") && nonWhiteStickers.contains("G")) {
					solvePart.append("U' ");
					return Face.BACK;
				}
				else if (nonWhiteStickers.contains("G") && nonWhiteStickers.contains("R")) {
					solvePart.append("U' U' ");
					return Face.LEFT;
				}
				else if (nonWhiteStickers.contains("R") && nonWhiteStickers.contains("B")) {
					solvePart.append("U ");
					return Face.FRONT;
				}
			case BACK:
				if (nonWhiteStickers.contains("G") && nonWhiteStickers.contains("R")) {
					solvePart.append("U' ");
					return Face.LEFT;
				}
				else if (nonWhiteStickers.contains("R") && nonWhiteStickers.contains("B")) {
					solvePart.append("U' U' ");
					return Face.FRONT;
				}
				else if (nonWhiteStickers.contains("B") && nonWhiteStickers.contains("O")) {
					solvePart.append("U ");
					return Face.RIGHT;
				}
			case LEFT:
				if (nonWhiteStickers.contains("R") && nonWhiteStickers.contains("B")) {
					solvePart.append("U' ");
					return Face.FRONT;
				}
				else if (nonWhiteStickers.contains("B") && nonWhiteStickers.contains("O")) {
					solvePart.append("U' U' ");
					return Face.RIGHT;
				}
				else if (nonWhiteStickers.contains("O") && nonWhiteStickers.contains("G")) {
					solvePart.append("U ");
					return Face.BACK;
				}
		}

		return corner.getFace();
	}

	private void replaceDownCorner() {

		Face face = findDownCorner();

		if (face == null) return;

		switch (face) {
			case FRONT:
				solvePart.append("R U' R' ");
				break;
			case RIGHT:
				solvePart.append("B U' B' ");
				break;
			case BACK:
				solvePart.append("L U' L' ");
				break;
			case LEFT:
				solvePart.append("F U' F' ");
				break;
		}

		applyPart();
	}

	private Face findDownCorner() {

		for (Face face : facesForCheck) {
			boolean done = isDownCornerDone(face);
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

		for (String[] rows : cube.getFaceCopy(Face.DOWN)) {
			for (String sticker : rows) {
				if (!sticker.equals("W")) return false;
			}
		}

		for (Face face : facesForCheck) {

			String[][] faceArray = cube.getFaceCopy(face);
			String[] bottomRow = faceArray[2];

			for (String sticker : bottomRow) {
				if (!sticker.equals(faceArray[1][1])) return false;
			}
		}

		return true;
	}

	private boolean isDownCornerDone(Face face) {

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
				if (downFace[2][2].equals("W")
						&& frontFace[2][2].equals(frontFace[1][1])
						&& rightFace[2][0].equals(rightFace[1][1]))
					return true;
				break;
			case BACK:
				rightFace = cube.getFaceCopy(Face.LEFT);
				if (downFace[2][0].equals("W")
						&& frontFace[2][2].equals(frontFace[1][1])
						&& rightFace[2][0].equals(rightFace[1][1]))
					return true;
				break;
			case LEFT:
				rightFace = cube.getFaceCopy(Face.FRONT);
				if (downFace[0][0].equals("W")
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

		boolean hasWhite() {
			return up.equals("W") || front.equals("W") || right.equals("W");
		}

		public List<String> getNonWhiteStickers() {

			List<String> stickers = new ArrayList<>();

			for (int i = 0; i < 2; i++) {
				if (!up.equals("W") && !stickers.contains(up))
					stickers.add(up);
				else if (!front.equals("W") && !stickers.contains(front))
					stickers.add(front);
				else if (!right.equals("W") && !stickers.contains(right))
					stickers.add(right);

			}
			return stickers;
		}
	}
}
