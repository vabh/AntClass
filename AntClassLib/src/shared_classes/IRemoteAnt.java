package shared_classes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteAnt extends Remote {
	public Location getLocation(int antIndex) throws RemoteException;

	public void changeLocation(Location location, int antIndex) throws RemoteException;

	public int getBoardWidth() throws RemoteException;

	public int getBoardHeight() throws RemoteException;

	public int getTotalNumOfAnts() throws RemoteException;

	public int getTotalNumOfClients() throws RemoteException;

	public int getStartIndex(int clientID) throws RemoteException;

	public int getEndIndex(int clientID) throws RemoteException;

	public Board getBoard() throws RemoteException;

	public void requestRedraw() throws RemoteException;
}