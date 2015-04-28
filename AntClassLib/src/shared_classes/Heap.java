package shared_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Heap implements Serializable, CellEntity {

	private static final long serialVersionUID = 6933443421222451481L;

	// private int r; // location on board
	// private int c;
	private Location location;
	private int elements[];
	private int size;
	private int types; // the different types of heap elements


	// probabilities for heap operation

	public Heap(int r, int c, int size, int types) {
		location = new Location(r, c);
		this.size = size;
		this.types = types;
		elements = new int[size];

		// populating the heap
		for (int i = 0; i < elements.length; i++) {
			elements[i] = (int) (Math.random() * types);
		}
	}

	public int getSize() {
		return size;
	}

	public int[] getHeapElements() {
		return elements;
	}

	//returns an upper bound on the types of heap elements
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
		int[] heapelements = getHeapElements();
		for (int k = 0; k < getSize(); k++) {
			float color = (float) heapelements[k] / (float) getTypes();
			Color colortype = new Color((int) ((color) * 255), (int) ((color) * 255), (int) ((0.7) * 255));
			g.setColor(colortype);
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
	public void updateHeap(int heap[]) {
		this.size = heap.length;
		this.elements = new int[size];
		for (int i = 0; i < heap.length; i++) {
			this.elements[i] = heap[i];
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
