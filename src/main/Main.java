package main;

public class Main {

	public static void main(String[] args) {
		Maze maze = new Maze();
		maze.buildMaze(args[1]);
		switch(args[0]) {
		case "1":
			maze.findExit(args[2]);
			break;
		case "2":
			maze.findShortestPath(args[2]);
			break;
		}
	}

}
