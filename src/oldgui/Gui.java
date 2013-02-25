/**
 * 
 */
package oldgui;

import grid.core.Coordinate2D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import simplegui.SimpleGUI;

/**
 * @author jonas
 *
 */
public class Gui {

	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final int ROWS = 10;
	public static final int COLS = 10;
	
	public static final int ROW_HEIGHT = HEIGHT / (ROWS);
	public static final int COL_WIDTH = WIDTH / (COLS);
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			HashMap<Coordinate2D,Color> rectangles =  new HashMap<Coordinate2D, Color>();

			public void run() {
				final SimpleGUI gui = new SimpleGUI("Demo", WIDTH, HEIGHT) {

					@Override
					public void paint(Graphics2D graphics) {
						graphics.setColor(Color.LIGHT_GRAY);
					    for (int i = 0; i <= ROWS; i++)
					    	graphics.drawLine(0, i * ROW_HEIGHT, WIDTH, i * ROW_HEIGHT);
					    for (int i = 0; i <= COLS; i++)
					    	graphics.drawLine(i * COL_WIDTH, 0, i * COL_WIDTH, HEIGHT);
					    
						for(Coordinate2D coordinate : rectangles.keySet()){
							Coordinate2D guiCoordinate = getGuiCoordinate(coordinate);
							graphics.drawRect(guiCoordinate.getX(), guiCoordinate.getY(), COL_WIDTH, ROW_HEIGHT);
						}
					}

					@Override
					public void handleMouseClick(int x, int y, boolean doubleClick) {
						Coordinate2D coordinate = getGridCoordinate(new Coordinate2D(x, y));
						System.out.println(coordinate);
						drawSquare(coordinate, Color.BLUE);
						new Runnable() {
							public void run() {
								//gui.repaint();
							}
						};
					}
					
					public void drawSquare(Coordinate2D coordinate, Color color){
						rectangles.put(coordinate, color);
					}
				};
					
				

			}

		});
	}
	
	public static Coordinate2D getGridCoordinate(Coordinate2D coordinate){
		int newX = coordinate.getX() / COL_WIDTH;
		int newY = coordinate.getY() / ROW_HEIGHT;
		return new Coordinate2D(newX, newY);
	}
	
	public static Coordinate2D getGuiCoordinate(Coordinate2D coordinate){
		int newX = coordinate.getX() * COL_WIDTH;
		int newY = coordinate.getY() * ROW_HEIGHT;
		return new Coordinate2D(newX, newY);
	}
 }
