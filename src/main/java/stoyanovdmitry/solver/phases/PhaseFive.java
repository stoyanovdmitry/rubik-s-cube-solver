package stoyanovdmitry.solver.phases;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

class PhaseFive extends AbstractPhase {

	private static final int[][] COORDINATES = {
			{0, 1},
			{1, 0},
			{1, 2},
			{2, 1},
	};

	PhaseFive() {
		super();
	}

	public PhaseFive(Cube cube) {
		super(cube);
	}

	@Override
	public void computePhase() {

		while (!isPhaseDone()) {

			boolean paralleled = setUpTop();

			if (paralleled)
				solvePart.append("F R U R' U' F' ");
			else
				solvePart.append("F R U R' U' R U R' U' F' ");

			applyPart();
		}
	}

	private boolean setUpTop() {

		boolean isParalleled = false;
		int yellowStickers = countUpStickers();

		if (yellowStickers == 0) {
			solvePart.append("F R U R' U' F' ");
			solvePart.append("U U ");
			applyPart();
		}
		if (yellowStickers == 2) {

			Cross cross = new Cross();

			if (cross.getUp().equals(cross.getDown())
					|| cross.getLeft().equals(cross.getRight())) {

				isParalleled = true;
				if (cross.getUp().equals(cross.getDown())) {
					solvePart.append("U ");
				}
			}
			else if (cross.getLeft().equals(cross.getUp()))
				return false;
			else if (cross.getUp().equals(cross.getRight()))
				solvePart.append("U' ");
			else if (cross.getRight().equals(cross.getDown()))
				solvePart.append("U U ");
			else if (cross.getDown().equals(cross.getLeft()))
				solvePart.append("U ");
		}

		applyPart();
		return isParalleled;
	}

	private int countUpStickers() {

		int counter = 0;

		Cross cross = new Cross();

		for (String sticker : cross.getArray()) {
			if (sticker.equals("Y"))
				counter++;
		}

		return counter;
	}

	@Override
	public boolean isPhaseDone() {

		return new Cross().isDone();
	}

	private class Cross {

		String up;
		String left;
		String right;
		String down;

		Cross() {

			String[][] face = cube.getFaceCopy(Face.UP);

			up = face[0][1];
			left = face[1][0];
			right = face[1][2];
			down = face[2][1];
		}

		Cross(String[][] face) {

			up = face[0][1];
			left = face[1][0];
			right = face[1][2];
			down = face[2][1];
		}

		String getUp() {
			return up;
		}

		String getLeft() {
			return left;
		}

		String getRight() {
			return right;
		}

		String getDown() {
			return down;
		}

		String[] getArray() {

			String[] arr = {up, left, right, down};
			return arr;
		}

		boolean isDone() {

			for (String sticker : getArray()) {
				if (!sticker.equals("Y"))
					return false;
			}
			return true;
		}
	}
}
