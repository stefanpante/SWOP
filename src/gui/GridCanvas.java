/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import square.Square;
import utils.Coordinate2D;

/**
 * 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class GridCanvas extends JPanel implements ImageObserver {
	
	private int width, height, rows, cols;
	private int rowHeight, colWidth;
	private static GridModel GRID_MODEL = new GridModel();;

	/**
	 * 
	 * Creates a new GridCanvas with the specified width and height in pixels.
	 * the grid consists of squares of size height/rows x width/cols
	 * @param width		The width of the canvas
	 * @param height	The height of the canvas
	 * @param rows		number of rows in the grid
	 * @param cols		number of columns in the gris
	 */
	GridCanvas(int width, int height, int rows, int cols){
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		this.rowHeight = height/rows;
		this.colWidth = width/cols;
		
		GRID_MODEL.addToLightGrenades(new Coordinate2D(0, 0), new Square());
		GRID_MODEL.setPlayer1(new Coordinate2D(0, 0));
		GRID_MODEL.setPlayer2(new Coordinate2D(100, 100));
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
	    for(Coordinate2D coordinate : GRID_MODEL.getWalls().keySet())
	    	DrawImage(graphics, coordinate, "wall");
		for(Coordinate2D coordinate : GRID_MODEL.getLightTrails().keySet())
		    DrawImage(graphics, coordinate, "cell_lighttrail_blue");	
		for(Coordinate2D coordinate : GRID_MODEL.getLightGrenades().keySet())
		    DrawImage(graphics, coordinate, "lightgrenade");
		
		 DrawImage(graphics, GRID_MODEL.getPlayer1(), "player_blue");
		 DrawImage(graphics, GRID_MODEL.getPlayer2(), "player_red");
	}
	
	private void DrawImage(Graphics graphics, Coordinate2D coordinate, String image){
		System.out.println("Draw "+ image +" at "+ coordinate);
		Image img = Toolkit.getDefaultToolkit().getImage("./src/res/"+image+".png");
    	graphics.drawImage(img,coordinate.getX(),coordinate.getY(),colWidth+1,rowHeight+1,Color.BLACK,this);
	}
	

}
