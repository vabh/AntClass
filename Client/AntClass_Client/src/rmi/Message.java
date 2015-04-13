package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Message extends Remote {
	void sayHello(String name) throws RemoteException;
}