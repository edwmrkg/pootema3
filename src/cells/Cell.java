package cells;

public abstract class Cell {
	private int noOfVisits;
	private int distance;
	
	/**
	 * Increases the number of visits of the cell by 1.
	 */
	public void visit() {
		++noOfVisits;
	}
	
	/**
	 * Decreases the number of visits of the cell by 1.
	 */
	public void unvisit() {
		--noOfVisits;
	}
	
	/**
	 * Returns the number of visits of the cell.
	 * @return an Integer representing the number of visits.
	 */
	public int getVisits() {
		return noOfVisits;
	}

	/**
	 * Sets the distance to this cell.
	 * @param distance - an integer representing the distance.
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Returns the distance to this cell.
	 * @return an integer representing the distance.
	 */
	public int getDistance() {
		return distance;
	}
}
