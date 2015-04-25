package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.Timer;

import shared_classes.Board;
import shared_classes.Cell;
import shared_classes.Heap;

public class GUI extends JPanel implements ActionListener, Serializable {

	private static final long serialVersionUID = -6465327145108762100L;

	private static final int AntSize = 35, SPEED = 1, ObjSize = 30;

	private Board board;
	private int rows, cols;
	private int width, height;
	private int sizecol = 0;
	private int sizerow = 0;
	private Cell[][] cells;

	public GUI(Board _board, int _rows, int _cols, int _width, int _height) {
		this.board = _board;
		this.rows = _rows;
		this.cols = _cols;
		this.width = _width;
		this.height = _height;
		sizecol = width / cols;
		sizerow = height / rows;
		cells = board.getBoardCells();

		// timer to repaint the board
		Timer timer = new Timer(500, this);
		timer.start();
	}

	public void setUpdatedBoard(Board board) {
		this.board = board;
		cells = board.getBoardCells();
	}

	public void actionPerformed(ActionEvent e) {
		// get screen size, to use later maybe
		int widthnew = getWidth();
		int heightnew = getHeight();

		// recalculate the column/row sizes to have them match the resized
		// window area
		sizecol = widthnew / cols;
		sizerow = heightnew / rows;
		width = widthnew;
		height = heightnew;

		// Save the updated board
		cells = board.getBoardCells();
		repaint();
	}

	public int getWidth(ActionEvent e) {
		// get screen size
		int widthnew = getWidth();
		return widthnew;
	}

	public int getHeight(ActionEvent e) {
		// get screen size
		int heightnew = getHeight();
		return heightnew;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// Drawing lines
		for (int i = 0; i < rows; i++) {
			Line2D lin = new Line2D.Float(0, i * sizerow, width, i * sizerow);
			g2.draw(lin);
		}
		for (int j = 0; j < cols; j++) {
			Line2D lin = new Line2D.Float(j * sizecol, 0, j * sizecol, height);
			g2.draw(lin);
		}
		// Traversing the board
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = cells[i][j];
				if (cell == null) {
					continue;
				}
				if (cell.getEntityType().equals("ant")) {
					// Drawing ants from their position on a board
					g.setColor(Color.orange);
					int x = i * sizecol;
					int y = j * sizerow;
					g.fillOval((int) (x + (sizecol - AntSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
							(int) (y + (sizerow - AntSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
							(int) (AntSize * (0.02 * Math.min(sizecol, sizerow))),
							(int) (AntSize * (0.02 * Math.min(sizecol, sizerow))));
					cell.getAnt().move(rows, cols);

				} else if (cell.getEntityType().equals("heap")) {
					// Drawing heap objects of different types with different colors
					Heap h = cell.getHeap();
					int[] heapelements = h.getHeapElements();
					for (int k = 0; k < h.getSize(); k++) {
						float color = (float) heapelements[k] / (float) h.getTypes();
						Color сolortype = new Color((int) ((color) * 255), (int) ((color) * 255), (int) ((0.7) * 255));
						g.setColor(сolortype);
						int x = i * sizecol;
						int y = j * sizerow;
						g.fillRect((int) (x + (k) * (sizecol / h.getSize())), (int) (y + (sizerow * 0.1)), sizecol / h.getSize(),
								(int) (sizerow * 0.8));
						// Drawing an edge for a heap element, to differentiate their quantity
						g.setColor(Color.white);
						g.drawRect((int) (x + (k) * (sizecol / h.getSize())), (int) (y + (sizerow * 0.1)), sizecol / h.getSize(),
								(int) (sizerow * 0.8));
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		// Input parameters
		/*
		 * int row = 20; int co = 20; int w = 1024; int h = 600;
		 * 
		 * Board board = new Board(row, co, 2, 2); board.init(); System.out.println(board);
		 * 
		 * JFrame frame = new JFrame("AntClass"); frame.setSize(w, h); frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 * 
		 * frame.add(new GUI(board, row, co, w, h)); frame.setVisible(true);
		 */

	}
}
