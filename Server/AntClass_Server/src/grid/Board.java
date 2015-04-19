package grid;

import grid.elements.Ant;
import grid.elements.Heap;

public class Board {
	Cell board[][];
	int rows;
	int columns;
	int types; //number of different heap elements for the board
	int heapSize; // maximum size of a heap
	
	public Board(int rows, int columns, int types, int heapSize){
		
		this.rows = rows;
		this.columns = columns;
		this.types = types;
		this.heapSize = heapSize;
		
		board = new Cell[rows][columns];
	}
	
	public void init(){
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
				int r = (int) (Math.random()*10);
				
				if(r > 5) {//ant
					board[i][j] = new Cell(new Ant(i, j), null);
				}
				else if(r < 2){//heap
					int hSize= (int)(Math.random() * heapSize + 1);
					board[i][j] = new Cell(null, new Heap(i, j, hSize, types));
					System.out.println(hSize);
				}
				else{//empty
					board[i][j] = new Cell(null, null);
				}
			}
		}
	}
		
	//returns -- ant | heap | empty
	public String getCellObjectType(int r, int c){
		return board[r][c].getEntityType();
	}
	
	public static void main(String args[]) {
		
		Board b = new Board(6, 6, 2, 5);
		b.init();
		
		for (int i = 0; i < b.rows; i++) {
			for (int j = 0; j < b.columns; j++) {
				Cell c = b.board[i][j];
				if(c.getEntityType().equals("ant")){
					System.out.print("ant  |");
				}
				else if (c.getEntityType().equals("heap")){
					System.out.print("heap"+ c.heap.getSize() +" |");
				}
				else
					System.out.print("-- |");
			}
			System.out.println();
		}
		
	}
}