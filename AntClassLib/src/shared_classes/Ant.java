package shared_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Ant implements Serializable, CellEntity {

	private static final long serialVersionUID = -6819460915834657895L;

	private Location location;
	private boolean carrying;
	private int heapElement; // -1 when not carrying any object
	private float color;
	private int typesOfObjects;
	private int clientID;
	
	//Declare an Array of Colors
	private static Color[] ColorArray = {Color.black, Color.blue, Color.cyan, 		
		Color.green, Color.lightGray, Color.magenta, Color.darkGray, 
		Color.orange, Color.pink, Color.red, Color.gray, Color.yellow};

	public Ant(int r, int c, float _color, int _typesOfObjects) {
		System.out.println("ant born");
		location = new Location(r, c);
		this.carrying = false;
		this.heapElement = -1;
		this.color = _color;
		this.typesOfObjects = _typesOfObjects;

	}

	public boolean isCarrying() {
		return carrying;
	}

	public int getHeapElementType() {
		// -1 if ant is not carrying anything
		return heapElement;
	}

	public void drop() {
		carrying = false;
		heapElement = -1;
	}

	public void pickUp(int heapElement) {
		System.err.println("inside pickUp() with parameter " + heapElement);
		carrying = true;
		this.heapElement = heapElement;
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
		System.out.println("("+x+" ,"+y+")");
		System.out.println("("+location.getRow() +" ,"+location.getColumn()+")");
		//g.fillOval(x, y, 25, 25);
		g.fillOval((int) (x + (sizecol - getDrawingSize() * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
				(int) (y + (sizerow - getDrawingSize() * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
				(int) (getDrawingSize() * (0.02 * Math.min(sizecol, sizerow))),
				(int) (getDrawingSize() * (0.02 * Math.min(sizecol, sizerow))));
		
		//Drawing heap object on Ant if carrying
		if (carrying) {
			float colorheapvalue = (float) heapElement / (float) typesOfObjects;
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
		System.out.println(carrying + ":" + heapElement);
	}

}
