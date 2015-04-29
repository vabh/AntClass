package shared_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Ant implements Serializable, CellEntity {

	private static final long serialVersionUID = -6819460915834657895L;

	private Location location;
	private boolean carrying;
	private int heapElement; // -1 when not carrying any object
	private float color;

	public Ant(int r, int c, float _color) {
		location = new Location(r, c);
		this.carrying = false;
		this.heapElement = -1;
		this.color = _color;
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

			int lookatX = getLocation().getRow() + xPos[i];
			int lookatY = getLocation().getColumn() + yPos[i];
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
				// heapLocation = new Location(lookatX, lookatY);
				heapLocation.setRow(lookatX);
				heapLocation.setColumn(lookatY);
				// System.out.println("Heap:" + lookatX + "," + lookatY);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getEntityType() {
		return "ant";
	}

	@Override
	public int getDrawingSize() {
		return 35;
	}

	@Override
	public void drawOnBoard(Graphics g, int sizecol, int sizerow, Board board) {
		// Drawing ants from their position on a board
		// float color = (float) heapelements[k] / (float) getTypes();
		Color colortype = new Color((int) ((0.7) * 255), (int) ((color) * 255), (int) ((color) * 255));
		g.setColor(colortype);
		// g.setColor(Color.orange);
		// System.err.println("ant location from drawOnBoard() function: " + getLocation().toString());
		int x = getLocation().getRow() * sizecol;
		int y = getLocation().getColumn() * sizerow;
		g.fillOval((int) (x + (sizecol - getDrawingSize() * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
				(int) (y + (sizerow - getDrawingSize() * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
				(int) (getDrawingSize() * (0.02 * Math.min(sizecol, sizerow))),
				(int) (getDrawingSize() * (0.02 * Math.min(sizecol, sizerow))));
	}

	public void processDropAlgorithm(Board board, Location heapLocation) {
		if (board.getCellObjectType(heapLocation).equals("heap")) {
			Heap heap = (Heap) board.getCellEntity(heapLocation);
			int heapElements[] = heap.getHeapElements();
			System.out.println("here");
			System.out.println(heapElements);

		}
	}

	public void processPickUpAlgorithm(Board board, Location heapLocation) {

	}

	public void assignColor(float _color) {
		color = _color;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void changeLocation(int row, int col) {
		this.location.setColumn(col);
		this.location.setRow(row);
	}

}
