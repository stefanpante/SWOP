package processing;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class ObjectronGUI extends PApplet{

	/**
	 * SearialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	GridGui grid;
	
	//FIXME: remove this shit
	PShape shape;
	/**
	 * initializes the objectron gui
	 */
	public void setup(){
		// sets the size from the applet to a fourth of the screen.
		size(displayWidth - 100, displayHeight - 100);
		
		// sets the framerate to 60 frames per second.
		frameRate(60);
		PVector position = new PVector(100, 100);
		this.grid = new GridGui(position, this, 500,500, 10, 10);
		
		
		
	}
	
	float width = 50;
	float height = 50;
	float margin = 5;
	
	/**
	 * Draws the entire game
	 */
	public void draw(){
		background(color(255));
		grid.draw();
		grid.mouseOver(mouseX, mouseY);
		grid.mouseOver(pmouseX, pmouseY);
		
	}
	
	


}
