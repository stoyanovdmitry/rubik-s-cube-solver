package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

public class PhaseOne extends AbstractPhase {

	private static final int[][] COORDINATES = {
			{0, 1},
			{1, 0},
			{1, 2},
			{2, 1},
	};
	private StringBuilder solveBuilder;
	private StringBuilder solvePart;


	public PhaseOne(Cube cube) {
		super(cube);
		solveBuilder = new StringBuilder();
		solvePart = new StringBuilder();
	}

	public void computePhase() {

		checkFace(Face.FRONT);
//
//		while (!isPhaseDone()) {
//			checkFace(Face.FRONT);
//		}
	}

	private void checkFace(Face face) {

		String[][] tempFace;
		for (int[] COORDINATE : COORDINATES) {
			tempFace = getCube().getFaceCopy(face);
			int y = COORDINATE[0];
			int x = COORDINATE[1];

			if (tempFace[y][x].equals("W")) {
				replace(x, y, face);
			}
		}
	}

	private void replace(int x, int y, Face face) {

		String[][] tempFace = getCube().getFaceCopy(Face.DOWN);

		String[] fourStickers = {tempFace[0][1], tempFace[1][0], tempFace[1][2], tempFace[2][1]}; //стикеры с нижней стороны которые проходят проверку

		switch (face) {
			case FRONT:
				replaceFront(x, y, fourStickers);
				break;
			case LEFT:
				replaceLeft(x, y, fourStickers);
				break;
			case RIGHT:
				replaceRight(x, y, fourStickers);
				break;
			case BACK:
				replaceBack(x, y, fourStickers);
				break;
		}
	}

	private void replaceBack(int x, int y, String[] fourStickers) {

	}

	private void replaceRight(int x, int y, String[] fourStickers) {

	}

	private void replaceLeft(int x, int y, String[] fourStickers) {

		if (y == 0) { //верх
			if (!tempFace[0][1].equals("W")) {
				solvePart.append("F R' ");
				if (tempFace[1][0].equals("W"))
					solvePart.append("F' ");
			} else if (!tempFace[2][1].equals("W")) {
				solvePart.append("F' L ");
				if (!tempFace[1][0].equals("W"))
					solvePart.append("F ");
			} else
				solvePart.append("U ");
		} else if (y == 1) { //центральная линия..
			if (x == 0) {//..справа
				if (!tempFace[2][1].equals("W"))
					solvePart.append("L ");
				else if (!tempFace[0][1].equals("W")) {
					solvePart.append("F' F' R' ");
					if (tempFace[1][0].equals("W"))
						solvePart.append("F F ");
				} else {
					solvePart.append("F U ");
					if (tempFace[1][0].equals("W"))
						solvePart.append("F' ");
				}
			} else if (x == 2) { //..слева
				if (!tempFace[0][1].equals("W"))
					solvePart.append("R' ");
				else if (!tempFace[2][1].equals("W")) {
					solvePart.append("F F L ");
					if (tempFace[1][0].equals("W"))
						solvePart.append("F' F' ");
				} else {
					solvePart.append("F' U ");
					if (tempFace[1][0].equals("W"))
						solvePart.append("F ");
				}
			}
		} else if (y == 2) { //низ
			if (!tempFace[0][1].equals("W")) {
				solvePart.append("F' R' ");
			} else if (!tempFace[2][1].equals("W")) {
				solvePart.append("F L ");
			} else solvePart.append("F F U ");
		}
		applyPart();
	}

	private void replaceFront(int x, int y, String[] fourStickers) {

		String up = fourStickers[0];
		String left = fourStickers[1];
		String right = fourStickers[2];

		if (y == 0) { //done
			if (!tempFace[1][2].equals("W")) {
				solvePart.append("F R' ");
				if (tempFace[0][1].equals("W"))
					solvePart.append("F' ");
			} else if (!tempFace[1][0].equals("W")) {
				solvePart.append("F' L ");
				if (!tempFace[0][1].equals("W"))
					solvePart.append("F ");
			} else
				solvePart.append("U ");
		} else if (y == 1) { //done
			if (x == 0) {
				if (!tempFace[1][0].equals("W"))
					solvePart.append("L ");
				else if (!tempFace[1][2].equals("W")) {
					solvePart.append("F' F' R' ");
					if (tempFace[0][1].equals("W"))
						solvePart.append("F F ");
				} else {
					solvePart.append("F U ");
					if (tempFace[0][1].equals("W"))
						solvePart.append("F' ");
				}
			} else if (x == 2) {
				if (!tempFace[1][2].equals("W"))
					solvePart.append("R' ");
				else if (!tempFace[1][0].equals("W")) {
					solvePart.append("F F L ");
					if (tempFace[0][1].equals("W"))
						solvePart.append("F' F' ");
				} else {
					solvePart.append("F' U ");
					if (tempFace[0][1].equals("W"))
						solvePart.append("F ");
				}
			}
		} else if (y == 2) { //done
			if (!tempFace[1][2].equals("W")) {
				solvePart.append("F' R' ");
			} else if (!tempFace[1][0].equals("W")) {
				solvePart.append("F L ");
			} else solvePart.append("F F U ");
		}
		applyPart();
	}

	private boolean isPhaseDone() {
		boolean done = true;
		String[][] down = getCube().getFaceCopy(Face.DOWN);

		for (int[] coordinate : COORDINATES) {
			int y = coordinate[0];
			int x = coordinate[1];

			if (!down[y][x].equals("W")) {
				done = false;
				break;
			}
		}

		return done;
	}

	private void applyPart() {
		solveBuilder.append(solvePart);
		getCube().rotateByPattern(solvePart.toString());
		solvePart = new StringBuilder();
	}
}
