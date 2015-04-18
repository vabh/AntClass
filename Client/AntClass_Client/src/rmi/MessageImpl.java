package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageImpl extends UnicastRemoteObject implements IMessage {

	private static final long serialVersionUID = -7268072713058246914L;

	public MessageImpl() throws RemoteException {
	}

	@Override
	public void remoteFunction1(String param) throws RemoteException {
		System.out.println("remoteSynchronizedFunction1() received parameter: " + param);
		while (true) { // do something useful instead ;)
			;
		}
	}

	@Override
	public void remoteFunction2(String param) throws RemoteException {
		System.out.println("remoteSynchronizedFunction2() received parameter: " + param);
		while (true) { // do something useful instead ;)
			;
		}
	}
}