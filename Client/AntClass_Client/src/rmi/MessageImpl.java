package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.IMessage;
import shared_classes.Location;

public class MessageImpl extends UnicastRemoteObject implements IMessage {

	private static final long serialVersionUID = -7268072713058246914L;

	public MessageImpl() throws RemoteException {
	}

	@Override
	public void remoteAntProcessor(Ant ant, Board board) throws RemoteException { // processes one single ant (received as a
																					// parameter)

		// move the ant passed as a parameter
		ant.move(board.getRows(), board.getColumns());

		Location heapLocation = null;
		boolean isHeapFound = ant.lookAround(board, heapLocation);

		if (isHeapFound) {
			// pick-up or drop an object
			if (ant.isCarrying()) {
				ant.processDropAlgorithm();
			} else {
				ant.processPickUpAlgorithm();
			}
		}

		// TODO: @Anastasia: make it complicated please.

	}

	// @Override
	// public void remoteFunction2(String param) throws RemoteException {
	// System.out.println("remoteSynchronizedFunction2() received parameter: " + param);
	// while (true) { // do something useful instead ;)
	// ;
	// }
	// }
}