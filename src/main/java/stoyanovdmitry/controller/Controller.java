package stoyanovdmitry.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

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

	private final Thread playAnimationThread;

	private boolean isPaused;
	private boolean isStoped;
	private boolean isPlaying;


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

	public Controller() {
		cube = new Cube();
		solveSteps = new ArrayList<>();
		currentStep = new AtomicInteger(0);

		playAnimationThread = new Thread(this::playNextStep);
	}

	synchronized private void playNextStep() {

		try {
			for (; currentStep.get() < solveSteps.size(); ) {

				int speed = (int) speedSlider.getValue();

				cube.rotateByPattern(
						solveSteps.get(currentStep.getAndIncrement())
				);
				drawCube();


				if (isPaused)
					wait();
				else if (isStoped) ;
				else
					Thread.sleep(speed * 100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

	//central buttons block

	@FXML
	private void shuffleCube() {
		cube.shuffle();
		solve = null;
		solveSteps = null;
		disableLeftBlock(true);

		drawCube();
	}

	private void disableLeftBlock(boolean b) {
		stepBackButton.setDisable(b);
		stepForwardButton.setDisable(b);
		speedSlider.setDisable(b);
		playButton.setDisable(b);
		pauseButton.setDisable(b);
		stopButton.setDisable(b);
	}

	@FXML
	private void computeCube() {
		try {
			Solver solver = new Solver(cube.clone());
			solve = solver.getSolve();
			solveSteps = Arrays.asList(solve.split(" "));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		speedSlider.setDisable(false);
		playButton.setDisable(false);
	}

	@FXML
	private void resetCube() {
		cube = new Cube();
		solve = null;
		solveSteps = null;
		disableLeftBlock(true);

		drawCube();
	}

	//left buttons block

	@FXML
	private void stepBack() {

	}

	@FXML
	private void stepForward() {

	}

	@FXML
	synchronized private void playAnimation() {
		System.out.println(playAnimationThread.isAlive());

		if (!playAnimationThread.isAlive())
			playAnimationThread.start();
		else
			notify();

		isStoped = false;
		isPaused = false;

		playButton.setDisable(true);
		pauseButton.setDisable(false);
		stopButton.setDisable(false);
	}

	@FXML
	private void pauseAnimation() {

		isPaused = true;
		isStoped = false;

		pauseButton.setDisable(true);
		playButton.setDisable(false);
		stopButton.setDisable(false);
	}

	@FXML
	synchronized private void stopAnimation() {

		isStoped = true;
		isPaused = false;

		stopButton.setDisable(true);
		pauseButton.setDisable(true);
		playButton.setDisable(false);
	}
}
