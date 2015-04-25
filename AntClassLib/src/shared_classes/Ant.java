package shared_classes;

import java.io.Serializable;

public class Ant implements Serializable {

	private static final long serialVersionUID = -6819460915834657895L;

	private int r, c; // location on board
	// private int id; // corresponds to client
	private Location location;
	private boolean carrying;
	private int heapElement; // -1 when !carrying

	// probabilities

	public Ant(int r, int c) {
		this.r = r;
		this.c = c;
		location = new Location(r, c);
		this.carrying = false;
		this.heapElement = -1;
	}

	public Ant(int r, int c, int id) {
		this.r = r;
		this.c = c;
		// this.id = id;
		location = new Location(r, c);
		this.carrying = false;
		this.heapElement = -1;
	}

	public boolean isCarrying() {
		return carrying;
	}

	public int getHeapElementType() {
		// -1 if ant is not carrying anything
		return heapElement;
	}

	public void drop() {
		carrying = false;
		heapElement = -1;
	}

	public void pickUp(int heapElement) {
		carrying = true;
		this.heapElement = heapElement;
	}

	// returns true if there is a heap found, false otherwise
	public boolean lookAround(Board board, Location heapLocation) { // the second parameter is the result being populated by this
																	// function
		// direction to look at
		int xPos[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int yPos[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

		Cell[][] cells = board.getBoardCells();
		int rows = board.getRows();
		int columns = board.getColumns();

		for (int i = 0; i < xPos.length; i++) {

			int lookatX = r + xPos[i];
			int lookatY = c + yPos[i];
			// System.out.println(i + " :" + lookatX + "," + lookatY);
			if (lookatX >= 0) {
				lookatX %= rows;
			} else if (lookatX < 0) {
				lookatX = rows - 1;
			}

			if (lookatY >= 0) {
				lookatY %= columns;
			} else if (lookatY < 0) {
				lookatY = columns - 1;
			}
			// System.out.println(i + " :" + lookatX + "," + lookatY + "--");
			// Cell c = cells[lookatX][lookatY];
			if (cells[lookatX][lookatY] == null) {
				continue;
			}
			if (cells[lookatX][lookatY].getEntityType().equals("heap")) {
				heapLocation = cells[lookatX][lookatY].getHeap().getLocation();
				// System.out.println("Heap:" + lookatX + "," + lookatY);
				return true;
			}
		}
		return false;
	}

	// this method should be called to make the ant move
	// called with board size, to make movement toroidal
	public void move(int boardRows, int boardColumns) {

		float x = (float) Math.random();
		float y = (float) Math.random();
		System.out.println(r + c);
		if (x >= 0.5) {
			r = (r + 1) % boardRows;
		} else {
			r -= 1;
			if (r < 0)
				r = boardRows - 1;
		}

		if (y >= 0.5) {
			c = (c + 1) % boardColumns;
		} else {
			c -= 1;
			if (r < 0)
				r = boardColumns - 1;
		}
		location.setRow(r);
		location.setColumn(c);
		// System.out.println(r + c);
	}

	public int[] getLocationArray() {
		int location[] = { r, c };
		return location;
	}

	public Location getLocation() {
		return location;
	}

	public void processDropAlgorithm() {

	}

	public void processPickUpAlgorithm() {

	}
}
