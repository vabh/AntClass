package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.Random;

public class GUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final int AntSize = 35, SPEED = 1, ObjSize = 30;
     
    private int xPos, yPos;
    private int xSpeed, ySpeed;
    int rows, cols;
    int width, height;
    int sizecol = 0;
    int sizerow = 0;
    int antnum = 10;
    int obj1num = 10;
    int obj2num = 15;
    int [] AntxPos = new int[antnum];
    int [] AntyPos = new int[antnum];
    int [] Obj1xPos = new int[obj1num];
    int [] Obj1yPos = new int[obj1num];
    int [] Obj2xPos = new int[obj2num];
    int [] Obj2yPos = new int[obj2num];
    
    GUI(int _rows, int _cols, int _width, int _height) {
        this.rows = _rows;
    	this.cols = _cols;
    	this.width = _width;
        this.height = _height;
        sizecol = width/cols;
        sizerow = height/rows;
        
        Random r = new Random();
        for (int i = 0; i< antnum; i++){
        	AntxPos[i] = r.nextInt(cols);
            AntyPos[i] = r.nextInt(rows);
        }
        for (int i = 0; i< obj1num; i++){
        	Obj1xPos[i] = r.nextInt(cols);
            Obj1yPos[i] = r.nextInt(rows);
        }
        
        for (int i = 0; i< obj2num; i++){
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
       
        	 for (int i = 0; i< antnum; i++){
           	  // update positions
               AntxPos[i] += xSpeed;
               // test if we go out the screen on the X axis
               if(AntxPos[i] < 0) {
                   AntxPos[i] = 0;
                   xSpeed = SPEED;
               }
               else if(AntxPos[i]*sizecol > width) {
                   AntxPos[i] = 0;
                   xSpeed = SPEED;
               }
        	 
              	  // update positions
                  AntyPos[i] += ySpeed;
                  // test if we go out the screen on the Y axis
                  if(AntyPos[i] < 0) {
                      AntyPos[i] = 0;
                      ySpeed = SPEED;
                  }
                  else if(AntyPos[i]*sizecol > width) {
                      AntyPos[i] = 0;
                      ySpeed = SPEED;
                  }
        	 }
             
        repaint();
    }
    
    //Maybe use later
    public int getWidth(ActionEvent e) {
        // get screen size
        int widthnew = getWidth();
        int heightnew = getHeight();
		return widthnew;
    }
    //Maybe use later
    public int getHeight(ActionEvent e) {
        // get screen size
        int widthnew = getWidth();
        int heightnew = getHeight();
		return heightnew;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        //Drawing lines
        for (int i = 0; i< rows; i++){
        	Line2D lin = new Line2D.Float(0, i*sizerow, width, i*sizerow);
        g2.draw(lin);
        }
        for (int j = 0; j< cols; j++){
        Line2D lin = new Line2D.Float(j*sizecol, 0, j*sizecol, height);
        g2.draw(lin);
        }
        
        //Drawing Ants
        for (int i=0; i<antnum; i++){
        	g.setColor(Color.orange);
       	 int x = AntxPos[i]*sizecol;
       	 int y = AntyPos[i]*sizerow;
        g.fillOval(x, y, AntSize, AntSize);
        }
        
        //Drawing objects type 1
        for (int k=0; k<obj1num; k++){
        	g.setColor(Color.green);
        	 int x = Obj1xPos[k]*sizecol;
        	 int y = Obj1yPos[k]*sizerow;
        	    g.fillRect(x, y, ObjSize, ObjSize);
        }
        
        //Drawing objects type 2
        for (int k=0; k<obj2num; k++){
        	g.setColor(Color.blue);
        	 int x = Obj2xPos[k]*sizecol;
        	 int y = Obj2yPos[k]*sizerow;
        	    g.fillRect(x, y, ObjSize, ObjSize);
        }
    }

    public static void main(String[] args) {
    	//Input parameters
    	int r = 20;
    	int co = 20;
    	int w = 1000;
    	int h = 900;
    	
        JFrame frame = new JFrame("Grid");
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.add(new GUI(r, co, w, h));
        frame.setVisible(true);

    }
}
