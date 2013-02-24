/**
 * 
 */
package gui;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 * @author jonas
 *
 */
public class GridCanvas extends Canvas {

	int width, height;
	int rows, cols;

	GridCanvas(int width, int height, int rows, int cols) {
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		setSize(width, height);
	}

	public void paint(Graphics g) {
		int i;
	    width = getSize().width;
	    height = getSize().height;

	    int rowHt = height / (rows);
	    for (i = 0; i < rows; i++)
	      g.drawLine(0, i * rowHt, width, i * rowHt);

	    int rowWid = width / (cols);
	    for (i = 0; i < cols; i++)
	      g.drawLine(i * rowWid, 0, i * rowWid, height);
	}
}
