package shared_classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AntStub extends UnicastRemoteObject implements IRemoteAnt {

	private static final long serialVersionUID = 5500371245725480160L;

	private Ant[] ants;
	private int boardColumns;
	private int boardRows;
	private int totalNumOfClients;

	public AntStub(Ant[] ants, int boardColumns, int boardRows, int totalNumOfClients) throws RemoteException {
		this.ants = ants;
		this.boardColumns = boardColumns;
		this.boardRows = boardRows;
		this.totalNumOfClients = totalNumOfClients;
	}

	@Override
	public Location getLocation(int antIndex) throws RemoteException {
		return ants[antIndex].getLocation();
	}

	@Override
	public synchronized void changeLocation(Location location, int antIndex) throws RemoteException {
		ants[antIndex].getLocation().setColumn(location.getColumn());
		ants[antIndex].getLocation().setRow(location.getRow());
	}

	@Override
	public int getBoardWidth() throws RemoteException {
		return this.boardColumns;
	}

	@Override
	public int getBoardHeight() throws RemoteException {
		return this.boardRows;
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
}
