package client.main;

import shared_classes.Board;
import shared_classes.Location;

public class AntProcessor {
	public Location move(Location antLocation, int boardRows, int boardColumns, Board board) {
		while (true) {
			float x = (float) Math.random();
			float y = (float) Math.random();
			int r;
			int c;
			if (x >= 0.5) {
				r = (antLocation.getRow() + 1) % boardRows;
			} else {
				r = (antLocation.getRow() - 1 + boardRows) % boardRows;
			}
			if (y >= 0.5) {
				c = (antLocation.getColumn() + 1) % boardColumns;
			} else {
				c = (antLocation.getColumn() - 1 + boardColumns) % boardColumns;
			}

			// check if new cell contains a heap
			if (board.getCellObjectType(r, c).equalsIgnoreCase("empty")) {
				return new Location(c, r);
			} else { // move in a new direction if we saw a heap it should not move on top of a heap
				continue;
			}
		}
	}

}
