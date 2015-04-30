package client.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shared_classes.IRemoteAnt;
import shared_classes.Location;


public class MainClient {

	private final int clientID = 1; // should start from 0 and be less than the number of clients set in the server

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
					remoteAnts.changeLocation(
							antProc.move(remoteAnts.getAnt(index).getLocation(), remoteAnts.getBoardHeight(),
									remoteAnts.getBoardWidth(), remoteAnts.getBoard()), index);

					// look around of the ant[index]
					Location heapLocation = remoteAnts.lookAround(index);
					if (heapLocation != null) {
						// pick-up or drop an object
						if (remoteAnts.getAnt(index).isCarrying()) {
							antProc.processDropAlgorithm(remoteAnts.getBoard(), heapLocation, remoteAnts.getAnt(index));
						} else {
							antProc.processPickUpAlgorithm(remoteAnts.getBoard(), heapLocation, remoteAnts.getAnt(index));
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