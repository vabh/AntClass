package shared_classes;

import java.io.Serializable;

public class Location implements Serializable {

	private static final long serialVersionUID = -6171531016967658900L;

	private int column;
	private int row;

	// invalid location
	public Location() {
		setRow(-1);
		setColumn(-1);
	}

	public Location(int row, int column) {
		setRow(row);
		setColumn(column);
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	@Override
	public String toString() {
		return "[" + row + ", " + column + "]";
	}
}
