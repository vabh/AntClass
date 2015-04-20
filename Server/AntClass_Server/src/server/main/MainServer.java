package server.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.Heap;
import shared_classes.IMessage;

public class MainServer {

	private final int NUM_OF_LOOPS = 10000; // number of loops/steps to process ants

	// TODO: declare the member variable for array of objects in order to have them locked while processing ants with sync methods
	private int boardSize = 10;
	private final int NUM_OF_ANTS = 8;
	private int MAX_HEAP_SIZE = 7;
	private int NUMBER_OF_INITIAL_HEAPS = 12;
	private int TYPES_OF_OBJECTS = 2;
	private final int NUMBER_OF_PROCESSORS = 2;
	private final Board board = new Board(boardSize, boardSize, TYPES_OF_OBJECTS, MAX_HEAP_SIZE);
	private final Ant ants[] = new Ant[NUM_OF_ANTS];
	private final Heap heaps[] = new Heap[NUMBER_OF_INITIAL_HEAPS];

	// TODO: use this function for processing some ants on the 1st remote client/processor
	// (Hint: pass ants and grid object as parameters to the remote function)
	public synchronized void processAntsOnClient(String ipAddress, int portNumber, Ant ant, Board board) {
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry(ipAddress, portNumber); // fire to localhost port 1099
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for service with the right message
			rmiMessage.remoteAntProcessor(ant, board); // call server's method with parameters
			System.out.println("IMessage Sent with param 1 to remoteAntProcessor()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final MainServer mainServer = new MainServer(); // object to use functions of this class

		Random rand = new Random();

		// TODO: initialise the grid with objects and ants in it
		mainServer.board.init();

		// init the heaps
		for (int i = 0; i < mainServer.NUMBER_OF_INITIAL_HEAPS; ++i) {
			// TODO: @Anuvabh
		}

		// init ants and pass them to the board to keep track of
		for (int i = 0; i < mainServer.NUM_OF_ANTS; ++i) {
			mainServer.ants[i] = new Ant(rand.nextInt(mainServer.boardSize), rand.nextInt(mainServer.boardSize));
			mainServer.board.placeAnt(mainServer.ants[i]);
		}

		for (int i = 0; i < mainServer.NUM_OF_LOOPS; ++i) { // each step of this loop is one cycle to process each ant

			// run the synchronizedFunction() in a separate thread
			Thread processor1 = new Thread() {
				public void run() {
					// for each ant allocated to the client #1
					for (int i = 0; i < mainServer.NUM_OF_ANTS / mainServer.NUMBER_OF_PROCESSORS; ++i) {
						mainServer.processAntsOnClient("127.0.0.1", 1099, mainServer.ants[i], mainServer.board);
					}
				}
			};
			processor1.start();

			// run the synchronizedFunction() in another separate thread
			Thread processor2 = new Thread() {
				public void run() {
					// for each ant allocated to the client #2
					for (int i = 0; i < mainServer.NUM_OF_ANTS / mainServer.NUMBER_OF_PROCESSORS; ++i) {
						mainServer.processAntsOnClient("127.0.0.1", 2099, mainServer.ants[i], mainServer.board);
					}
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