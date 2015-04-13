package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageImpl extends UnicastRemoteObject implements IMessage {

	private static final long serialVersionUID = -7268072713058246914L;

	public MessageImpl() throws RemoteException {

	}

	@Override
	public void sayHello(String name) throws RemoteException {
		System.out.println("hello " + name);
	}

}