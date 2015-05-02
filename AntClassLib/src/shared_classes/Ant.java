package shared_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Ant implements Serializable, CellEntity {

	private static final long serialVersionUID = -6819460915834657895L;

	private Location location;
	private boolean isCarrying;
	private int carriedHeapObject; // -1 when not isCarrying any object
	private float color;
	private int totalNumberOfTypesOfHeapObjects;
	private int clientID;
	
	//Declare an Array of Colors
	private static Color[] ColorArray = {Color.black, Color.blue, Color.cyan, 		
		Color.green, Color.lightGray, Color.magenta, Color.darkGray, 
		Color.orange, Color.pink, Color.red, Color.gray, Color.yellow};

	public Ant(int r, int c, float _color, int _typesOfObjects) {
		//System.out.println("ant born");
		location = new Location(r, c);
		this.isCarrying = false;
		this.carriedHeapObject = -1;
		this.color = _color;
		this.totalNumberOfTypesOfHeapObjects = _typesOfObjects;

	}
	public Ant(int r, int c){
		location = new Location(r, c);
		this.isCarrying = false;
		this.carriedHeapObject = -1;
	};

	public boolean isCarrying() {
		return isCarrying;
	}

	public int getHeapElementType() {
		// -1 if ant is not isCarrying anything
		return carriedHeapObject;
	}

	public void dropCarriedObject() {
		isCarrying = false;
		carriedHeapObject = -1;
	}

	public void pickUp(int heapElement) {
		//System.err.println("inside pickUp() with parameter " + carriedHeapObject);
		isCarrying = true;
		this.carriedHeapObject = heapElement;
	}

	@Override
	public String getEntityType() {
		return "ant";
	}

	@Override
	public int getDrawingSize() {
		return 35;
	}

	@Override
	public void drawOnBoard(Graphics g, int sizecol, int sizerow, Board board) {

		if (clientID < 12) {
			g.setColor(ColorArray[clientID]);
		} else {
			Color colorAnt = new Color((int) ((0.7) * 255), (int) ((color) * 255), (int) ((color/2.0) * 255));
			g.setColor(colorAnt);
		}
		int y = getLocation().getRow() * sizerow;
		int x = getLocation().getColumn() * sizecol;
		//System.out.println("("+x+" ,"+y+")");
		//System.out.println("("+location.getRow() +" ,"+location.getColumn()+")");
		//g.fillOval(x, y, 25, 25);
		g.fillOval((int) (x + (sizecol - getDrawingSize() * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
				(int) (y + (sizerow - getDrawingSize() * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
				(int) (getDrawingSize() * (0.02 * Math.min(sizecol, sizerow))),
				(int) (getDrawingSize() * (0.02 * Math.min(sizecol, sizerow))));
		
		//Drawing heap object on Ant if isCarrying
		if (isCarrying) {
			float colorheapvalue = (float) carriedHeapObject / (float) totalNumberOfTypesOfHeapObjects;
			Color colorHeap = new Color((int) ((colorheapvalue) * 255), (int) ((colorheapvalue) * 255), (int) ((0.7) * 255));
			g.setColor(colorHeap);
			g.fillRect((int) (x + (sizecol - getDrawingSize() / 2 * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
					(int) (y + (sizerow - getDrawingSize() / 2 * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
					(int) (getDrawingSize() / 2 * (0.02 * Math.min(sizecol, sizerow))),
					(int) (getDrawingSize() / 2 * (0.02 * Math.min(sizecol, sizerow))));
			g.setColor(Color.white);
			g.drawRect((int) (x + (sizecol - getDrawingSize() / 2 * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
					(int) (y + (sizerow - getDrawingSize() / 2 * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
					(int) (getDrawingSize() / 2 * (0.02 * Math.min(sizecol, sizerow))),
					(int) (getDrawingSize() / 2 * (0.02 * Math.min(sizecol, sizerow))));
		}
	}

	public void assignColor(float _color) {
		color = _color;
	}
	
	public void assignAntsClientID(int _clientID){
		clientID = _clientID;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void changeLocation(int row, int col) {
		this.location.setColumn(col);
		this.location.setRow(row);
	}

	public void printStatus() {
		//System.out.println(isCarrying + ":" + carriedHeapObject);
	}

}
