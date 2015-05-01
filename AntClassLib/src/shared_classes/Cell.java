package shared_classes;

import java.io.Serializable;


public class Cell implements Serializable {

	private static final long serialVersionUID = 6305343068366336423L;

	private CellEntity cellEntity;

	public Cell(int r, int c) {
		cellEntity = new EmptyCellEntity(r, c);
	}

	public String getEntityType() {
		return cellEntity.getEntityType();
	}

	public CellEntity getEntityOnCell() {
		return cellEntity;
	}

	public void setEntityOnCell(CellEntity cellEntity) {
		this.cellEntity = null;
		this.cellEntity = cellEntity;
	}

}
