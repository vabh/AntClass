package shared_classes;


public class Board {
	
	private Cell cells[][];
	private int rows;
	private int columns;
	private int types; //number of different heap elements for the board
	private int maxHeapSize; // maximum size of a heap
	
	public Board(int rows, int columns, int types, int heapSize){
		
		this.rows = rows;
		this.columns = columns;
		this.types = types;
		this.maxHeapSize = heapSize;
		
		cells = new Cell[rows][columns];
	}
	
	public Cell[][] getBoardCells(){
		return cells;
	}
	
	public void init(){
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
				int r = (int) (Math.random()*10);
				
				if(r > 5) {//ant
					cells[i][j] = new Cell(new Ant(i, j), null);
				}
				else if(r < 2){//heap
					int hSize= (int)(Math.random() * maxHeapSize + 1);
					cells[i][j] = new Cell(null, new Heap(i, j, hSize, types));
					System.out.println(hSize);
				}
				else{//empty
					cells[i][j] = new Cell(null, null);
				}
			}
		}
	}
		
	//returns -- ant | heap | empty
	public String getCellObjectType(int r, int c){
		return cells[r][c].getEntityType();
	}
	
	public boolean destroyAnt(int r, int c){
		
		if(cells[r][c].getEntityType().equals("ant")){
			cells[r][c].ant = null;
			return true;
		}
		else
			return false;
	}
	//true if heap can be destroyed, else false
	public boolean destroyHeap(int r, int c){
		if(cells[r][c].getEntityType().equals("heap")){
			cells[r][c].heap = null;
			return true;
		}
		else
			return false;			
	}
	
	public void placeAnt(Ant ant){
		int pos[] = ant.getLocation();
		int r = pos[0];
		int c = pos[1];
		if( (r >= 0 && r < rows) && (c >= 0 && c < columns)){
			cells[r][c] = null;
			cells[r][c] = new Cell(ant, null);
		}
	}
	
	public void placeHeap(Heap heap){
		int pos[] = heap.getLocation();
		int r = pos[0];
		int c = pos[1];
		if( (r >= 0 && r < rows) && (c >= 0 && c < columns)){
			cells[r][c] = null;
			cells[r][c] = new Cell(null, heap);
		}
	}
	
	public void move(Ant ant) {
		
		int pos[] = ant.getLocation();
		
		if(destroyAnt(pos[0], pos[1])){
		
			ant.move(rows, columns);				
			placeAnt(ant);
		}
		
	}
	
	public static void main(String args[]) {
		
		Board b = new Board(6, 6, 2, 5);
		b.init();
		
		for (int i = 0; i < b.rows; i++) {
			for (int j = 0; j < b.columns; j++) {
				Cell c = b.cells[i][j];
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