package stoyanovdmitry.solver;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.solver.phases.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Solver {

	private String solve;
	private List<PhaseNum> solveStepPhases;
	private List<Phase> phases;

	private Cube cube;

	public Solver(Cube cube) {
		this.cube = cube;

		solveStepPhases = new ArrayList<>();
		phases = new PhaseFactory().emptyPhases();
	}

	public String getSolve() {
		if (solve == null) solveCube();
		return solve;
	}

	public List<PhaseNum> getSolveStepPhases() {
		if (solve == null) solveCube();
		return solveStepPhases;
	}

	public Cube getCube() {
		return cube;
	}

	private void solveCube() {

		solve = "";
		solveStepPhases = new ArrayList<>();

		for (Phase phase : phases) {

			phase.setCube(cube);
			phase.computePhase();
			String tempSolve = phase.getPhaseSolve();

			solve += tempSolve;
			fillMapSolve(tempSolve, phase);

			this.cube = phase.getCube();
		}
	}

	private void fillMapSolve(String solve, Phase phase) {

		PhaseNum phaseNum = null;

		switch (phase.getClass().getSimpleName()) {
			case "PhaseOne":
				phaseNum = PhaseNum.ONE;
				break;
			case "PhaseTwo":
				phaseNum = PhaseNum.TWO;
				break;
			case "PhaseThree":
				phaseNum = PhaseNum.THREE;
				break;
			case "PhaseFour":
				phaseNum = PhaseNum.FOUR;
				break;
			case "PhaseFive":
				phaseNum = PhaseNum.FIVE;
				break;
			case "PhaseSix":
				phaseNum = PhaseNum.SIX;
				break;
			case "PhaseSeven":
				phaseNum = PhaseNum.SEVEN;
				break;
			case "PhaseEight":
				phaseNum = PhaseNum.EIGHT;
				break;
		}

		for (int i = 0; i < solve.split(" ").length; i++) {
			solveStepPhases.add(phaseNum);
		}
	}
}
