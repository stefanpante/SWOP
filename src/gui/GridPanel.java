/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utils.Coordinate;

/**
 * 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class GridPanel extends JPanel implements ImageObserver, Observer {
	
	private int width, height, rows, cols;
	private int rowHeight, colWidth;

	private String message;
	
	private ArrayList<Coordinate> walls;
	private ArrayList<Coordinate> grenades;
	private ArrayList<Coordinate> players;
	
	/**
	 * 
	 * Creates a new GridCanvas with the specified width and height in pixels.
	 * the grid consists of squares of size height/rows x width/cols
	 * @param width		The width of the canvas
	 * @param height	The height of the canvas
	 * @param rows		number of rows in the grid
	 * @param cols		number of columns in the gris
	 */
	GridPanel(int width, int height, int rows, int cols){
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		this.rowHeight = height/rows;
		this.colWidth = width/cols;
	}
	
	/**
	 * Paints the grid
	 */
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		setBackground(Color.BLACK);
		
		/* Draw gridlines */
		graphics.setColor(Color.WHITE);
	    for (int i = 1; i <= rows-1; i++)
	    	graphics.drawLine(0, i * rowHeight, width, i * rowHeight);
	    for (int i = 1; i <= cols-1; i++)
	    	graphics.drawLine(i * colWidth, 0, i * colWidth, height);
	    
	    /* Draw walls & grenades */
	    for(Coordinate coordinate : walls)
	    	DrawImage(graphics, coordinate, "wall");
		for(Coordinate coordinate : grenades)
		    DrawImage(graphics, coordinate, "lightgrenade");
		
//		/* Draw light trail */
//		for(Coordinate coordinate : ApplicationWindow.MODEL.getLightTrailRed())
//		    DrawImage(graphics, coordinate, "cell_lighttrail_blue");
//		for(Coordinate coordinate : ApplicationWindow.MODEL.getLightTrailRed())
//		    DrawImage(graphics, coordinate, "cell_lighttrail_red");	
		
		/* Draw Players */
		for(int i = 0; i < players.size(); i++)
			DrawImage(graphics, players.get(i), "player_"+i);
		

	}
	
	private void DrawImage(Graphics graphics, Coordinate coordinate, String image){
		if(coordinate == null)
			return;
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/"+image+".png"));
    	graphics.drawImage(img,coordinate.getX()*colWidth+1,coordinate.getY()*rowHeight+1,colWidth-1,rowHeight-1,Color.BLACK,this);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}
	
	
	
	public void showMessage(){
		String msg = getMessage();
		if(msg != null && msg.length() != 0){
			JOptionPane.showMessageDialog(this, msg);
		}
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		String msg = message;
		this.message = "";
		return msg;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param newValue
	 */
	public void setWalls(ArrayList<Coordinate> walls) {
		this.walls = walls;
		this.repaint();
	}

	/**
	 * @param o
	 */
	public void setGrenades(ArrayList<Coordinate> o) {
		this.grenades = grenades;
		this.repaint();		
	}

}
