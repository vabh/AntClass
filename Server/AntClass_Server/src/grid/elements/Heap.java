package grid.elements;

public class Heap {
	
	int heap[];
	int size;
	//probabilities
	public Heap(int size){
		this.size = size;
		heap = new int[size];
		
		//populating the heap
		for (int i = 0; i < heap.length; i++) {
			heap[i] = (int) (Math.random() * 10);
		}
	}
	
}
