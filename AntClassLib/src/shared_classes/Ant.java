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
