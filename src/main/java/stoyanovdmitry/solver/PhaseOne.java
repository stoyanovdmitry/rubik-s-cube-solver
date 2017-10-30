package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

/**
 * На данной фазе соибраеться нижний крест
 */
public class PhaseOne extends AbstractPhase {

	private static final int[][] COORDINATES = {
			{0, 1},
			{1, 0},
			{1, 2},
			{2, 1},
	};


	public PhaseOne(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {
			checkFace(Face.FRONT);
			checkFace(Face.LEFT);
			checkFace(Face.BACK);
			checkFace(Face.RIGHT);
			checkFace(Face.UP);
		}
	}

	private void checkFace(Face face) {

		String[][] tempFace;
		for (int[] COORDINATE : COORDINATES) {
			tempFace = cube.getFaceCopy(face);
			int y = COORDINATE[0];
			int x = COORDINATE[1];

			if (tempFace[y][x].equals("W")) {
				replace(x, y, face);
			}
		}
	}

	private void replace(int x, int y, Face face) {

		String[][] tempFace = cube.getFaceCopy(Face.DOWN);

		String[] fourStickers = {tempFace[0][1], tempFace[1][0], tempFace[1][2], tempFace[2][1]}; //стикеры с нижней стороны которые проходят проверку

		switch (face) {
			case FRONT:
				doReplace(x, y, fourStickers[0], fourStickers[1], fourStickers[2], face);
				break;
			case LEFT:
				doReplace(x, y, fourStickers[1], fourStickers[3], fourStickers[0], face);
				break;
			case RIGHT:
				doReplace(x, y, fourStickers[2], fourStickers[0], fourStickers[3], face);
				break;
			case BACK:
				doReplace(x, y, fourStickers[3], fourStickers[2], fourStickers[1], face);
				break;
			case UP:
				doReplaceUp(x, y, fourStickers);
				break;
		}
	}

	private void doReplaceUp(int x, int y, String[] fourStickers) {

		String[][] upFace = cube.getFaceCopy(Face.UP);

		if (y == 2 && !fourStickers[0].equals("W"))
			solvePart.append("F F ");
		else if (y == 1 && x == 0 && !fourStickers[1].equals("W"))
			solvePart.append("L L ");
		else if (y == 1 && x == 2 && !fourStickers[2].equals("W"))
			solvePart.append("R R ");
		else if (y == 0 && !fourStickers[3].equals("W"))
			solvePart.append("B B ");
		else if (y == 2 && upFace[0][1].equals("W") && !fourStickers[3].equals("W"))    //две дополнительные проверки что бы избежать зациклиной до бесконечности верхушки,
			solvePart.append("B B ");                                                   // когда два стикера расположены друг напротив друга
		else if (y == 1 && x == 0 && upFace[1][2].equals("W") && !fourStickers[2].equals("W"))
			solvePart.append("R R ");
		else
			solvePart.append("U ");

		applyPart();
	}

	private void doReplace(int x, int y, String up, String left, String right, Face face) {

		if (y == 0) { //done
			if (!right.equals("W")) {
				solvePart.append("F R' ");
				if (up.equals("W"))
					solvePart.append("F' ");
			}
			else if (!left.equals("W")) {
				solvePart.append("F' L ");
				if (!up.equals("W"))
					solvePart.append("F ");
			}
			else
				solvePart.append("U ");
		}
		else if (y == 1) { //done
			if (x == 0) {
				if (!left.equals("W"))
					solvePart.append("L ");
				else if (!right.equals("W")) {
					solvePart.append("F' F' R' ");
					if (up.equals("W"))
						solvePart.append("F F ");
				}
				else {
					solvePart.append("F U ");
					if (up.equals("W"))
						solvePart.append("F' ");
				}
			}
			else if (x == 2) {
				if (!right.equals("W"))
					solvePart.append("R' ");
				else if (!left.equals("W")) {
					solvePart.append("F F L ");
					if (up.equals("W"))
						solvePart.append("F' F' ");
				}
				else {
					solvePart.append("F' U ");
					if (up.equals("W"))
						solvePart.append("F ");
				}
			}
		}
		else if (y == 2) { //done
			if (!right.equals("W")) {
				solvePart.append("F' R' ");
			}
			else if (!left.equals("W")) {
				solvePart.append("F L ");
			}
			else solvePart.append("F F U ");
		}

		switch (face) {
			case LEFT:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("L", "B")
						.replaceAll("F", "L")
						.replaceAll("R", "F")
				);
				break;
			case RIGHT:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("R", "B")
						.replaceAll("F", "R")
						.replaceAll("L", "F")
				);
				break;
			case BACK:
				solvePart = new StringBuilder(solvePart.toString()
						.replaceAll("F", "B")
						.replaceAll("R", "l")
						.replaceAll("L", "R")
						.replaceAll("l", "L") //additional replaceAll for avoid errors
				);
				break;
		}

		applyPart();
	}

	@Override
	public boolean isPhaseDone() {
		boolean done = true;
		String[][] down = cube.getFaceCopy(Face.DOWN);

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

}
