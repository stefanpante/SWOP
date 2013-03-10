/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import square.Square;
import utils.Coordinate2D;

/**
 * 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class GridPanel extends JPanel implements ImageObserver, Observer {
	
	private int width, height, rows, cols;
	private int rowHeight, colWidth;

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
		graphics.setColor(Color.WHITE);
	    for (int i = 1; i <= rows-1; i++)
	    	graphics.drawLine(0, i * rowHeight, width, i * rowHeight);
	    for (int i = 1; i <= cols-1; i++)
	    	graphics.drawLine(i * colWidth, 0, i * colWidth, height);
	    for(Coordinate2D coordinate : ApplicationWindow.MODEL.getWalls())
	    	DrawImage(graphics, coordinate, "wall");
		for(Coordinate2D coordinate : ApplicationWindow.MODEL.getLightTrailRed())
		    DrawImage(graphics, coordinate, "cell_lighttrail_blue");
		for(Coordinate2D coordinate : ApplicationWindow.MODEL.getLightTrailRed())
		    DrawImage(graphics, coordinate, "cell_lighttrail_red");	
		for(Coordinate2D coordinate : ApplicationWindow.MODEL.getLightGrenades())
		    DrawImage(graphics, coordinate, "lightgrenade");
		
		 DrawImage(graphics, ApplicationWindow.MODEL.getPlayer1(), "player_blue");
		 DrawImage(graphics, ApplicationWindow.MODEL.getPlayer2(), "player_red");
	}
	
	private void DrawImage(Graphics graphics, Coordinate2D coordinate, String image){
		if(coordinate == null)
			return;
		// System.out.println("Draw "+ image +" at "+ coordinate);
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/"+image+".png"));
    	graphics.drawImage(img,coordinate.getX()*colWidth+1,coordinate.getY()*rowHeight+1,colWidth-1,rowHeight-1,Color.BLACK,this);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}
	

}
