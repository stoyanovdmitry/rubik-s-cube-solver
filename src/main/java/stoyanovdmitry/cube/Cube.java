package stoyanovdmitry.cube;

import java.util.Arrays;

public class Cube {

	private static final String SPACES = "      ";

	private String[][][] cube = {
			{
					{"R", "R", "R"},
					{"R", "R", "R"},
					{"R", "R", "R"}
			},
			{
					{"G", "G", "G"},
					{"G", "G", "G"},
					{"G", "G", "G"}
			},
			{
					{"B", "B", "B"},
					{"B", "B", "B"},
					{"B", "B", "B"}
			},
			{
					{"O", "O", "O"},
					{"O", "O", "O"},
					{"O", "O", "O"}
			},
			{
					{"Y", "Y", "Y"},
					{"Y", "Y", "Y"},
					{"Y", "Y", "Y"}
			},
			{
					{"W", "W", "W"},
					{"W", "W", "W"},
					{"W", "W", "W"}
			},
	};

	private String[][] temp;

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
			builder.append(SPACES);

			for (int sticker = 0; sticker < 3; sticker++) {
				builder.append(cube[face][row][sticker])
						.append(" ");
			}

			builder.append("\n");
		}
		return builder.toString();
	}

	public String getFaces() {

		StringBuilder builder = new StringBuilder();

		for (int row = 0; row < 3; row++) {
			for (int face = 0; face < 4; face++) {
				for (int sticker = 0; sticker < 3; sticker++) {
					builder.append(cube[face][row][sticker])
					.append(" ");
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
