package shared_classes;

public class Location {
	private int column;
	private int row;

	Location(int column, int row) {
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


}
