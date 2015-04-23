package shared_classes;

import java.io.Serializable;


public class Cell implements Serializable {

	private static final long serialVersionUID = 6305343068366336423L;

	public Ant ant;
	public Heap heap;

	public Cell() {
		ant = null;
		heap = null;
	}

	public Cell(Ant ant, Heap heap) {
		this.ant = ant;
		this.heap = heap;
	}

	// maybe we should have a GridElements class, and return an object of type GridElements
	public String getEntityType() {
		if (ant != null)
			return "ant";
		else if (heap != null)
			return "heap";
		else
			return "empty";
	}


	public Ant getAnt() {
		return ant;
	}

	public Heap getHeap() {
		// TODO Auto-generated method stub
		return heap;
	}
}
