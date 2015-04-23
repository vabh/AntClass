package server.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.Heap;
import shared_classes.IMessage;
import GUI.GUI;

public class MainServer {

	private final int NUM_OF_LOOPS = 10; // number of loops/steps to process
											// ants

	// declare the member variable for array of objects in order to have
	// them locked while processing ants with sync methods
	private int boardSize = 10;
	private final int NUM_OF_ANTS = 5;
	private int MAX_HEAP_SIZE = 10;
	private int NUMBER_OF_INITIAL_HEAPS = 10;
	private int TYPES_OF_OBJECTS = 3;
	private final int NUMBER_OF_PROCESSORS = 2;

	private final int ANT_PROBABILITY = 5;
	private final int HEAP_PROBABILITY = 2;

	private final Board board = new Board(boardSize, boardSize, TYPES_OF_OBJECTS, MAX_HEAP_SIZE);
	private final Ant ants[] = new Ant[NUM_OF_ANTS];
	private final Heap heaps[] = new Heap[NUMBER_OF_INITIAL_HEAPS];

	private static int currentAnts;
	private static int currentHeaps;

	// use this function for processing some ants on the 1st remote
	// client/processor
	// (Hint: pass ants and grid object as parameters to the remote function)
	public synchronized void processAntsOnClient(String ipAddress, int portNumber, Ant ant, Board board) {
		try {
			Registry rmiRegistry = LocateRegistry.getRegistry(ipAddress, portNumber); // fire to localhost port 1099
			IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for service with the
																						// right message
			rmiMessage.remoteAntProcessor(ant, board); // call server's method
														// with parameters
			System.out.println("IMessage Sent with param 1 to remoteAntProcessor()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final MainServer mainServer = new MainServer(); // object to use
														// functions of this
														// class

		// initialise the grid with objects and ants in it
		mainServer.board.init();
		currentAnts = 0;
		currentHeaps = 0;

		// create the GUI object
		int w = 1024;
		int h = 600;
		JFrame frame = new JFrame("AntClass");
		frame.setSize(w, h);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUI gui = new GUI(mainServer.board, mainServer.boardSize, mainServer.boardSize, w, h);
		frame.add(gui);
		frame.setVisible(true);

		// initialise board
		for (int i = 0; i < mainServer.boardSize; i++) {
			for (int j = 0; j < mainServer.boardSize; j++) {
				int r = (int) (Math.random() * 10);

				if (currentAnts < mainServer.NUM_OF_ANTS && r > mainServer.ANT_PROBABILITY) {// ant
					Ant ant = new Ant(i, j);
					mainServer.board.placeAnt(ant);
					mainServer.ants[currentAnts++] = ant;

				} else if (currentHeaps < mainServer.NUMBER_OF_INITIAL_HEAPS && r < mainServer.HEAP_PROBABILITY) {// heap
					int hSize = (int) (Math.random() * mainServer.MAX_HEAP_SIZE + 1);
					Heap heap = new Heap(i, j, hSize, mainServer.TYPES_OF_OBJECTS);
					mainServer.board.placeHeap(heap);
					mainServer.heaps[currentHeaps++] = heap;

				}
			}
		}

		for (int i = 0; i < mainServer.NUM_OF_LOOPS; ++i) { // each step of this
															// loop is one cycle
															// to process each
															// ant

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

			// update the GUI with new positions of ants and objects
			mainServer.updateGUI(gui);

		} // end of the big for() loop

	} // end of the main() function

	private void updateGUI(GUI gui) {
		for (Ant ant : ants) {
			ant.move(boardSize, boardSize);
		}
		gui.setUpdatedBoard(board);
	}
}