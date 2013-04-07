package processing;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class SquareGUI extends GUIElement{


	/**
	 * The color when rollover
	 */
	private int rolloverColor;
	
	private PShape shape;



	/**
	 * Constructs a new SquareGUi.
	 * @param Position
	 * @param width
	 * @param height
	 * @param objectronGUI
	 */
	public SquareGUI(PVector position, float width, float height, PApplet gui) {
		// float height, float width, PVector position, PApplet gui
		super(height, width, position, gui);
	}

	/**
	 * Draws the square on the screen.
	 */
	@Override
	public void draw() {
		
			// no stroke on the square.
			gui.noStroke();

			// set the fill color of the square to lighter grey
			gui.fill(color);
			// Draw the square
			gui.rect(position.x , position.y, width, height);
			if(shape != null)
				if(shape.equals(Shapes.wall))
					gui.shape(shape , position.x ,position.y , width ,height);
				else gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
						width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
	}

	/**
	 * Shows a rollover status of the square.
	 */
	private void rollover(){

		// no stroke on the square.
		gui.noStroke();

		// Set the fill color to light grey
		gui.fill(rolloverColor, 75);

		// Draw the square
		gui.rect(position.x, position.y, width, height);

	}

	/**
	 * Method which returns whether the mouse is on the square.
	 */
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		if(!visible){
			return false;
		}
		if(mouseX > position.x && mouseX < position.x + width){
			if(mouseY > position.y && mouseY < position.y + height){
				return true;
			}
		}

		return false;

	}
	
	public void setShape(PShape shape){
		this.shape = shape;
	}

	/**
	 * Checks whether the mouse is over the grid and takes appropiate action
	 * (shows a rollover status)
	 */
	public void hover(int mouseX, int mouseY) {
		if(mouseHit(mouseX, mouseY)){
			this.rollover();
		}

	}

	public void setPosition(float x, float y){
		this.position.x = x;
		this.position.y = y;
	}

	public void setHeight(float height){
		this.height = height;
	}

	public void setWidth(float width){
		this.width = width;
	}

	public void setVisibility(boolean visible){
		this.visible = visible;
	}

	public boolean isVisible(){
		return visible;
	}

	public void reset(){
		this.shape = null;
		this.color = OConstants.LIGHTER_GREY;
	}
	
	public void setColor(int color){
		
	}
	public boolean hasShape(){
		return (shape != null);
	}
	
	public PShape getShape(){
		return this.shape;
	}

	public PVector getPosition() {
		return this.position;
		
	}

}
