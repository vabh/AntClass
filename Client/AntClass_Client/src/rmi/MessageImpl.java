package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageImpl extends UnicastRemoteObject implements IMessage {

	private static final long serialVersionUID = -7268072713058246914L;

	public MessageImpl() throws RemoteException {
	}

	@Override
	public void remoteAntProcessor(String param) throws RemoteException { // processes one single ant (received as a parameter)
		System.out.println("remoteSynchronizedFunction1() received parameter: " + param);


		// TODO: move the ant passed as a parameter


		// TODO: pick-up or drop an object


	}

	// @Override
	// public void remoteFunction2(String param) throws RemoteException {
	// System.out.println("remoteSynchronizedFunction2() received parameter: " + param);
	// while (true) { // do something useful instead ;)
	// ;
	// }
	// }
}