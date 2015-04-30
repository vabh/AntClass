package shared_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

public class Heap implements Serializable, CellEntity {

	private static final long serialVersionUID = 6933443421222451481L;

	// private int r; // location on board
	// private int c;
	private Location location;
	private LinkedList<Integer> elements;
	private int types; // the different types of heap elements


	// probabilities for heap operation

	public Heap(int r, int c, int size, int types) {
		location = new Location(r, c);
		this.types = types;
		elements = new LinkedList<Integer>();

		// populating the heap
		for (int i = 0; i < size; i++) {
			elements.add((int) (Math.random() * types));
		}
	}

	public int getSize() {
		return elements.size();
	}

	public LinkedList<Integer> getHeapElements() {
		return elements;
	}

	// returns an upper bound on the types of heap elements
	public int getTypes() {
		return types;
	}

	@Override
	public String getEntityType() {
		return "heap";
	}

	@Override
	public int getDrawingSize() {
		return 30;
	}

	@Override
	public void drawOnBoard(Graphics g, int sizecol, int sizerow, Board board) {
		// Drawing heap objects of different types with different colors
		// Heap heap = (Heap) board.getBoardCells()[r][c].getEntityOnCell();
		for (int k = 0; k < getSize(); k++) {
			Color colorHeap = new Color((int) (((float) elements.get(k) / (float) getTypes()) * 255),
					(int) (((float) elements.get(k) / (float) getTypes()) * 255), (int) ((0.7) * 255));
			g.setColor(colorHeap);
			int x = getLocation().getRow() * sizecol;
			int y = getLocation().getColumn() * sizerow;
			g.fillRect((int) (x + (k) * (sizecol / getSize())), (int) (y + (sizerow * 0.1)), sizecol / getSize(),
					(int) (sizerow * 0.8));
			// Drawing an edge for a heap element, to differentiate their quantity
			g.setColor(Color.white);
			g.drawRect((int) (x + (k) * (sizecol / getSize())), (int) (y + (sizerow * 0.1)), sizecol / getSize(),
					(int) (sizerow * 0.8));
		}
	}

	// modified after the pickup and dropping operations
	public void updateHeap(LinkedList<Integer> heap) {
		this.elements = new LinkedList<Integer>();
		for (int i = 0; i < heap.size(); i++) {
			this.elements.add(heap.get(i)); // TODO: make sure it's consistent with the .pdf document description
		}
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
