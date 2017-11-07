package stoyanovdmitry.solver.phases;

import stoyanovdmitry.cube.Cube;

import java.util.ArrayList;

public class PhaseFactory {

	public Phase create(PhaseNum phaseNum) {

		switch (phaseNum) {
			case ONE:
				return new PhaseOne();
			case TWO:
				return new PhaseTwo();
			case THREE:
				return new PhaseThree();
			case FOUR:
				return new PhaseFour();
			case FIVE:
				return new PhaseFive();
			case SIX:
				return new PhaseSix();
			case SEVEN:
				return new PhaseSeven();
			case EIGHT:
				return new PhaseEight();
		}

		return null;
	}

	public Phase create(PhaseNum phaseNum, Cube cube) {

		switch (phaseNum) {
			case ONE:
				return new PhaseOne(cube);
			case TWO:
				return new PhaseTwo(cube);
			case THREE:
				return new PhaseThree(cube);
			case FOUR:
				return new PhaseFour(cube);
			case FIVE:
				return new PhaseFive(cube);
			case SIX:
				return new PhaseSix(cube);
			case SEVEN:
				return new PhaseSeven(cube);
			case EIGHT:
				return new PhaseEight(cube);
		}

		return null;
	}

	public ArrayList<Phase> emptyPhases() {

		ArrayList<Phase> phaseList = new ArrayList<>();

		for (PhaseNum phaseNum : PhaseNum.values()) {
			phaseList.add(create(phaseNum));
		}

		return phaseList;
	}
}
