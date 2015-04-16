package grid;

public class Board {
	Cell board[][];
	int rows;
	int columns;
	
	public Board(int rows, int columns){
		
		this.rows = rows;
		this.columns = columns;
		board = new Cell[rows][columns];
	}
}
