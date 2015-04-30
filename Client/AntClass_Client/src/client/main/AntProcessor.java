package client.main;

import java.util.LinkedList;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.Cell;
import shared_classes.Heap;
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

	public void processPickUpAlgorithm(Board board, Location heapLocation, Ant ant) {
		System.out.println("inside processPickUpAlgorithm() function Milestone #1");
		synchronized (board) {
			System.out.println("inside processPickUpAlgorithm() synchronized scope Milestone #2");
			// first, check if the heap is still there (because it may have disappeared because of other clients' ants)
			if (!board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityType().equalsIgnoreCase("heap")) {
				return; // do nothing and just exit the function
			}

			System.out.println("inside processPickUpAlgorithm() synchronized scope Milestone #3");

			Heap heap = (Heap) board.getCellEntity(heapLocation);
			LinkedList<Integer> heapElements = heap.getHeapElements();

			// TODO:
			switch (heapElements.size()) {
			case 1:
				System.out.println("inside case 1");
				ant.pickUp(heapElements.get(0));
				heapElements.remove(0);
				board.destroyHeap(heapLocation);
				break;
			case 2:
				System.out.println("inside case 2");
				ant.pickUp(heapElements.get(0));
				heapElements.remove(0); // TODO: adjust accordint to the .pdf reference document
				heap.updateHeap(heapElements);
				break;
			default:
				System.out.println("inside case 3");
				ant.pickUp(heapElements.get(0));
				heapElements.remove(0); // TODO: adjust accordint to the .pdf reference document as well
				heap.updateHeap(heapElements);
			}

		}
		System.out.println("after the synchronized block");
	}
}
