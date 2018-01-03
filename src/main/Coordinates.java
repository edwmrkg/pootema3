package main;

import java.util.Stack;

public class Coordinates {
	private int x, y;
	private String heading = null;
	private int height, width;
	Stack<Integer> lastX = new Stack<Integer>();
	Stack<Integer> lastY = new Stack<Integer>();
	Stack<String> lastHeading = new Stack<String>();

	/**
	 * This constructor creates a Coordinates instance
	 * initializing the variables with values given.
	 * @param x - an integer representing the x-coordinate.
	 * @param y - an integer representing the y-coordinate.
	 * @param heading - a string representing the current heading.
	 * @param h - an integer representing the height (boundaries).
	 * @param w - an integer representing the width (boundaries).
	 */
	public Coordinates(int x, int y, String heading, int h, int w) {
		this.x = x;
		this.y = y;
		this.heading = heading;
		this.height = h;
		this.width = w;
	}
	
	/**
	 * This constructor clones an existing Coordinates instance
	 * @param pos - the Coordinates instance to be cloned
	 */
	public Coordinates(Coordinates pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.heading = pos.getHeading();
		this.height = pos.getHeight();
		this.width = pos.getWidth();
	}

	/**
	 * Sets the boundaries of the maze, the height and the width.
	 * @param height - an Integer representing the height.
	 * @param width - an Integer representing the width.
	 */
	public void setBoundaries(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * Sets the coordinates to the ones given as parameter.
	 * @param x - an Integer representing the x-coordinate.
	 * @param y - an Integer representing the y-coordinate.
	 */
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds the current coordinates to stack before taking a step.
	 */
	public void setLastCoord() {
		lastX.push(this.x);
		lastY.push(this.y);
	}
	
	/**
	 * Adds the current heading to stack before taking a step.
	 */
	public void setLastHeading() {
		lastHeading.push(this.heading);
	}

	/**
	 * Sets the heading to the given one.
	 * @param heading - a String representing the heading
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * Returns the heading of the hero.
	 * @return a String representing the heading.
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Gets the x-coordinate of the current position.
	 * 
	 * @return an Integer representing the coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y-coordinate of the current position.
	 * 
	 * @return an Integer representing the coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the height of the maze.
	 * @return an integer representing the height.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns the width of the maze.
	 * @return an integer representing the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Goes back a step to previous coordinates.
	 */
	public void goBack() {
		if (!lastX.isEmpty() && !lastY.isEmpty() && !lastHeading.isEmpty()) {
			setCoord(lastX.pop(), lastY.pop());
			setHeading(lastHeading.pop());
		}
	}

	/**
	 * Moves the coordinates one step to the right according to the current heading.
	 * @throws HeroOutOfGroundException when coordinates are out of boundaries.
	 */
	public void goRight() throws HeroOutOfGroundException {
		switch (heading) {
		case "North":
			setLastHeading();
			setLastCoord();
			setHeading("East");
			setCoord(x, y + 1);
			break;
		case "East":
			setLastHeading();
			setLastCoord();
			setHeading("South");
			setCoord(x + 1, y);
			break;
		case "South":
			setLastHeading();
			setLastCoord();
			setHeading("West");
			setCoord(x, y - 1);
			break;
		case "West":
			setLastHeading();
			setLastCoord();
			setHeading("North");
			setCoord(x - 1, y);
			break;
		}
		if (x < 0 || x >= height) {
			goBack();
			throw new HeroOutOfGroundException();
		}
		if (y < 0 || y >= width) {
			goBack();
			throw new HeroOutOfGroundException();
		}
	}

	/**
	 * Moves the coordinates one step forward according to the current heading.
	 * @throws HeroOutOfGroundException when coordinates are out of boundaries.
	 */
	public void goForward() throws HeroOutOfGroundException {
		switch (heading) {
		case "North":
			setLastHeading();
			setLastCoord();
			setCoord(x - 1, y);
			break;
		case "East":
			setLastHeading();
			setLastCoord();
			setCoord(x, y + 1);
			break;
		case "South":
			setLastHeading();
			setLastCoord();
			setCoord(x + 1, y);
			break;
		case "West":
			setLastHeading();
			setLastCoord();
			setCoord(x, y - 1);
			break;
		}
		if (x < 0 || x >= height) {
			goBack();
			throw new HeroOutOfGroundException();
		}
		if (y < 0 || y >= width) {
			goBack();
			throw new HeroOutOfGroundException();
		}
	}

	/**
	 * Moves the coordinates one step back according to the current heading.
	 * @throws HeroOutOfGroundException when coordinates are out of boundaries.
	 */
	public void goBackward() throws HeroOutOfGroundException {
		switch (heading) {
		case "North":
			setLastHeading();
			setLastCoord();
			setHeading("South");
			setCoord(x + 1, y);
			break;
		case "East":
			setLastHeading();
			setLastCoord();
			setHeading("West");
			setCoord(x, y - 1);
			break;
		case "South":
			setLastHeading();
			setLastCoord();
			setHeading("North");
			setCoord(x + 1, y);
			break;
		case "West":
			setLastHeading();
			setLastCoord();
			setHeading("East");
			setCoord(x, y + 1);
			break;
		}
		if (x < 0 || x >= height) {
			goBack();
			throw new HeroOutOfGroundException();
		}
		if (y < 0 || y >= width) {
			goBack();
			throw new HeroOutOfGroundException();
		}
	}

	/**
	 * Moves the coordinates one step to the left according to the current heading.
	 * @throws HeroOutOfGroundException when coordinates are out of boundaries.
	 */
	public void goLeft() throws HeroOutOfGroundException {
		switch (heading) {
		case "North":
			setLastHeading();
			setLastCoord();
			setHeading("East");
			setCoord(x, y - 1);
			break;
		case "East":
			setLastHeading();
			setLastCoord();
			setHeading("North");
			setCoord(x - 1, y);
			break;
		case "South":
			setLastHeading();
			setLastCoord();
			setHeading("West");
			setCoord(x, y + 1);
			break;
		case "West":
			setLastHeading();
			setLastCoord();
			setHeading("South");
			setCoord(x + 1, y);
			break;
		}
		if (x < 0 || x >= height) {
			goBack();
			throw new HeroOutOfGroundException();
		}
		if (y < 0 || y >= width) {
			goBack();
			throw new HeroOutOfGroundException();
		}
	}

	@Override
	public String toString() {
		return x + " " + y;
	}
}
