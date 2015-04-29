package client.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shared_classes.IRemoteAnt;


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
					remoteAnts.changeLocation(
							antProc.move(remoteAnts.getLocation(index), remoteAnts.getBoardHeight(), remoteAnts.getBoardWidth()),
							index);
				}
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