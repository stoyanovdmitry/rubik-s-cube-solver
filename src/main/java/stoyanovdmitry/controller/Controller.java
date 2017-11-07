package stoyanovdmitry.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;

public class Controller {

	private Cube cube;

	@FXML
	private GridPane up;
	@FXML
	private GridPane left;
	@FXML
	private GridPane front;
	@FXML
	private GridPane right;
	@FXML
	private GridPane back;
	@FXML
	private GridPane down;

	public Controller() {
		cube = new Cube();
	}

	@FXML
	public void initialize() {
		drawCube();
	}

	private void drawCube() {

		for (Face face : Face.values()) {

			String[][] faceArr = cube.getFaceCopy(face);

			GridPane currentPane = null;

			switch (face) {
				case UP:
					currentPane = up;
					break;
				case LEFT:
					currentPane = left;
					break;
				case FRONT:
					currentPane = front;
					break;
				case RIGHT:
					currentPane = right;
					break;
				case BACK:
					currentPane = back;
					break;
				case DOWN:
					currentPane = down;
			}

			ObservableList<Node> childrens = currentPane.getChildren();

			for (int i = 0, childNum = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++, childNum++) {

					String sticker = faceArr[i][j];

					childrens.get(childNum)
							.getStyleClass()
							.setAll(sticker);
				}
			}
		}
	}
}
