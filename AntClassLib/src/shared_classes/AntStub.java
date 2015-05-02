package shared_classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AntStub extends UnicastRemoteObject implements IRemoteAnt {

	private static final long serialVersionUID = 5500371245725480160L;

	private Ant[] ants;
	private int totalNumOfClients;
	private static int numOfActiveClients;
	private Board board;
	private GUI gui;
	private int clientID;
	
	private int typesOfHeapObjects;

	public AntStub(Ant[] ants, int totalNumOfClients, Board board, GUI gui, int typesOfHeapObjects) throws RemoteException {
		this.ants = ants;
		this.totalNumOfClients = totalNumOfClients;
		this.board = board;
		this.gui = gui;
		AntStub.numOfActiveClients = 0;
		this.typesOfHeapObjects = typesOfHeapObjects;
	}

	@Override
	public Ant getAnt(int antIndex) throws RemoteException {
		return ants[antIndex];
	}

	@Override
	public synchronized void changeAntLocation(Location current, Location next, int antIndex) throws RemoteException {
		destroyAntOnBoard(current);
		ants[antIndex].getLocation().setColumn(next.getColumn());
		ants[antIndex].getLocation().setRow(next.getRow());
		placeAntOnBoard(ants[antIndex]);

	}

	@Override
	public void updateAntHeapObject(int antIndex, int carryingObjectType) throws RemoteException {
		if (carryingObjectType == -1) {
			ants[antIndex].dropCarryingHeapObject();
		} else {
			ants[antIndex].pickUpHeapObject(carryingObjectType);
		}
	}

	@Override
	public synchronized void updateHeapOnBoard(Location heapLocation, Heap newHeap) throws RemoteException {
		// check if the heap is there
		if (board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityOnCell().getEntityType()
				.equalsIgnoreCase("heap")) {
			board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].setEntityOnCell(newHeap);
		}
	}

	/**
	 * heapLocation is a parameter for heap location
	 */
	@Override
	public void destroyHeapOnBoard(Location heapLocation) throws RemoteException {
		// check if the heap is there
		if (board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityOnCell().getEntityType()
				.equalsIgnoreCase("heap")) {
			board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].setEntityOnCell(new EmptyCellEntity(
					heapLocation.getRow(), heapLocation.getColumn()));
		}
	}

	public void placeHeapOnBoard(Heap heap) throws RemoteException {
		// check if the heap is there
		Location heapLocation = heap.getLocation();
		if (board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityOnCell().getEntityType()
				.equalsIgnoreCase("empty")) {
			board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].setEntityOnCell(heap);
		}
	}

	@Override
	public int getBoardWidth() throws RemoteException {
		return board.getColumns();
	}

	@Override
	public int getBoardHeight() throws RemoteException {
		return board.getRows();
	}

	@Override
	public int getTotalNumOfAnts() throws RemoteException {
		return ants.length;
	}

	@Override
	public int getTotalNumOfClients() throws RemoteException {
		return totalNumOfClients;
	}

	@Override
	public int getStartIndexOfAnts(int clientID) throws RemoteException {
		// clientID should start from 0
		// return clientID * (getTotalNumOfAnts() / getTotalNumOfClients());
		return clientID * (getTotalNumOfAnts() / numOfActiveClients);
	}

	@Override
	public int getEndIndexOfAnts(int clientID) throws RemoteException {
		// clientID should start from 0
		// if (clientID + 1 == getTotalNumOfClients()) { // check if this is the last client (i.e. with the highest ID)
		if (clientID + 1 == numOfActiveClients) { // check if this is the last client (i.e. with the highest ID)
			// assign until the last ant
			return getTotalNumOfAnts();
		} else {
			// return (clientID + 1) * (getTotalNumOfAnts() / getTotalNumOfClients());
			return (clientID + 1) * (getTotalNumOfAnts() / numOfActiveClients);
		}
	}

	@Override
	public Board getBoard() throws RemoteException {
		return this.board;
	}

	@Override
	public synchronized void requestRedraw() throws RemoteException {
		this.gui.repaint();
		try {
			wait(900); // force waiting period to synchronise after each repaint
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void assignColorToAnt(int antIndex, int _clientID) throws RemoteException {
		this.clientID = _clientID;
		// ants[antIndex].assignColor((float) clientID / (float) totalNumOfClients);
		ants[antIndex].assignColor((float) clientID / (float) numOfActiveClients);
	}

	@Override
	public void assignAntsClientID(int antIndex, int clientID) throws RemoteException {
		ants[antIndex].assignAntsClientID(clientID);
	}

	@Override
	public void destroyAntOnBoard(Location oldLocation) throws RemoteException {
		if (board.getBoardCells()[oldLocation.getRow()][oldLocation.getColumn()].getEntityOnCell().getEntityType()
				.equalsIgnoreCase("ant")) {
			board.getBoardCells()[oldLocation.getRow()][oldLocation.getColumn()].setEntityOnCell(new EmptyCellEntity(oldLocation
					.getRow(), oldLocation.getColumn()));
		}

	}

	@Override
	public void placeAntOnBoard(Ant ant) throws RemoteException {
		int r = ant.getLocation().getRow();
		int c = ant.getLocation().getColumn();
		if (board.getBoardCells()[r][c].getEntityOnCell().getEntityType().equalsIgnoreCase("empty")) {
			board.getBoardCells()[r][c].setEntityOnCell(ant);
		}

	}

	@Override
	public void clientConnected(int clientID) throws RemoteException {
		++numOfActiveClients;
	}

	@Override
	public int getTypesOfHeapObjects() throws RemoteException {
		return typesOfHeapObjects;
	}

}
