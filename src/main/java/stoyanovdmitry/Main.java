package stoyanovdmitry;

import stoyanovdmitry.cube.Cube;

public class Main {

	public static void main(String[] args) {

		Cube cube = new Cube();
		cube.rotateByPattern("D R F R' B L U F D' L B L F' U B L' D F D B");
//		cube.rotateByPattern("D R F R'");
//		cube.rotateByPattern("U B");
//		cube.rotateByPattern("U R");
//		cube.rotateByPattern("R F D B L B B' L' B' D' F' R'");
//		cube.rotateByPattern("D R F R' B L U F D'");
//		cube.rotateByPattern("L B L F' U B L' D F D B");
		System.out.println(cube);
	}
}