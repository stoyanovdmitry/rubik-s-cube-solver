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

	private Thread playAnimationThread;

	private boolean isPaused;
	private boolean isStopped;


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
				else if (isStopped) {
					returnInitCube();
					drawCube();
					wait();
				}
				else
					Thread.sleep(speed * 10);
			}

			disableLeftBlock(true);
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

	@FXML
	public void initialize() {
		drawCube();
	}

	synchronized private void drawCube() {

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
			currentStep.set(0);
			playAnimationThread = new Thread(this::playNextStep);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		stepBackButton.setDisable(false);
		stepForwardButton.setDisable(false);
		speedSlider.setDisable(false);
		playButton.setDisable(false);
	}

	@FXML
	private void resetCube() {
		cube = new Cube();
		solve = null;
		solveSteps = null;
		currentStep.set(0);
		disableLeftBlock(true);

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
		}
	}

	@FXML
	private void stepForward() {
		if (currentStep.get() < solveSteps.size()) {
			cube.rotateByPattern(
					solveSteps.get(currentStep.getAndIncrement())
			);
			drawCube();
		}
	}

	@FXML
	synchronized private void playAnimation() {

		if (!playAnimationThread.isAlive())
			playAnimationThread.start();
		else
			notify();

		isStopped = false;
		isPaused = false;

		playButton.setDisable(true);
		pauseButton.setDisable(false);
		stopButton.setDisable(false);
	}

	@FXML
	private void pauseAnimation() {

		isPaused = true;
		isStopped = false;

		pauseButton.setDisable(true);
		playButton.setDisable(false);
		stopButton.setDisable(false);
	}

	@FXML
	private void stopAnimation() {

		isStopped = true;
		isPaused = false;

		stopButton.setDisable(true);
		pauseButton.setDisable(true);
		playButton.setDisable(false);
	}
}
