package cells;

public class CellFactory {
	private static final CellFactory INSTANCE = new CellFactory();
	
	private CellFactory() {
	
	}
	/**
	 * Returns the same instance of this class.
	 * @return a CellFactory instance.
	 */
	public static CellFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Creates an instance of a specific type of Cell according to the parameter:
	 * "." for ordinary cell,
	 * "#" for a wall,
	 * "I" for the starting cell,
	 * "O" for the ending cell.
	 * @param type - a String containing the type of the cell read from file
	 * @return a particular instance of Cell
	 */
	public Cell getCell(Character type) {
		switch(type) {
		case '.':
			return new OpenCell();
		case 'I':
			return new StartCell();
		case 'O':
			return new EndCell();
		case '#':
			return new WallCell();
		default:
			break;
		}
		return null;
	}
}
