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
	public Location move(Location antLocation, int boardRows, int boardColumns, Board board) {
		while (true) { // TODO: what if there's no way to go? (i.e. surrounded with the heaps and other ants)
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
				return new Location(c, r);
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

	public void processDropAlgorithm(Board board, Location heapLocation, Ant ant) {
		synchronized (board) {
			Heap heap = (Heap) board.getCellEntity(heapLocation);
			LinkedList<Integer> heapElements = heap.getHeapElements();

			// TODO:

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

			switch (heapElements.size()) {
			case 1:
				ant.pickUp(heapElements.get(0));
				heapElements.remove(0);
				board.destroyHeap(heapLocation);
				antStub.updateHeap(heapLocation, heap); // update the ant object on the server side
				return heapElements.get(0); // return the type of the picked-up object
			case 2:
				heapElements.remove(0); // TODO: adjust according to the .pdf reference document

				heap.updateHeap(heapElements);
				antStub.updateHeap(heapLocation, heap); // update the ant object on the server side
				return heapElements.get(0); // return the type of the picked-up object
			default:
				heapElements.remove(0); // TODO: adjust according to the .pdf reference document as well

				heap.updateHeap(heapElements);
				antStub.updateHeap(heapLocation, heap); // update the ant object on the server side
				return heapElements.get(0); // return the type of the picked-up object
			}
		}
	}
}
