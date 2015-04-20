package grid;

import grid.elements.Ant;
import grid.elements.Heap;

public class Cell {

	Ant ant;
	Heap heap;
	
	public Cell(){
		ant = null;
		heap = null;
	}
	
	public Cell(Ant ant, Heap heap){
		this.ant = ant;
		this.heap = heap;
	}
	
	//maybe we should have a GridElements class, and return an object of type GridElements
	public String getEntityType(){		
		if(ant != null)
			return "ant";
		else if( heap != null)
			return "heap";	
		else
			return "empty";
	}
}
