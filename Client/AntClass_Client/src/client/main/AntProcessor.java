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
			float hDirection = (float) Math.random();
			float vDirection = (float) Math.random();
			int r;
			int c;
			if (hDirection >= 0.5) {
				r = (antLocation.getRow() + 1) % boardRows;
			} else {
				r = (antLocation.getRow() - 1 + boardRows) % boardRows;
			}
			if (vDirection >= 0.5) {
				c = (antLocation.getColumn() + 1) % boardColumns;
			} else {
				c = (antLocation.getColumn() - 1 + boardColumns) % boardColumns;
			}

			// check if new cell contains a heap
			if (board.getCellObjectType(r, c).equalsIgnoreCase("empty")) {
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
		int hDirection[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int vDirection[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

		Cell[][] cells = board.getBoardCells();
		int rows = board.getRows();
		int columns = board.getColumns();

		for (int i = 0; i < hDirection.length; i++) {
			int lookatH = ant.getLocation().getRow() + hDirection[i];
			int lookatV = ant.getLocation().getColumn() + vDirection[i];
			if (lookatH >= 0) {
				lookatH %= rows;
			} else if (lookatH < 0) {
				lookatH = rows - 1;
			}

			if (lookatV >= 0) {
				lookatV %= columns;
			} else if (lookatV < 0) {
				lookatV = columns - 1;
			}
			if (cells[lookatH][lookatV].getEntityType().equals("heap")) {
				resultLocation.setRow(lookatH);
				resultLocation.setColumn(lookatV);
				return resultLocation;
			} else {
				continue;
			}
		}
		return null;
	}

	public Location lookAroundForEmpty(Ant ant, Board board) {
		Location resultLocation = new Location();

		// direction to look at
		int hDirection[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int vDirection[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

		Cell[][] cells = board.getBoardCells();
		int rows = board.getRows();
		int columns = board.getColumns();

		for (int i = 0; i < hDirection.length; i++) {
			int lookatH = ant.getLocation().getRow() + hDirection[i];
			int lookatV = ant.getLocation().getColumn() + vDirection[i];
			if (lookatH >= 0) {
				lookatH %= rows;
			} else if (lookatH < 0) {
				lookatH = rows - 1;
			}

			if (lookatV >= 0) {
				lookatV %= columns;
			} else if (lookatV < 0) {
				lookatV = columns - 1;
			}
			if (cells[lookatH][lookatV].getEntityType().equals("empty")) {
				resultLocation.setRow(lookatH);
				resultLocation.setColumn(lookatV);
				return resultLocation;
			} else {
				continue;
			}
		}
		return null;
	}

	public void processDropAlgorithm(Board board, Location heapLocation, Ant ant, IRemoteAnt antStub) throws RemoteException {
		synchronized (board) {
			// first, check if the heap is still there (because it may have disappeared because of other clients' ants)
			if (board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityType().equalsIgnoreCase("empty")) {

				// drop on EmptyCell
				Heap heap = new Heap(heapLocation.getRow(), heapLocation.getColumn(), antStub.getTypesOfHeapObjects());
				LinkedList<Integer> heapObjects = new LinkedList<Integer>();
				heapObjects.add(ant.getHeapElementType());
				heap.updateHeapObjects(heapObjects);
				antStub.placeHeapOnBoard(heap);
				return;
			}

			Heap heap = (Heap) board.getCellEntity(heapLocation);
			LinkedList<Integer> heapObjects = heap.getHeapObjects();

			switch (heapObjects.size()) {
			case 0: // TODO: according to the reference .pdf file, there should be such a case
				return;
			case 1:
				// TODO: apply the drop algorithm according to the reference .pdf file
				heapObjects.add(ant.getHeapElementType());
				// ant.drop();
				antStub.updateHeapOnBoard(heapLocation, heap); // update the heap object on the server side
				return;
			default:
				// TODO: apply the drop algorithm according to the reference .pdf file
				heapObjects.add(ant.getHeapElementType());
				// ant.drop();
				antStub.updateHeapOnBoard(heapLocation, heap); // update the heap object on the server side
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
			LinkedList<Integer> heapObjects = heap.getHeapObjects();
			int heapObjectElement = -1; // return -1 if it is not carrying anything
			switch (heapObjects.size()) {
			case 0: // nothing to do [but remove heap from board]
				antStub.destroyHeapOnBoard(heapLocation);
				break;
			case 1:// pickup object, destroy heap
					// ant.pickUp(heapElements.get(0));
				heapObjectElement = heapObjects.remove(0); // set type of object to be returned
				antStub.destroyHeapOnBoard(heapLocation);
				// antStub.updateHeap(heapLocation, heap); // update the heap object on the server side
				break;
			case 2: // pickup object, update heap
				heapObjectElement = heapObjects.remove(0); // TODO: adjust according to the .pdf reference document
													// set type of object to be returned
				heap.updateHeapObjects(heapObjects);
				antStub.updateHeapOnBoard(heapLocation, heap); // update the heap object on the server side
				break; // return the type of the picked-up object
			default:
				heapObjectElement = heapObjects.remove(0); // TODO: adjust according to the .pdf reference document as well

				heap.updateHeapObjects(heapObjects);
				antStub.updateHeapOnBoard(heapLocation, heap); // update the heap object on the server side
				break;
			}
			// for (int i = 0; i < board.getRows(); i++) {
			// for (int j = 0; j < board.getColumns(); j++) {
			// System.out.print(board.getCellObjectType(i, j) + "--|");
			// }
			// System.out.println("");
			// }
			// System.out.println("___________________________________________________");
			return heapObjectElement; // return the type of the picked-up object

		}
	}

}
