package shared_classes;

import java.awt.Graphics;

public interface CellEntity {
	// private static final long serialVersionUID = -3600230737857993262L;

	// protected Location location;

	// public CellEntity(Location location) {
	// this.location = new Location(location.getRow(), location.getColumn());
	// }

	public Location getLocation();

	public void changeLocation(int row, int col);

	public String getEntityType();

	public int getDrawingSize();

	public void drawOnBoard(Graphics g, int sizecol, int sizerow, Board board);
}
