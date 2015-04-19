package grid.elements;

public class Heap {
	
	int heap[];
	int size;
	int types; // the different types of heal elements
	
	//probabilities for heap operation
	
	public Heap(int size, int types){
		this.size = size;
		this.types = types;
		heap = new int[size];
		
		//populating the heap
		for (int i = 0; i < heap.length; i++) {
			heap[i] = (int) (Math.random() * types); 
		}
	}
	public int getSize(){
		return size;
	}
	
}
