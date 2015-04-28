package server.main;

import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import shared_classes.Ant;
import shared_classes.Board;
import shared_classes.Heap;
import GUI.GUI;

public class MainServer {

	private final int NUM_OF_LOOPS = 10000; // number of loops/steps to process
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

	private int currentAnts;
	private int currentHeaps;
	private int client_id;
	private float color = (float)client_id / (float)NUMBER_OF_PROCESSORS;
	private int width = 1024;
	private int height = 600;

	// use this function for processing some ants on the 1st remote
	// client/processor
	// (Hint: pass ants and grid object as parameters to the remote function)
	public synchronized void processAntsOnClient(String ipAddress, int portNumber, Ant ant, Board board) {
		try {
			// Registry rmiRegistry = LocateRegistry.getRegistry(ipAddress, portNumber); // fire to localhost port 1099
			// IMessage rmiMessage = (IMessage) rmiRegistry.lookup(IMessage.messageTag); // search for service
			//
			System.err.println("position before moving original: " + ant.getLocation().toString());
			// rmiMessage.remoteAntProcessor(ant, board); // call server's method ith parameters
			// ant.changeLocation(rmiMessage.getAnt().getLocation().getRow(), rmiMessage.getAnt().getLocation().getColumn());

			ant.move(board, board.getRows(), board.getColumns()); // TODO: this line is only for testing purposes!
			
			System.err.println("position after moving original: " + ant.getLocation().toString());

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
		mainServer.currentAnts = 0;
		mainServer.currentHeaps = 0;
		

		// Board initialization
		for (int i = 0; i < mainServer.boardSize; i++) {
			for (int j = 0; j < mainServer.boardSize; j++) {
				int randValue = (int) (Math.random() * 10);

				if (mainServer.currentAnts < mainServer.NUM_OF_ANTS && randValue > mainServer.ANT_PROBABILITY) {// ant
					Ant ant = new Ant(i, j, mainServer.color);
					System.out.print(mainServer.client_id +" "+ mainServer.color);
					mainServer.ants[mainServer.currentAnts++] = ant;
					mainServer.board.placeAnt(ant);
				} else if (mainServer.currentHeaps < mainServer.NUMBER_OF_INITIAL_HEAPS
						&& randValue < mainServer.HEAP_PROBABILITY) {// heap
					int hSize = (int) (Math.random() * mainServer.MAX_HEAP_SIZE + 1);
					Heap heap = new Heap(i, j, hSize, mainServer.TYPES_OF_OBJECTS);
					mainServer.board.placeHeap(heap);
					mainServer.heaps[mainServer.currentHeaps++] = heap;
				}
			}
		}
		

		//Assigning ants to two clients, creating different colors for better representation
		for (int i = 0; i < mainServer.currentAnts / mainServer.NUMBER_OF_PROCESSORS; ++i) {
			mainServer.client_id = 1;
			mainServer.color = (float)mainServer.client_id / (float)mainServer.NUMBER_OF_PROCESSORS;
			mainServer.ants[i].assignColor(mainServer.color);
		}
		for (int i = mainServer.currentAnts / mainServer.NUMBER_OF_PROCESSORS; i < mainServer.currentAnts; ++i) {
			mainServer.client_id = 2;
			mainServer.color = (float)mainServer.client_id / (float)mainServer.NUMBER_OF_PROCESSORS;
			mainServer.ants[i].assignColor(mainServer.color);
		}
		System.out.println("Initialized with " + mainServer.currentAnts + " ants and " + mainServer.currentHeaps + " heaps!");

		// create the GUI object
		JFrame frame = new JFrame("AntClass");
		frame.setSize(mainServer.width, mainServer.height);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUI gui = new GUI(mainServer.board, mainServer.boardSize, mainServer.boardSize, mainServer.width, mainServer.height);
		frame.add(gui);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		for (int i = 0; i < mainServer.NUM_OF_LOOPS; ++i) { // each step of this
															// loop is one cycle
															// to process each
															// ant

			// run the synchronizedFunction() in a separate thread
			Thread processor1 = new Thread() {
				public void run() {
					// for each ant allocated to the client #1
					for (int i = 0; i < mainServer.currentAnts / mainServer.NUMBER_OF_PROCESSORS; ++i) {
						// System.err.println("T#1: ant #" + i + " position before moving: "
						// + mainServer.ants[i].getLocation().toString());
						//mainServer.client_id = 1;
						mainServer.processAntsOnClient("127.0.0.1", 1099, mainServer.ants[i], mainServer.board);
						// System.err.println("T#1: ant #" + i + " position after moving: "
						// + mainServer.ants[i].getLocation().toString());
					}
				}
			};
			processor1.start();

			// run the synchronizedFunction() in another separate thread
			Thread processor2 = new Thread() {
				public void run() {
					// for each ant allocated to the client #2
					for (int i = mainServer.currentAnts / mainServer.NUMBER_OF_PROCESSORS; i < mainServer.currentAnts; ++i) {
						// System.err.println("T#2: ant's position before moving: " + mainServer.ants[i].getLocation().getRow()
						// + ", " + mainServer.ants[i].getLocation().getColumn());
						//mainServer.client_id = 2;
						mainServer.processAntsOnClient("127.0.0.1", 2099, mainServer.ants[i], mainServer.board);
						// System.err.println("T#2: ant's position after moving: " + mainServer.ants[i].getLocation().getRow()
						// + ", " + mainServer.ants[i].getLocation().getColumn());
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
			//Repainting the board after each ant processing iteration
			gui.repaint();

			// update the GUI with new positions of ants and objects
			// Note: the GUI class will be updating itself, no need to call any function here!!!
			synchronized (mainServer) { // lock the object to acquire the monitor
				try {
					mainServer.wait(200); // wait a bit to let the GUI update the screen
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} // end of the big for() loop

	} // end of the main() function

}