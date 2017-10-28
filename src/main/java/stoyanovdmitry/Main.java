package stoyanovdmitry;

import stoyanovdmitry.cube.Cube;
import stoyanovdmitry.cube.Face;
import stoyanovdmitry.solver.Phase;
import stoyanovdmitry.solver.PhaseOne;

public class Main {

	public static void main(String[] args) {

		Cube cube = new Cube();
//		cube.rotateByPattern("D R F R' B L U F D' L B L F' U B L' D F D B");
		cube.rotateByPattern("F' R U ");
		Phase phase = new PhaseOne(cube);
		System.out.println(phase.getCube());
		phase.computePhase();
		System.out.println(phase.getCube());
//		System.out.println(cube);



	}
}