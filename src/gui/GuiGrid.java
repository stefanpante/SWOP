/**
 * 
 */
package gui;

import grid.Coordinate2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * @author jonas
 *
 */
public class GuiGrid extends Canvas {
	
	int width, height, rows, cols;
	int rowHeight, colWidth;

	GuiGrid(int width, int height, int rows, int cols){
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		this.rowHeight = height/rows;
		this.colWidth = width/cols;
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paint(Graphics graphics){
		graphics.setColor(Color.WHITE);
	    for (int i = 0; i <= rows; i++)
	    	graphics.drawLine(0, i * rowHeight, width, i * rowHeight);
	    for (int i = 0; i <= cols; i++)
	    	graphics.drawLine(i * colWidth, 0, i * colWidth, height);
	}
}
