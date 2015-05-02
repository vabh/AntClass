package client.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shared_classes.IRemoteAnt;
import shared_classes.Location;


public class MainClient {

	private final int clientID = 0; // should start from 0 and be less than the number of clients set in the server

	private void start() {
		try {
			// connecting to a remote host and looking up for a remote object created by it (remote host)
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
			IRemoteAnt remoteAnts = (IRemoteAnt) registry.lookup("remoteAnt");

			AntProcessor antProc = new AntProcessor();

			while (true) { // loop forever
				int startIndex = remoteAnts.getStartIndex(clientID);
				int endIndex = remoteAnts.getEndIndex(clientID);
				for (int index = startIndex; index < endIndex; ++index) {
					// move the ant[index]
					
					Location current = remoteAnts.getAnt(index).getLocation();
					Location next = antProc.move(current, remoteAnts.getBoardHeight(), remoteAnts.getBoardWidth(), remoteAnts.getBoard());
					remoteAnts.changeLocation(current, next, index);							

					remoteAnts.getAnt(index).printStatus();

					// look around of the ant[index]
					Location heapLocation = antProc.lookAround(remoteAnts.getAnt(index), remoteAnts.getBoard());
					// Assigning color to Ant depending on the client ID
					remoteAnts.assignColor(index, clientID);
					remoteAnts.assignAntsClientID(index, clientID);
					if (heapLocation != null) {
						// pick-up or drop an object
						if (remoteAnts.getAnt(index).isCarrying()) {
							// drop the carrying object according to the drop algorithm (do the heavy computation on client side)
							// the heap will be updated on the server side, that's why passing remoteAnts stub object here
							antProc.processDropAlgorithm(remoteAnts.getBoard(), heapLocation, remoteAnts.getAnt(index),
									remoteAnts);

							// update the ant object on the server side
							remoteAnts.updateCarryingObject(index, -1);
						} else {
							// find the pickup object (do the heavy processing on client side), heap update will be called inside
							// pickup algorithm but again on client side, that's why passing remoteAnts stub object here
							int pickupObject = antProc.processPickUpAlgorithm(remoteAnts.getBoard(), heapLocation,
									remoteAnts.getAnt(index), remoteAnts);

							// update the ant object on the server side
							remoteAnts.updateCarryingObject(index, pickupObject);
						}
					}

				}
				remoteAnts.requestRedraw();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Client finished!");
	}

	public static void main(String[] args) {
		MainClient main = new MainClient();
		main.start();
	}
}