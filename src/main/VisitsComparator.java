package main;

import java.util.Comparator;

import cells.*;

public class VisitsComparator implements Comparator<Cell> {

	@Override
	public int compare(Cell arg0, Cell arg1) {
		return arg0.getVisits() - arg1.getVisits();
	}

}
