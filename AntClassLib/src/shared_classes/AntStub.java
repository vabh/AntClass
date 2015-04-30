package shared_classes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AntStub extends UnicastRemoteObject implements IRemoteAnt {

	private static final long serialVersionUID = 5500371245725480160L;

	private Ant[] ants;
	private int totalNumOfClients;
	private Board board;
	private GUI gui;

	public AntStub(Ant[] ants, int totalNumOfClients, Board board, GUI gui) throws RemoteException {
		this.ants = ants;
		this.totalNumOfClients = totalNumOfClients;
		this.board = board;
		this.gui = gui;
	}



	@Override
	public Ant getAnt(int antIndex) throws RemoteException {
		return ants[antIndex];
	}

	@Override
	public synchronized void changeLocation(Location location, int antIndex) throws RemoteException {
		ants[antIndex].getLocation().setColumn(location.getColumn());
		ants[antIndex].getLocation().setRow(location.getRow());
	}

	@Override
	public int getBoardWidth() throws RemoteException {
		return board.getColumns();
	}

	@Override
	public int getBoardHeight() throws RemoteException {
		return board.getRows();
	}

	@Override
	public int getTotalNumOfAnts() throws RemoteException {
		return ants.length;
	}

	@Override
	public int getTotalNumOfClients() throws RemoteException {
		return totalNumOfClients;
	}

	@Override
	public int getStartIndex(int clientID) throws RemoteException {
		// clientID should start from 0
		return clientID * (getTotalNumOfAnts() / getTotalNumOfClients());
	}

	@Override
	public int getEndIndex(int clientID) throws RemoteException {
		// clientID should start from 0
		if (clientID + 1 == getTotalNumOfClients()) { // check if this is the last client (i.e. with the highest ID)
			// assign until the last ant
			return getTotalNumOfAnts();
		} else {
			return (clientID + 1) * (getTotalNumOfAnts() / getTotalNumOfClients());
		}
	}

	@Override
	public Board getBoard() throws RemoteException {
		return this.board;
	}

	@Override
	public synchronized void requestRedraw() throws RemoteException {
		this.gui.repaint();
		try {
			wait(1000); // force waiting period to synchronise after each repaint
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// returns a nearby heap location if found any, otherwise returns null
	// index parameter is the index of the ant in the ants[] array which needs to look around itself
	@Override
	public synchronized Location lookAround(int index) throws RemoteException {
		Location resultLocation = new Location();

		// direction to look at
		int xPos[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int yPos[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

		Cell[][] cells = board.getBoardCells();
		int rows = board.getRows();
		int columns = board.getColumns();

		for (int i = 0; i < xPos.length; i++) {
			int lookatX = getAnt(index).getLocation().getRow() + xPos[i];
			int lookatY = getAnt(index).getLocation().getColumn() + yPos[i];
			if (lookatX >= 0) {
				lookatX %= rows;
			} else if (lookatX < 0) {
				lookatX = rows - 1;
			}

			if (lookatY >= 0) {
				lookatY %= columns;
			} else if (lookatY < 0) {
				lookatY = columns - 1;
			}
			if (cells[lookatX][lookatY] == null) {
				continue;
			}
			if (cells[lookatX][lookatY].getEntityType().equals("heap")) {
				resultLocation.setRow(lookatX);
				resultLocation.setColumn(lookatY);
				return resultLocation;
			}
		}
		return null;
	}
}
