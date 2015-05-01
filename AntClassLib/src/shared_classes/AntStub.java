package shared_classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AntStub extends UnicastRemoteObject implements IRemoteAnt {

	private static final long serialVersionUID = 5500371245725480160L;

	private Ant[] ants;
	private int totalNumOfClients;
	private Board board;
	private GUI gui;

	public AntStub(Ant[] ants, int totalNumOfClients, Board board, GUI gui) throws RemoteException {
		this.ants = ants;
		this.totalNumOfClients = totalNumOfClients;
		this.board = board;
		this.gui = gui;
	}



	@Override
	public Ant getAnt(int antIndex) throws RemoteException {
		return ants[antIndex];
	}

	@Override
	public synchronized void changeLocation(Location location, int antIndex) throws RemoteException {
		ants[antIndex].getLocation().setColumn(location.getColumn());
		ants[antIndex].getLocation().setRow(location.getRow());
	}

	@Override
	public void updateCarryingObject(int antIndex, int carryingObjectType) throws RemoteException {
		ants[antIndex].pickUp(carryingObjectType);
	}

	@Override
	public synchronized void updateHeap(Location heapLocation, Heap newHeap) throws RemoteException {
		// check if the heap is there
		if (board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].getEntityOnCell().getEntityType()
				.equalsIgnoreCase("heap")) {
			board.getBoardCells()[heapLocation.getRow()][heapLocation.getColumn()].setEntityOnCell(newHeap);
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
	public int getStartIndex(int clientID) throws RemoteException {
		// clientID should start from 0
		return clientID * (getTotalNumOfAnts() / getTotalNumOfClients());
	}

	@Override
	public int getEndIndex(int clientID) throws RemoteException {
		// clientID should start from 0
		if (clientID + 1 == getTotalNumOfClients()) { // check if this is the last client (i.e. with the highest ID)
			// assign until the last ant
			return getTotalNumOfAnts();
		} else {
			return (clientID + 1) * (getTotalNumOfAnts() / getTotalNumOfClients());
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
			wait(1000); // force waiting period to synchronise after each repaint
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
