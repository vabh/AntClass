package grid;

import grid.elements.Ant;
import grid.elements.Heap;

public class Board {
	Cell board[][];
	int rows;
	int columns;
	
	public Board(int rows, int columns){
		
		this.rows = rows;
		this.columns = columns;
		board = new Cell[rows][columns];
	}
	
	public void init(){
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
				int r = (int) (Math.random()*10);
				if(r > 0.5)
					board[i][j] = new Cell(new Ant(i, j), null);
				else if(r < 0.3)
					board[i][j] = new Cell(null, new Heap((int)Math.random()*20));
			}
		}
	}
}