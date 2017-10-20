package stoyanovdmitry.cube;

import stoyanovdmitry.util.Color;

import java.util.Arrays;

public class Cube {

	private static final String SPACES = "        ";

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
			for (int face = 1; face < 4; face++) {
				builder.append(Color.RESET.getAnsiColor())
						.append("| ");
				for (int sticker = 0; sticker < 3; sticker++) {
					builder.append(getColoredSticker(cube[face][row][sticker]))
							.append(" ");
				}
				if (face == 1) face = -1;
				else if (face == 0) face = 1;
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
				return sticker;
		}
	}
}
