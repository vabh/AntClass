package grid.elements;

public class Ant {

	int r, c;
	int id;
	
	boolean carrying;
	int heapElement;
	//probabilities
	
	public Ant(int r, int c){
		this.r = r;
		this.c = c;
		
		this.carrying = false;
		int heapElement = -1;
	}
	
	public boolean isCarrying(){
		return carrying;
	}
	
	public int getHeapElementType(){
		//-1 if ant is not carrying anything
		return heapElement;
	}
	public void drop(){
		carrying = false;
		heapElement = -1;
	}
	
	public void pickUp(int heapElement){
		carrying = true;
		this.heapElement = heapElement;
	}
	
	//this method should be called to make the ant move
	//called with board size, to make movement toroidal
	public void move(int rows, int columns){
		float x = (float) Math.random();
		float y = (float) Math.random();
		
		if( x >= 0.5)
			r = (r + 1) % rows;
		else{
			r -= 1;
			if(r < 0)
				r = rows - 1;
		}
		
		if( y >= 0.5)
			c = (c + 1) % columns;
		else{
			c -= 1;
			if(r < 0)
				r = columns - 1;
		}		
	}

	public int[] getLocation(){
		int location[] = {r, c};
		return location;
	}
}
