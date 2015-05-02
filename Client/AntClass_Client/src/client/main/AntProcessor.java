package client.main;

import java.rmi.RemoteException;
import java.util.LinkedList;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.Cell;
import shared_classes.Heap;
import shared_classes.IRemoteAnt;
import shared_classes.Location;

public class AntProcessor {

	public Location move(Location antLocation, int boardRows, int boardColumns, Board board) throws RemoteException {
		while (true) { // TODO: what if there's no way to go? (i.e. surrounded with the heaps and other ants)
			// System.err.println("yahoo!!!!!");
			Location oldLocation = antLocation;
			float x = (float) Math.random();
			float y = (float) Math.random();
			int r;
			int c;
			if (x >= 0.5) {
				r = (antLocation.getRow() + 1) % boardRows;
			} else {
				r = (antLocation.getRow() - 1 + boardRows) % boardRows;
			}
			if (y >= 0.5) {
				c = (antLocation.getColumn() + 1) % boardColumns;
			} else {
				c = (antLocation.getColumn() - 1 + boardColumns) % boardColumns;
			}

			// check if new cell contains a heap
			if (board.getCellObjectType(r, c).equalsIgnoreCase("empty")) {
				// antStub.destroyAnt(oldLocation);
				// Location newLocation = null;
				// newLocation.setRow(r);
				// newLocation.setColumn(c);
				// antStub.placeAntObject(r, c);

				return new Location(r, c);
			} else { // move in a new direction if we saw a heap it should not move on top of a heap
				continue;
			}
		}
	}

	// returns a nearby heap location if found any, otherwise returns null
	// index parameter is the index of the ant in the ants[] array which needs to look around itself
	public Location lookAround(Ant ant, Board board) {
		Location resultLocation = new Location();

		// direction to look at
		int xPos[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int yPos[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

		Cell[][] cells = board.getBoardCells();
		int rows = board.getRows();
		int columns = board.getColumns();

		for (int i = 0; i < xPos.length; i++) {
			int lookatX = ant.getLocation().getRow() + xPos[i];
			int lookatY = ant.getLocation().getColumn() + yPos[i];
			if (lookatX >= 0) {
				lookatX %= rows;
			} else if (lookatX < 0) {
				lookatX = rows - 1;
			}

			if (lookatY >= 0) {
				lookatY %= columns;
			} else if (lookatY < 0) {
				lookatY = columns - 1;
			}
			if (cells[lookatX][lookatY] == null) {
				continue;
			}
			if (cells[lookatX][lookatY].getEntityType().equals("heap")) {
				resultLocation.setRow(lookatX);
				resultLocation.setColumn(lookatY);
				return resultLocation;
			}
		}
		return null;
	}

	public void processDropAlgorithm(Board board, Location heapLocation, Ant ant, IRemoteAnt antStub) throws RemoteException {
		synchronized (board) {
			// first, check if the heap is still there (because it may have disappeared because of other clients' ants)
			if (!board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityType().equalsIgnoreCase("heap")) {
				return; // do nothing
			}

			Heap heap = (Heap) board.getCellEntity(heapLocation);
			LinkedList<Integer> heapElements = heap.getHeapElements();

			switch (heapElements.size()) {
			case 0: // TODO: according to the reference .pdf file, there should be such a case
				return;
			case 1:
				// TODO: apply the drop algorithm according to the reference .pdf file
				heapElements.add(ant.getHeapElementType());
				// ant.drop();
				antStub.updateHeap(heapLocation, heap); // update the heap object on the server side
				return;
			default:
				// TODO: apply the drop algorithm according to the reference .pdf file
				heapElements.add(ant.getHeapElementType());
				// ant.drop();
				antStub.updateHeap(heapLocation, heap); // update the heap object on the server side
				return;
			}

		}
	}

	// returns the type of the picked-up object
	public int processPickUpAlgorithm(Board board, Location heapLocation, Ant ant, IRemoteAnt antStub) throws RemoteException {
		synchronized (board) {
			// first, check if the heap is still there (because it may have disappeared because of other clients' ants)
			if (!board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityType().equalsIgnoreCase("heap")) {
				return -1; // return an empty object (carrying nothing)
			}

			Heap heap = (Heap) board.getCellEntity(heapLocation);
			LinkedList<Integer> heapElements = heap.getHeapElements();
			int element = -1; // return -1 if it is not carrying anything
			switch (heapElements.size()) {
			case 0: // nothing to do [but remove heap from board]
				antStub.destroyHeap(heapLocation);
				break;
			case 1:// pickup object, destroy heap
					// ant.pickUp(heapElements.get(0));
				element = heapElements.remove(0); // set type of object to be returned
				antStub.destroyHeap(heapLocation);
				// antStub.updateHeap(heapLocation, heap); // update the heap object on the server side
				break;
			case 2: // pickup object, update heap
				element = heapElements.remove(0); // TODO: adjust according to the .pdf reference document
													// set type of object to be returned
				heap.updateHeap(heapElements);
				antStub.updateHeap(heapLocation, heap); // update the heap object on the server side
				break; // return the type of the picked-up object
			default:
				element = heapElements.remove(0); // TODO: adjust according to the .pdf reference document as well

				heap.updateHeap(heapElements);
				antStub.updateHeap(heapLocation, heap); // update the heap object on the server side
				break;
			}
			// for (int i = 0; i < board.getRows(); i++) {
			// for (int j = 0; j < board.getColumns(); j++) {
			// System.out.print(board.getCellObjectType(i, j) + "--|");
			// }
			// System.out.println("");
			// }
			// System.out.println("___________________________________________________");
			return element; // return the type of the picked-up object

		}
	}

}
