package server.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.IMessage;

public class MainServer {

	private final int NUM_OF_LOOPS = 10000; // number of loops/steps to process ants

	// TODO: declare the member variable for array of objects in order to have them locked while processing ants with sync methods



	// TODO: use this function for processing some ants on the 1st remote client/processor
	// (Hint: pass ants and grid object as parameters to the remote function)
	public synchronized void processAntsOnClient1() {
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099); // fire to localhost port 1099
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for service with the right message
			rmiMessage.remoteAntProcessor("param 1"); // call server's method with parameters
			System.out.println("IMessage Sent with param 1 to remoteAntProcessor()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO: use this function for processing some ants on the 2nd remote client/processor
	// (Hint: pass ants and grid object as parameters to the remote function)
	public synchronized void processAntsOnClient2() {
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", 2099); // fire to another localhost port 2099
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for myMessage service
			rmiMessage.remoteAntProcessor("param 1"); // call server's method with parameters
			System.out.println("IMessage Sent with param 1 to remoteAntProcessor()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final MainServer mainServer = new MainServer(); // object to use functions of this class

		// TODO: initialise the grid with objects and ants in it



		for (int i = 0; i < mainServer.NUM_OF_LOOPS; ++i) { // each step of this loop is one cycle to process each ant

			// run the synchronizedFunction1() in a separate thread
			Thread processor1 = new Thread() {
				public void run() {
					// TODO:
					// for each ant allocated to the client #1 do {
					mainServer.processAntsOnClient1();
					// }
				}
			};
			processor1.start();

			// run the synchronizedFunction2() in another separate thread
			Thread processor2 = new Thread() {
				public void run() {
					// TODO:
					// for each ant allocated to the client #2 do {
					mainServer.processAntsOnClient2();
					// }
				}
			};
			processor2.start();

			// wait for the processor1 and processor2 to finish their tasks
			try {
				processor1.join();
				processor2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			// TODO: update the GUI with new positions of ants and objects



		} // end of the big for() loop

	} // end of the main() function

}