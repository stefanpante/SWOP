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
	private GridModel gridModel;

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
		
		this.gridModel= new GridModel();
		gridModel.addToWalls(new Coordinate2D(0, 0), null);
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
	    for(Coordinate2D coordinate : getGridModel().getWalls().keySet()){
	    	DrawImage(graphics, coordinate, "wall");
	    }
	}
	
	private void DrawImage(Graphics graphics, Coordinate2D coordinate, String image){
		Image img = Toolkit.getDefaultToolkit().getImage("res/"+image+".png");
    	graphics.drawImage(img,coordinate.getX(),coordinate.getY(),colWidth,rowHeight,Color.BLACK,this);

	}
	
	public GridModel getGridModel(){
		return this.gridModel;
	}
	
}
