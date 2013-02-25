/**
 * 
 */
package gui.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author jonas
 *
 */
public class GuiGrid extends JPanel {
	
	int width, height, rows, cols;
	int rowHeight, colWidth;

	GuiGrid(int width, int height, int rows, int cols){
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		this.rowHeight = height/rows;
		this.colWidth = width/cols;
	}
	
	public void paintComponent(Graphics graphics){
		graphics.setColor(Color.WHITE);
	    for (int i = 1; i <= rows-1; i++)
	    	graphics.drawLine(0, i * rowHeight, width, i * rowHeight);
	    for (int i = 1; i <= cols-1; i++)
	    	graphics.drawLine(i * colWidth, 0, i * colWidth, height);
	}
}
