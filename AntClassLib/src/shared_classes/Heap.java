package shared_classes;

public class Heap {
	
	private int r; //location on board
	private int c;
	private Location location;
	private int elements[];
	private int size;
	private int types; // the different types of heal elements
	
	
	//probabilities for heap operation
	
	public Heap(int r, int c, int size, int types){
		this.r = r;
		this.c = c;
		location = new Location(r, c);
		this.size = size;
		this.types = types;
		elements = new int[size];
		
		//populating the heap
		for (int i = 0; i < elements.length; i++) {
			elements[i] = (int) (Math.random() * types); 
		}
	}
	
	public int getSize(){
		return size;
	}
	
	public int[] getLocationArray(){
		int location[] = {r, c};
		return location;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public int[] getHeapElements(){
		return elements;
	}
	
	public int getTypes(){
		return types;
	}
	
	//modified after the pickup and dropping operations
	public void updateHeap(int heap[]){
		this.size = heap.length;
		this.elements = new int[size];
		for (int i = 0; i < heap.length; i++) {
			this.elements[i] = heap[i];			
		}
	}
}
