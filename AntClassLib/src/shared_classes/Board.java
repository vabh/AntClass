package shared_classes;

import java.io.Serializable;

public class Board implements Serializable {

	private static final long serialVersionUID = -8340202074843934794L;

	private Cell cells[][];
	private int rows;
	private int columns;

	// private int types; // number of different heap elements for the board
	// private int maxHeapSize; // maximum size of a heap

	public Board(int rows, int columns, int types, int heapSize) {

		setRows(rows);
		setColumns(columns);

		// this.types = types;
		// this.maxHeapSize = heapSize;

		cells = new Cell[rows][columns];
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				cells[i][j] = new Cell(i, j);
			}
		}
	}

	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public int getColumns() {
		return columns;
	}


	public void setColumns(int columns) {
		this.columns = columns;
	}

	public Cell[][] getBoardCells() {
		return cells;
	}

	public CellEntity getCellEntity(Location location) {
		int r = location.getRow();
		int c = location.getColumn();

		return cells[r][c].getEntityOnCell();
	}

	public void destroyHeap(Location location) {
		if (!cells[location.getRow()][location.getColumn()].getEntityType().equalsIgnoreCase("heap")) {
			System.err.println("No heap found at " + location.toString());
			return;
		}
		cells[location.getRow()][location.getColumn()].setEntityOnCell(new EmptyCellEntity(location.getRow(), location
				.getColumn()));
	}

	// returns -- ant | heap | empty
	public String getCellObjectType(int r, int c) {
		return cells[r][c].getEntityType();
	}

	public String getCellObjectType(Location location) {
		int r = location.getRow();
		int c = location.getColumn();

		return cells[r][c].getEntityType();
	}

	public void placeAnt(Ant ant) {
		int r = ant.getLocation().getRow();
		int c = ant.getLocation().getColumn();
		if ((r >= 0 && r < rows) && (c >= 0 && c < columns)) {
			cells[r][c].setEntityOnCell(ant);
		}
	}

	public void placeHeap(Heap heap) {
		int r = heap.getLocation().getRow();
		int c = heap.getLocation().getColumn();
		if ((r >= 0 && r < rows) && (c >= 0 && c < columns)) {
			// System.out.println(cells[r][c].getEntityType());
			// System.out.println(r + ":" + c);
			cells[r][c].setEntityOnCell(heap);
		}
	}

	public void destroyAnt(Location location) {
		if (!cells[location.getRow()][location.getColumn()].getEntityType().equalsIgnoreCase("ant")) {
			System.err.println("No ant found at " + location.toString());
			return;
		}
		cells[location.getRow()][location.getColumn()].setEntityOnCell(new EmptyCellEntity(location.getRow(), location
				.getColumn()));

	}

}