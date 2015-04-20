package GUI;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import shared_classes.Board;
import shared_classes.Cell;
import shared_classes.Heap;

public class GUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final int AntSize = 35, SPEED = 1, ObjSize = 30;

	Board b;
	private int xPos, yPos;
	private int xSpeed, ySpeed;
	int rows, cols;
	int width, height;
	int sizecol = 0;
	int sizerow = 0;
	int antnum = 10;
	int obj1num = 10;
	int obj2num = 15;
	int[] AntxPos = new int[antnum];
	int[] AntyPos = new int[antnum];
	int[] Obj1xPos = new int[obj1num];
	int[] Obj1yPos = new int[obj1num];
	int[] Obj2xPos = new int[obj2num];
	int[] Obj2yPos = new int[obj2num];

	public GUI(Board _b, int _rows, int _cols, int _width, int _height) {
		this.b = _b;
		this.rows = _rows;
		this.cols = _cols;
		this.width = _width;
		this.height = _height;
		sizecol = width / cols;
		sizerow = height / rows;

		Random r = new Random();
		for (int i = 0; i < antnum; i++) {
			AntxPos[i] = r.nextInt(cols);
			AntyPos[i] = r.nextInt(rows);
		}
		for (int i = 0; i < obj1num; i++) {
			Obj1xPos[i] = r.nextInt(cols);
			Obj1yPos[i] = r.nextInt(rows);
		}

		for (int i = 0; i < obj2num; i++) {
			Obj2xPos[i] = r.nextInt(cols);
			Obj2yPos[i] = r.nextInt(rows);
		}
		// determine speed direction
		xSpeed = SPEED;
		ySpeed = -SPEED;
		// timer to
		Timer timer = new Timer(500, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		// get screen size, to use later maybe
		int widthnew = getWidth();
		int heightnew = getHeight();

		// recalculate the column/row sizes to have them match the resized window area
		sizecol = widthnew / cols;
		sizerow = heightnew / rows;
		width = widthnew;
		height = heightnew;

		for (int i = 0; i < antnum; i++) {
			// update positions
			AntxPos[i] += xSpeed;
			// test if we go out the screen on the X axis
			if (AntxPos[i] < 0) {
				AntxPos[i] = 0;
				xSpeed = SPEED;
			} else if (AntxPos[i] * sizecol > width) {
				AntxPos[i] = 0;
				xSpeed = SPEED;
			}

			// update positions
			AntyPos[i] += ySpeed;
			// test if we go out the screen on the Y axis
			if (AntyPos[i] < 0) {
				AntyPos[i] = 0;
				ySpeed = SPEED;
			} else if (AntyPos[i] * sizecol > width) {
				AntyPos[i] = 0;
				ySpeed = SPEED;
			}
		}

		Cell[][] board = b.getBoardCells();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell c = board[i][j];
				if (c.getEntityType().equals("ant")) {

					// b.move(c.getAnt());
				}
			}
		}
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

		Cell[][] cells = b.getBoardCells();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell c = cells[i][j];
				if (c.getEntityType().equals("ant")) {

					g.setColor(Color.orange);
					int x = i * sizecol;
					int y = j * sizerow;
					g.fillOval((int) (x + (sizecol - AntSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
							(int) (y + (sizerow - AntSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
							(int) (AntSize * (0.02 * Math.min(sizecol, sizerow))),
							(int) (AntSize * (0.02 * Math.min(sizecol, sizerow))));
					c.getAnt().move(rows, cols);
				} else if (c.getEntityType().equals("heap")) {
					Heap h = c.getHeap();

					// Check for type ???
					// if (c.heap.getSize() = 1)
					// Change the color to something
					// g.setColor(Color.green);
					Color сolor = new Color((int) ((1 / 2.0) * 255), (int) ((1 / 2.0) * 255), (int) ((1 / 2.0) * 255));
					g.setColor(сolor);
					int x = i * sizecol;
					int y = j * sizerow;
					g.fillRect((int) (x + (sizecol - ObjSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
							(int) (y + (sizerow - ObjSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
							(int) (ObjSize * (0.02 * Math.min(sizecol, sizerow))),
							(int) (ObjSize * (0.02 * Math.min(sizecol, sizerow))));
				}
			}
			System.out.println();
		}
		/*
		 * // Drawing Ants for (int i = 0; i < antnum; i++) { g.setColor(Color.orange); int x = AntxPos[i] * sizecol; int y =
		 * AntyPos[i] * sizerow; g.fillOval((int) (x + (sizecol - AntSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0), (int) (y +
		 * (sizerow - AntSize 0.02 * Math.min(sizecol, sizerow)) / 2.0), (int) (AntSize * (0.02 * Math.min(sizecol, sizerow))),
		 * (int) (AntSize * (0.02 * Math.min(sizecol, sizerow)))); }
		 * 
		 * // Drawing objects type 1 for (int k = 0; k < obj1num; k++) { float color1 = (k+1)/10;
		 * 
		 * Color сolor = new Color((int)color1*255, (int)color1*255, (int)color1*255); g.setColor(сolor );
		 * 
		 * int x = Obj1xPos[k] * sizecol; int y = Obj1yPos[k] * sizerow; g.fillRect((int) (x + (sizecol - ObjSize * 0.02 *
		 * Math.min(sizecol, sizerow)) / 2.0), (int) (y + (sizerow - ObjSize 0.02 * Math.min(sizecol, sizerow)) / 2.0), (int)
		 * (ObjSize * (0.02 * Math.min(sizecol, sizerow))), (int) (ObjSize * (0.02 * Math.min(sizecol, sizerow)))); }
		 * 
		 * // Drawing objects type 2 for (int k = 0; k < obj2num; k++) { g.setColor(Color.blue); int x = Obj2xPos[k] * sizecol;
		 * int y = Obj2yPos[k] * sizerow; g.fillRect((int) (x + (sizecol - ObjSize * 0.02 * Math.min(sizecol, sizerow)) / 2.0),
		 * (int) (y + (sizerow - ObjSize 0.02 * Math.min(sizecol, sizerow)) / 2.0), (int) (ObjSize * (0.02 * Math.min(sizecol,
		 * sizerow))), (int) (ObjSize * (0.02 * Math.min(sizecol, sizerow)))); }
		 */
	}

	public static void main(String[] args) {
		// Input parameters
		int row = 20;
		int co = 20;
		int w = 1024;
		int h = 600;

		Board board = new Board(row, co, 2, 2);
		board.init();
		System.out.println(board);

		JFrame frame = new JFrame("AntClass");
		frame.setSize(w, h);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(new GUI(board, row, co, w, h));
		frame.setVisible(true);

	}
}
