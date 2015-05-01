package server.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

import shared_classes.Ant;
import shared_classes.AntStub;
import shared_classes.Board;
import shared_classes.GUI;
import shared_classes.Heap;

public class MainServer {
	// declare the member variable for array of objects in order to have
	// them locked while processing ants with sync methods
	private int boardSize = 10;
	private final int NUM_OF_ANTS = 5;
	private int MAX_HEAP_SIZE = 3;
	private int NUMBER_OF_INITIAL_HEAPS = 3;
	private int TYPES_OF_OBJECTS = 2;
	private final int NUMBER_OF_PROCESSORS = 1;

	private final int ANT_PROBABILITY = 5;
	private final int HEAP_PROBABILITY = 2;

	private final Board board = new Board(boardSize, boardSize, TYPES_OF_OBJECTS, MAX_HEAP_SIZE);
	private final Ant ants[] = new Ant[NUM_OF_ANTS];
	private final Heap heaps[] = new Heap[NUMBER_OF_INITIAL_HEAPS];

	private int currentAnts;
	private int currentHeaps;
	private int client_id;
	private float color = 0;
	// (float) client_id / (float) NUMBER_OF_PROCESSORS;
	private int width = 1024;
	private int height = 600;

	public static void main(String[] args) {
		final MainServer mainServer = new MainServer(); // object to use
														// functions of this
														// class

		// initialise the grid with objects and ants in it
		mainServer.currentAnts = 0;
		mainServer.currentHeaps = 0;

		int counter = mainServer.NUM_OF_ANTS;
		while (counter > 0) {
			int randValue = (int) (Math.random() * 10);
			if (randValue < mainServer.ANT_PROBABILITY) {
				continue;
			}

			int r = (int) (Math.random() * mainServer.boardSize);
			int c = (int) (Math.random() * mainServer.boardSize);

			if (mainServer.board.getCellObjectType(r, c).equals("empty")) {
				Ant ant = new Ant(r, c, mainServer.color, mainServer.TYPES_OF_OBJECTS);
				System.out.print(mainServer.client_id + " " + mainServer.color);
				mainServer.ants[mainServer.currentAnts++] = ant;
				mainServer.board.placeAnt(ant);
				counter--;
			}
		}

		counter = mainServer.NUMBER_OF_INITIAL_HEAPS;
		while (counter > 0) {
			int randValue = (int) (Math.random() * 10);
			if (randValue < mainServer.HEAP_PROBABILITY) {
				continue;
			}

			int r = (int) (Math.random() * mainServer.boardSize);
			int c = (int) (Math.random() * mainServer.boardSize);

			if (mainServer.board.getCellObjectType(r, c).equals("empty")) {
				int hSize = (int) (Math.random() * mainServer.MAX_HEAP_SIZE + 1);
				Heap heap = new Heap(r, c, hSize, mainServer.TYPES_OF_OBJECTS);
				mainServer.board.placeHeap(heap);
				mainServer.heaps[mainServer.currentHeaps++] = heap;
				counter--;
			}
		}

		System.out.println("Initialized with " + mainServer.currentAnts + " ants and " + mainServer.currentHeaps + " heaps!");


		for (int i = 0; i < mainServer.boardSize; i++) {
			for (int j = 0; j < mainServer.boardSize; j++) {
				System.out.print(mainServer.board.getCellObjectType(i, j) + "--|");
			}
			System.out.println();
		}
		for (int i = 0; i < mainServer.ants.length; i++) {
			System.out.println(mainServer.ants[i].getLocation());
		}
		// create the GUI object
		JFrame frame = new JFrame("AntClass");
		frame.setSize(mainServer.width, mainServer.height);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUI gui = new GUI(mainServer.board, mainServer.boardSize, mainServer.boardSize, mainServer.width, mainServer.height);
		frame.add(gui);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// create the register and the shared/remote object being accessed from the clients
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			AntStub antsStub = new AntStub(mainServer.ants, mainServer.NUMBER_OF_PROCESSORS, mainServer.board, gui);
			registry.rebind("remoteAnt", antsStub);
			System.out.println("registry created and remote object rebound");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	} // end of the main() function

}