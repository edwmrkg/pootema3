package main;

public class Main {

	public static void main(String[] args) {
		Maze task1 = new Maze();
		task1.buildMaze(args[1]);
//		task1.move(args[2]);
		task1.findShortestPath(args[2]);
	}

}
