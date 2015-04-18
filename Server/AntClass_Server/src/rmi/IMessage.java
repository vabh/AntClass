package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMessage extends Remote {
	public String messageTag = "MESSAGE_TAG";

	public void remoteFunction1(String name) throws RemoteException;

	public void remoteFunction2(String name) throws RemoteException;
}