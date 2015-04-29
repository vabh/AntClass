package client.main;

import shared_classes.Location;

public class AntProcessor {
	public Location move(Location antLocation, int boardRows, int boardColumns) {
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
		return new Location(c, r);
	}

}
