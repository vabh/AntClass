package shared_classes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMessage extends Remote {
	public String messageTag = "MESSAGE_TAG";

	public void remoteAntProcessor(Ant ant, Board board) throws RemoteException;

}