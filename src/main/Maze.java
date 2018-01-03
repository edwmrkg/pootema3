package main;

import java.io.*;
import java.util.*;
import cells.*;

public class Maze {
	/* 2D list of the maze, the map. */
	private List<List<Cell>> maze = new ArrayList<List<Cell>>();

	/* current coordinates on the map */
	private Coordinates pos = null;

	/*
	 * The list of moves made from the starting point, until the end point is
	 * reached.
	 */
	private List<Coordinates> moves = new ArrayList<Coordinates>();

	enum Directions {
		Right, Front, Left, Back;
	}

	/**
	 * Builds the maze map, reading the map from a file. Reads a character from the
	 * file and create a matrix of Cells. Also, sets the starting position when
	 * encountering 'I' in file.
	 * 
	 * @param file
	 *            - a String representing the name of the input file.
	 * @throws IOException
	 */
	public void buildMaze(String file) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));

			String[] line = in.readLine().split(" ");
			int h = Integer.parseInt(line[0]);
			int w = Integer.parseInt(line[1]);

			/* parse the matrix from file and build the maze in memory */
			for (int i = 0; i < h; i++) {
				maze.add(i, new ArrayList<Cell>());
				String type = in.readLine();

				/*
				 * parse the line and get an instance of a Cell according to the character read
				 * from file.
				 */
				for (int j = 0; j < w; j++) {
					CellFactory fact = CellFactory.getInstance();
					maze.get(i).add(j, fact.getCell(type.charAt(j)));
					maze.get(i).get(j).setDistance(Integer.MAX_VALUE);

					/* set the starting position when 'I' is found */
					if (type.charAt(j) == 'I') {
						pos = new Coordinates(i, j, "North", h, w);
					}
				}
			}
			in.close();
		} catch (Exception e) {
			// System.out.println("Invalid input file.");
			return;
		}
	}

	/**
	 * Sorts a list of cells (and their corresponding directions) using a comparator
	 * by their number of visits.
	 * 
	 * @param cells
	 *            - a collection of instances of Cell.
	 * @param dirs
	 *            - a collection of Directions enum members.
	 * @param cmp
	 *            - a Comparator class.
	 */
	public void sort(List<Cell> cells, List<Directions> dirs, Comparator<Cell> cmp) {
		for (int i = 0; i < cells.size(); i++) {
			for (int j = i + 1; j < cells.size(); j++) {
				if (cmp.compare(cells.get(i), cells.get(j)) > 0) {
					/* Interchange values */
					Cell tmpc = cells.get(i);
					cells.set(i, cells.get(j));
					cells.set(j, tmpc);

					/* Interchange directions */
					Directions tmpd = dirs.get(i);
					dirs.set(i, dirs.get(j));
					dirs.set(j, tmpd);
				}
			}
		}
	}

	/**
	 * Tries to follow a direction. Returns true if available, false if out of
	 * boundaries or wall.
	 * 
	 * @param crtPos
	 *            - a Coordinates instance representing the position on the map.
	 * @param direction
	 *            - a String representing the direction to be followed.
	 * @return a boolean value indicating whether the direction is available.
	 * @throws HeroOutOfGroundException
	 * @throws CannotMoveIntoWallsException
	 */
	public boolean tryDirection(Coordinates crtPos, String direction)
			throws HeroOutOfGroundException, CannotMoveIntoWallsException {
		try {
			switch (direction) {
			case "Right":
				crtPos.goRight();
				break;
			case "Front":
				crtPos.goForward();
				break;
			case "Left":
				crtPos.goLeft();
				break;
			case "Back":
				crtPos.goBackward();
				break;
			}
		} catch (Exception e) {
			return false;
		}
		if (maze.get(crtPos.getX()).get(crtPos.getY()) instanceof WallCell) {
			crtPos.goBack();
			throw new CannotMoveIntoWallsException();
		}
		return true;
	}

	/**
	 * Returns a list of directions to be followed, sorted ascending by number of
	 * visits. It checks whether a cell is accessible and, if so, it adds it a the
	 * list, alongside its direction. Then, both lists are sorted. The hero will go
	 * in the direction indicated by the first value of the list.
	 * 
	 * @return a List of Directions.
	 */
	public List<Directions> getDirection() {
		List<Cell> possibleMoves = new ArrayList<Cell>();
		List<Directions> possibleDir = new ArrayList<Directions>();
		Coordinates crtPos = new Coordinates(pos);

		/* parse each direction in RFLB order */
		for (Directions dir : Directions.values()) {
			try {
				if (tryDirection(crtPos, dir.toString())) {
					possibleMoves.add(maze.get(crtPos.getX()).get(crtPos.getY()));
					possibleDir.add(dir);
					crtPos.goBack();
				}

			} catch (Exception e) {
			}
		}
		sort(possibleMoves, possibleDir, new VisitsComparator());
		return possibleDir;
	}

	/**
	 * Makes the moves on the map, always using the order: Right, Front, Left, Back.
	 * First, it adds the current position to the list of moves. Then it checks if
	 * the current cell is the exit cell. If yes, the method ends. Else, it asks for
	 * the list of directions available and follow the first one.
	 */
	public void move(String file) {
		moves.add(new Coordinates(pos));
		maze.get(pos.getX()).get(pos.getY()).visit();
		if (maze.get(pos.getX()).get(pos.getY()) instanceof EndCell) {
			printMoves(file);
			moves.clear();
			return;
		}

		try {
			tryDirection(pos, getDirection().get(0).toString());
		} catch (Exception e) {
		}
		move(file);
	}

	public void findShortestPath(String file) {
		Stack<Coordinates> possibleMoves = new Stack<Coordinates>();
		
		moves.add(new Coordinates(pos));
		possibleMoves.push(new Coordinates(pos));
		maze.get(pos.getX()).get(pos.getY()).visit();
		maze.get(pos.getX()).get(pos.getY()).setDistance(0);
		
		int minDistance = Integer.MAX_VALUE;
		int crtDistance;

		while (!possibleMoves.isEmpty()) {
			Coordinates crtPos = possibleMoves.pop();
			maze.get(crtPos.getX()).get(crtPos.getY()).visit();
			crtDistance = maze.get(crtPos.getX()).get(crtPos.getY()).getDistance();
			
			while (!moves.isEmpty() && crtDistance <= maze.get(moves.get(moves.size()-1).getX()).get(moves.get(moves.size()-1).getY()).getDistance()) {
				maze.get(moves.get(moves.size()-1).getX()).get(moves.get(moves.size()-1).getY()).unvisit();
				moves.remove(moves.size()-1);
			}
			moves.add(new Coordinates(crtPos));
			
			System.out.println(crtPos + " " + possibleMoves + " " + moves);			
			if (maze.get(crtPos.getX()).get(crtPos.getY()) instanceof EndCell) {
				if (crtDistance < minDistance) {
					minDistance = crtDistance;
					printMoves(file);
				}
			} else {
				for (Directions dir : Directions.values()) {
					try {
						if (tryDirection(crtPos, dir.toString()))
							if (maze.get(crtPos.getX()).get(crtPos.getY()).getVisits() == 0) {
								maze.get(crtPos.getX()).get(crtPos.getY()).setDistance(crtDistance+1);
								possibleMoves.push(new Coordinates(crtPos));
								crtPos.goBack();
							}
					} catch (Exception e) {
					}
				}
			}
		}
		moves.clear();
	}

	/**
	 * Prints the list of moves (positions reached) and the total number of moves
	 * made.
	 * @param file - a string containing the name of the file to write in.
	 */
	public void printMoves(String file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
		} catch (Exception e) {
			System.out.println("Invalid output file.");
			out.println("Invalid output file.");
			return;
		}
		System.out.println(moves.size());
		out.println(moves.size());
		for (int i = 0; i < moves.size(); i++) {
			System.out.println(moves.get(i));
			out.println(moves.get(i));
		}
		out.close();
	}
}