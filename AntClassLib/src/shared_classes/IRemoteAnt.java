package shared_classes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteAnt extends Remote {
	public Ant getAnt(int antIndex) throws RemoteException;

	public void changeAntLocation(Location current, Location next, int antIndex) throws RemoteException;

	public void updateAntHeapObject(int antIndex, int carryingObjectType) throws RemoteException;

	public void updateHeapOnBoard(Location heapLocation, Heap newHeap) throws RemoteException;

	public void destroyHeapOnBoard(Location heapLocation) throws RemoteException;

	public void placeHeapOnBoard(Heap heap) throws RemoteException;

	public int getBoardWidth() throws RemoteException;

	public int getBoardHeight() throws RemoteException;

	public int getTotalNumOfAnts() throws RemoteException;

	public int getTotalNumOfClients() throws RemoteException;

	public int getStartIndexOfAnts(int clientID) throws RemoteException;

	public int getEndIndexOfAnts(int clientID) throws RemoteException;

	public Board getBoard() throws RemoteException;

	public void requestRedraw() throws RemoteException;

	public void assignColorToAnt(int antIndex, int clientID) throws RemoteException;

	public void assignAntsClientID(int antIndex, int clientID) throws RemoteException;

	public void destroyAntOnBoard(Location oldLocation) throws RemoteException;

	public void placeAntOnBoard(Ant ant) throws RemoteException;

	public void clientConnected(int clientID) throws RemoteException;

	public int getTypesOfHeapObjects() throws RemoteException;
}