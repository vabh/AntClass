package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.IMessage;
import shared_classes.Location;

public class MessageImpl extends UnicastRemoteObject implements IMessage {

	private static final long serialVersionUID = -7268072713058246914L;

	private Ant _ant;

	public MessageImpl() throws RemoteException {
	}

	@Override
	public Ant getAnt() throws RemoteException {
		return _ant;
	}

	@Override
	public void remoteAntProcessor(Ant ant, Board board) throws RemoteException { // processes one single ant
																					// (received as a
		// parameter)

		// System.err.println("moving an ant from position " + ant.getLocation().getRow() + ", " + ant.getLocation().getColumn());
		ant.move(board.getRows(), board.getColumns()); // move the ant passed as a parameter
		// System.err.println("the new position of ant " + ant.getLocation().getRow() + ", " + ant.getLocation().getColumn());

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

		this._ant = ant; // new Ant(ant.getLocation().getRow(), ant.getLocation().getColumn());

		// TODO: @Anastasia: make it complicated please.

	}

}