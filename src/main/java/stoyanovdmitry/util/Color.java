package stoyanovdmitry.util;

public enum Color {

	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	ORANGE("\u001B[35m"),
	BLUE("\u001B[34m"),
	RESET("\033[0m");

	private String ansiColor;

	Color(String ansiColor) {
		this.ansiColor = ansiColor;
	}

	public String getAnsiColor() {
		return ansiColor;
	}

}