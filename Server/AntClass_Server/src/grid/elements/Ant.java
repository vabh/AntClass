package grid.elements;

public class Ant {

	int x, y;
	//probabilities
	public Ant(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void move(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int[] getLocation(){
		int location[] = {x, y};
		return location;
	}
}
