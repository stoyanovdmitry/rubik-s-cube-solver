package stoyanovdmitry.cube;

import stoyanovdmitry.util.Color;

public class Cube implements Cloneable {

	private static final String SPACES = "        ";

	private String[][][] cube = {
			{//LEFT
					{"G", "G", "G"},
					{"G", "G", "G"},
					{"G", "G", "G"}
			},
			{//FRONT
					{"R", "R", "R"},
					{"R", "R", "R"},
					{"R", "R", "R"}
			},
			{//RIGHT
					{"B", "B", "B"},
					{"B", "B", "B"},
					{"B", "B", "B"}
			},
			{//BACK
					{"O", "O", "O"},
					{"O", "O", "O"},
					{"O", "O", "O"}
			},
			{//UP
					{"Y", "Y", "Y"},
					{"Y", "Y", "Y"},
					{"Y", "Y", "Y"}
			},
			{//DOWN
					{"W", "W", "W"},
					{"W", "W", "W"},
					{"W", "W", "W"}
			},
	};

	public Cube() {

	}

	public Cube(String[][][] cube) {
		this.cube = cube;
	}

	public void rotateByPattern(String pattern) {

		String[] steps = pattern.split(" ");
		for (String step : steps) {
			System.out.print(step + " ");
		}
		System.out.println();
		for (String step : steps) {
			switch (step) {
				case "R":
					rotateRight(false);
					break;
				case "R'":
					rotateRight(true);
					break;
				case "L":
					rotateLeft(false);
					break;
				case "L'":
					rotateLeft(true);
					break;
				case "F":
					rotateFront(false);
					break;
				case "F'":
					rotateFront(true);
					break;
				case "D":
					rotateDown(false);
					break;
				case "D'":
					rotateDown(true);
					break;
				case "U":
					rotateUp(false);
					break;
				case "U'":
					rotateUp(true);
					break;
				case "B":
					rotateBack(false);
					break;
				case "B'":
					rotateBack(true);
					break;
			}
		}
	}

	public void rotateRight(boolean isCounterClockwise) {

		if (!isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[1][i][2],
						cube[5][i][2],
						cube[3][d][0],
						cube[4][i][2],
				};
				cube[4][i][2] = temp[0];
				cube[1][i][2] = temp[1];
				cube[5][i][2] = temp[2];
				cube[3][d][0] = temp[3];
			}

			clockwiseTurn(2);
		} else if (isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[1][i][2],
						cube[5][i][2],
						cube[3][d][0],
						cube[4][i][2],
				};
				cube[5][i][2] = temp[0];
				cube[3][d][0] = temp[1];
				cube[4][i][2] = temp[2];
				cube[1][i][2] = temp[3];
			}

			counterClockwiseTurn(2);
		}
	}

	public void rotateLeft(boolean isCounterClockwise) {

		if (!isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[1][i][0],
						cube[5][i][0],
						cube[3][d][2],
						cube[4][i][0],
				};
				cube[5][i][0] = temp[0];
				cube[3][d][2] = temp[1];
				cube[4][i][0] = temp[2];
				cube[1][i][0] = temp[3];
			}

			clockwiseTurn(0);
		} else if (isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[1][i][0],
						cube[5][i][0],
						cube[3][d][2],
						cube[4][i][0],
				};
				cube[4][i][0] = temp[0];
				cube[1][i][0] = temp[1];
				cube[5][i][0] = temp[2];
				cube[3][d][2] = temp[3];
			}

			counterClockwiseTurn(0);
		}
	}

	public void rotateFront(boolean isCounterClockwise) {

		if (!isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[0][i][2],
						cube[5][0][i],
						cube[2][d][0],
						cube[4][2][d],
				};
				cube[4][2][d] = temp[0];
				cube[0][i][2] = temp[1];
				cube[5][0][i] = temp[2];
				cube[2][d][0] = temp[3];
			}

			clockwiseTurn(1);
		} else if (isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[0][i][2],
						cube[5][0][i],
						cube[2][d][0],
						cube[4][2][d],
				};
				cube[5][0][i] = temp[0];
				cube[2][d][0] = temp[1];
				cube[4][2][d] = temp[2];
				cube[0][i][2] = temp[3];
			}

			counterClockwiseTurn(1);
		}
	}

	public void rotateBack(boolean isCounterClockwise) {

		if (!isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[0][i][0],
						cube[5][2][i],
						cube[2][d][2],
						cube[4][0][d],
				};
				cube[5][2][i] = temp[0];
				cube[2][d][2] = temp[1];
				cube[4][0][d] = temp[2];
				cube[0][i][0] = temp[3];
			}

			clockwiseTurn(3);
		} else if (isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[0][i][0],
						cube[5][2][i],
						cube[2][d][2],
						cube[4][0][d],
				};
				cube[4][0][d] = temp[0];
				cube[0][i][0] = temp[1];
				cube[5][2][i] = temp[2];
				cube[2][d][2] = temp[3];
			}

			counterClockwiseTurn(3);
		}
	}

	public void rotateUp(boolean isCounterClockwise) {

		if (!isCounterClockwise) {
			for (int i = 0; i < 3; i++) {
				String[] temp = new String[]{
						cube[1][0][i],
						cube[0][0][i],
						cube[2][0][i],
						cube[3][0][i],
				};
				cube[0][0][i] = temp[0];
				cube[3][0][i] = temp[1];
				cube[1][0][i] = temp[2];
				cube[2][0][i] = temp[3];
			}

			clockwiseTurn(4);
		} else if (isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[1][0][i],
						cube[0][0][i],
						cube[2][0][i],
						cube[3][0][i],
				};
				cube[2][0][i] = temp[0];
				cube[1][0][i] = temp[1];
				cube[3][0][i] = temp[2];
				cube[0][0][i] = temp[3];
			}

			counterClockwiseTurn(4);
		}
	}

	public void rotateDown(boolean isCounterClockwise) {

		if (!isCounterClockwise) {
			for (int i = 0; i < 3; i++) {
				String[] temp = new String[]{
						cube[0][2][i],
						cube[1][2][i],
						cube[2][2][i],
						cube[3][2][i],
				};
				cube[1][2][i] = temp[0];
				cube[2][2][i] = temp[1];
				cube[3][2][i] = temp[2];
				cube[0][2][i] = temp[3];
			}

			clockwiseTurn(5);
		} else if (isCounterClockwise) {
			for (int i = 0, d = 2; i < 3; i++, d--) {
				String[] temp = new String[]{
						cube[0][2][i],
						cube[1][2][i],
						cube[2][2][i],
						cube[3][2][i],
				};
				cube[3][2][i] = temp[0];
				cube[0][2][i] = temp[1];
				cube[1][2][i] = temp[2];
				cube[2][2][i] = temp[3];
			}

			counterClockwiseTurn(5);
		}
	}

	private void counterClockwiseTurn(int face) {

		String[][] temp = getFaceCopy(face);
		String[][] faceArray = cube[face];

		faceArray[0][0] = temp[0][2];
		faceArray[0][1] = temp[1][2];
		faceArray[0][2] = temp[2][2];

		faceArray[1][0] = temp[0][1];
		faceArray[2][0] = temp[0][0];

		faceArray[2][1] = temp[1][0];
		faceArray[2][2] = temp[2][0];

		faceArray[1][2] = temp[2][1];
	}

	private void clockwiseTurn(int face) {

		String[][] temp = getFaceCopy(face);
		String[][] faceArray = cube[face];

		faceArray[0][2] = temp[0][0];
		faceArray[1][2] = temp[0][1];
		faceArray[2][2] = temp[0][2];

		faceArray[0][1] = temp[1][0];
		faceArray[0][0] = temp[2][0];

		faceArray[1][0] = temp[2][1];
		faceArray[2][0] = temp[2][2];

		faceArray[2][1] = temp[1][2];
	}

	private String[][] getFaceCopy(int face) {
		String[][] faceCopy = new String[3][3];
		for (int row = 0; row < faceCopy.length; row++) {
			faceCopy[row] = cube[face][row].clone();
		}
		return faceCopy;
	}

	//возвращает массив - сторону
	public String[][] getFaceCopy(Face face) {

		return getFaceCopy(face.ordinal());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append(getFace(true)) //todo getFace(isUp)
				.append(getFaces())//todo getFaces();
				.append(getFace(false));

		return builder.toString();
	}

	private String getFace(boolean isUp) {

		StringBuilder builder = new StringBuilder();
		int face = isUp ? 4 : 5;

		for (int row = 0; row < 3; row++) {
			builder.append(SPACES)
					.append("| ");

			for (int sticker = 0; sticker < 3; sticker++) {
				builder.append(getColoredSticker(cube[face][row][sticker]))
						.append(" ");
			}

			builder.append(Color.RESET.getAnsiColor())
					.append("|")
					.append("\n");
		}
		return builder.toString();
	}

	private String getFaces() {

		StringBuilder builder = new StringBuilder();

		for (int row = 0; row < 3; row++) {
			for (int face = 0; face < 4; face++) {
				builder.append(Color.RESET.getAnsiColor())
						.append("| ");
				for (int sticker = 0; sticker < 3; sticker++) {
					builder.append(getColoredSticker(cube[face][row][sticker]))
							.append(" ");
				}
			}
			builder.append(Color.RESET.getAnsiColor())
					.append("|")
					.append("\n");
		}
		return builder.toString();
	}

	private String getColoredSticker(String sticker) {

		switch (sticker) {
			case "R":
				return Color.RED.getAnsiColor() + sticker;
			case "B":
				return Color.BLUE.getAnsiColor() + sticker;
			case "G":
				return Color.GREEN.getAnsiColor() + sticker;
			case "Y":
				return Color.YELLOW.getAnsiColor() + sticker;
			case "O":
				return Color.ORANGE.getAnsiColor() + sticker;
			default:
				return Color.RESET.getAnsiColor() + sticker;
		}
	}

	private void setCube(String[][][] cube) {
		this.cube = cube;
	}

	@Override
	public Cube clone() throws CloneNotSupportedException {

		String[][][] cubeArrCopy = {
				getFaceCopy(0),
				getFaceCopy(1),
				getFaceCopy(2),
				getFaceCopy(3),
				getFaceCopy(4),
				getFaceCopy(5),
		};

		return new Cube(cubeArrCopy);
	}
}
