package stoyanovdmitry.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.Pane;
import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;
import stoyanovdmitry.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

	private List<String> solveSteps;

	private String solve;
	private Cube cube;

	private AtomicInteger currentStep;

	private Thread playAnimationThread;

	private boolean isPaused;
	private boolean isStopped;

	private List<Node> topRow;
	private List<Node> bottomRow;
	private List<Node> leftRow;
	private List<Node> rightRow;
	private List<Node> frontRow;
	private List<Node> backRow;

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

	@FXML
	private Button stepBackButton;
	@FXML
	private Button stepForwardButton;
	@FXML
	private Slider speedSlider;
	@FXML
	private Button playButton;
	@FXML
	private Button pauseButton;
	@FXML
	private Button stopButton;

	@FXML
	private Button shuffleButton;
	@FXML
	private Button computeButton;
	@FXML
	private Button resetButton;


	@FXML
	private GridPane paletteGrid;
	@FXML
	private Button paintCubeButton;
	@FXML
	private Button resetPaintButton;
	@FXML
	private Button cancelPaintButton;

	@FXML
	private String pickedColor;
	@FXML
	private Button pickedColorButton;

	public Controller() {
		cube = new Cube();
		solveSteps = new ArrayList<>();
		currentStep = new AtomicInteger(0);
	}

	@FXML
	public void initialize() {
		drawCube();
		initRows();
	}

	//central buttons block

	@FXML
	private void shuffleCube() {

		cube.shuffle();
		solve = null;
		solveSteps = null;
		disableLeftBlock(true);
		paintCubeButton.setDisable(false);

		drawCube();
	}

	@FXML
	private void computeCube() {

		try {

			Solver solver = new Solver(cube.clone());
			solve = solver.getSolve();
			solveSteps = Arrays.asList(solve.split(" "));
			currentStep.set(0);
			playAnimationThread = new Thread(this::playNextStep);

			stepBackButton.setDisable(false);
			stepForwardButton.setDisable(false);
			speedSlider.setDisable(false);
			playButton.setDisable(false);

			showArrows();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void resetCube() {
		isPaused = true;

		cube = new Cube();
		solve = null;
		solveSteps = null;
		currentStep.set(0);
		disableLeftBlock(true);
		paintCubeButton.setDisable(false);

		drawCube();
	}

	//left buttons block

	@FXML
	private void stepBack() {
		if (currentStep.get() > 0) {
			cube.rotateByPattern(
					revertStep(
							solveSteps.get(currentStep.decrementAndGet())
					)
			);
			drawCube();
			showArrows();
		}
	}

	@FXML
	private void stepForward() {
		if (currentStep.get() < solveSteps.size()) {
			cube.rotateByPattern(
					solveSteps.get(currentStep.getAndIncrement())
			);
			drawCube();
			showArrows();
		}

		if (currentStep.get() == solveSteps.size()) {
			disableLeftBlock(true);
		}
		disableCentralBlock(false);
	}

	@FXML
	private void playAnimation() {

		if (!playAnimationThread.isAlive())
			playAnimationThread.start();

		isStopped = false;
		isPaused = false;

		playButton.setDisable(true);
		pauseButton.setDisable(false);
		stopButton.setDisable(false);

		stepBackButton.setDisable(true);
		stepForwardButton.setDisable(true);

		disableCentralBlock(true);
	}

	@FXML
	private void pauseAnimation() {

		isPaused = true;
		isStopped = false;

		pauseButton.setDisable(true);
		playButton.setDisable(false);
		stopButton.setDisable(false);

		stepBackButton.setDisable(false);
		stepForwardButton.setDisable(false);
	}

	@FXML
	private void stopAnimation() {

		isStopped = true;
		isPaused = false;

		stopButton.setDisable(true);
		pauseButton.setDisable(true);
		playButton.setDisable(false);

		stepBackButton.setDisable(false);
		stepForwardButton.setDisable(false);

		disableCentralBlock(false);
	}

	@FXML
	private void chooseColor(MouseEvent mouseEvent) {

		Button button = (Button) mouseEvent.getSource();
		pickedColor = button.getStyleClass().get(1);
		pickedColorButton = button;
	}

	@FXML
	private void paintSticker(MouseEvent mouseEvent) {

		Pane pane = (Pane) mouseEvent.getSource();
		String remainingStickers = pickedColorButton.getText();

		if (!remainingStickers.equals("0") && pane.getStyleClass().isEmpty())
			try {
				pane.getStyleClass().setAll(pickedColor);
				int decrementedNum = Integer.parseInt(remainingStickers);
				decrementedNum--;
				pickedColorButton.setText(String.valueOf(decrementedNum));

				if (decrementedNum == 0 && isPaletteEmpty()) {
					parseCube();
					disableCentralBlock(false);
					disableRightBlock(true);
					paintCubeButton.setDisable(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@FXML
	private void paintCube() {

		for (Node node : paletteGrid.getChildren()) {
			Button button = (Button) node;
			button.setText("8");
		}

		disableLeftBlock(true);
		disableCentralBlock(true);
		disableRightBlock(false);
		clearAllNonCentralStickers();
		paintCubeButton.setDisable(true);
	}

	@FXML
	private void resetPaint() {

		paintCube();
	}

	@FXML
	private void cancelPaint() {

		disableCentralBlock(false);
		disableRightBlock(true);
		paintCubeButton.setDisable(false);
		resetCube();
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

	private void initRows() {

		topRow = new ArrayList<>();
		bottomRow = new ArrayList<>();
		leftRow = new ArrayList<>();
		rightRow = new ArrayList<>();
		frontRow = new ArrayList<>();
		backRow = new ArrayList<>();

		GridPane pane = left;

		for (int i = 0; i < 4; i++) {

			ObservableList<Node> childrens = pane.getChildren();

			for (int j = 0; j < 3; j++) {
				topRow.add(childrens.get(j));
				bottomRow.add(childrens.get(j + 6));
			}

			if (pane == left) pane = front;
			else if (pane == front) pane = right;
			else if (pane == right) pane = back;
		}

		pane = up;

		for (int i = 0; i < 3; i++) {

			ObservableList<Node> childrens = pane.getChildren();

			for (int j = 0; j < 8; j += 3) {
				leftRow.add(childrens.get(j));
				rightRow.add(childrens.get(j + 2));
			}

			if (pane == up) pane = front;
			else if (pane == front) pane = down;
		}

		ObservableList<Node> childrens = back.getChildren();

		leftRow.add(childrens.get(2));
		leftRow.add(childrens.get(5));
		leftRow.add(childrens.get(8));

		rightRow.add(childrens.get(0));
		rightRow.add(childrens.get(3));
		rightRow.add(childrens.get(6));

		fillFrontAndBack();
	}

	private void fillFrontAndBack() {

		GridPane pane = left;

		ObservableList<Node> childrens = pane.getChildren();

		frontRow.add(childrens.get(2));
		frontRow.add(childrens.get(5));
		frontRow.add(childrens.get(8));

		backRow.add(childrens.get(0));
		backRow.add(childrens.get(3));
		backRow.add(childrens.get(6));

		pane = up;

		childrens = pane.getChildren();

		frontRow.add(childrens.get(6));
		frontRow.add(childrens.get(7));
		frontRow.add(childrens.get(8));

		backRow.add(childrens.get(0));
		backRow.add(childrens.get(1));
		backRow.add(childrens.get(2));

		pane = right;

		childrens = pane.getChildren();

		frontRow.add(childrens.get(0));
		frontRow.add(childrens.get(3));
		frontRow.add(childrens.get(6));

		backRow.add(childrens.get(2));
		backRow.add(childrens.get(5));
		backRow.add(childrens.get(8));

		pane = down;

		childrens = pane.getChildren();

		frontRow.add(childrens.get(0));
		frontRow.add(childrens.get(1));
		frontRow.add(childrens.get(2));

		backRow.add(childrens.get(6));
		backRow.add(childrens.get(7));
		backRow.add(childrens.get(8));
	}

	synchronized private void playNextStep() {

		try {
			for (; currentStep.get() < solveSteps.size(); ) {

				int speed = (int) speedSlider.getValue();

				cube.rotateByPattern(
						solveSteps.get(currentStep.getAndIncrement())
				);
				drawCube();
				showArrows();


				if (isPaused)
					while (isPaused)
						Thread.sleep(100);
				else if (isStopped) {
					returnInitCube();
					drawCube();
					while (isStopped)
						Thread.sleep(100);
				}
				else
					Thread.sleep(speed * 100);
			}

			disableLeftBlock(true);
			disableCentralBlock(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void returnInitCube() {

		for (; currentStep.get() > 0; ) {

			String revertedStep = revertStep(
					solveSteps.get(currentStep.decrementAndGet())
			);
			cube.rotateByPattern(revertedStep);
		}
	}

	private String revertStep(String s) {

		String step = s;

		if (step.contains("'"))
			step = step.replaceAll("'", "");
		else
			step += "'";
		return step;
	}

	private void disableLeftBlock(boolean b) {
		stepBackButton.setDisable(b);
		stepForwardButton.setDisable(b);
		speedSlider.setDisable(b);
		playButton.setDisable(b);
		pauseButton.setDisable(b);
		stopButton.setDisable(b);
	}

	private void disableCentralBlock(boolean b) {
		shuffleButton.setDisable(b);
		computeButton.setDisable(b);
		resetButton.setDisable(b);
	}

	private void disableRightBlock(boolean b) {
		for (Node node : paletteGrid.getChildren()) {
			node.setDisable(b);
		}
		paintCubeButton.setDisable(b);
		resetPaintButton.setDisable(b);
		cancelPaintButton.setDisable(b);
	}

	private void showArrows() {

		if (currentStep.get() >= solveSteps.size()) {
			paintCubeButton.setDisable(false);
			return;
		}
		paintCubeButton.setDisable(true);

		String step = solveSteps.get(currentStep.get());

		if (step.contains("U") || step.contains("D")) showTopDownArrows(step);
		else if (step.contains("L") || step.contains("R")) showLeftRightArrows(step);
		else if (step.contains("F") || step.contains("B")) showFrontBackArrows(step);
	}

	private void showTopDownArrows(String step) {

		String degClass = "arrow180";

		if (step.equals("U'") || step.equals("D")) degClass = "";

		List<Node> currentRow = topRow;
		if (step.contains("D")) currentRow = bottomRow;

		for (Node node : currentRow) {
			node.getStyleClass().addAll("arrow", degClass);
		}
	}

	private void showLeftRightArrows(String step) {

		String degClass = "arrow90";

		if (step.equals("L'") || step.equals("R")) degClass = "arrow270";

		List<Node> currentRow = leftRow;
		if (step.contains("R")) currentRow = rightRow;

		for (int i = 0; i < currentRow.size(); i++) {
			currentRow.get(i).getStyleClass().addAll("arrow", degClass);

			if (i == 8) {
				if (degClass.equals("arrow90")) degClass = "arrow270";
				else if (degClass.equals("arrow270")) degClass = "arrow90";
			}
		}
	}

	private void showFrontBackArrows(String step) {

		String leftDeg = "arrow270";
		String upDeg = "";
		String rightDeg = "arrow90";
		String downDeg = "arrow180";

		if (step.equals("F'") || step.equals("B")) {
			leftDeg = "arrow90";
			upDeg = "arrow180";
			rightDeg = "arrow270";
			downDeg = "";
		}

		String currentDeg = leftDeg;
		List<Node> currentRow = frontRow;

		if (step.contains("B")) currentRow = backRow;

		for (int i = 0; i < currentRow.size(); i++) {

			currentRow.get(i).getStyleClass().addAll("arrow", currentDeg);

			if (i == 2) currentDeg = upDeg;
			else if (i == 5) currentDeg = rightDeg;
			else if (i == 8) currentDeg = downDeg;
		}
	}

	private void parseCube() {

		String[][][] cubeArray = new String[6][3][3];

		List<GridPane> facesList = Arrays.asList(left, front, right, back, up, down);

		for (int i = 0; i < facesList.size(); i++) {

			String[][] faceArray = cubeArray[i];

			List<Node> childrens = facesList.get(i).getChildren();

			for (int j = 0, k = 0; j < 3; j++, k += 3) {

				faceArray[j][0] = childrens.get(k).getStyleClass().get(0);
				faceArray[j][1] = childrens.get(k + 1).getStyleClass().get(0);
				faceArray[j][2] = childrens.get(k + 2).getStyleClass().get(0);
			}
		}

		cube = new Cube(cubeArray);
	}

	private boolean isPaletteEmpty() {

		for (Node node : paletteGrid.getChildren()) {
			Button button = (Button) node;
			if (!button.getText().equals("0")) return false;
		}
		return true;
	}

	private void clearAllNonCentralStickers() {

		List<GridPane> facesList = Arrays.asList(up, left, front, right, back, down);

		for (GridPane gridPane : facesList) {

			List<Node> childrens = gridPane.getChildren();

			for (int j = 0; j < childrens.size(); j++) {
				if (j != 4) childrens.get(j).getStyleClass().clear();
			}
		}
	}
}
