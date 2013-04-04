package processing;

import processing.core.PShape;
import processing.core.PVector;

public class SquareGUI implements Drawable{

	
	/**
	 * Integer representation of color(241,241,241)
	 */
	public static int LIGHTER_GREY = -921103;

	/**
	 *  Integer representation of color(204,204,204)
	 */
	public static int LIGHT_GREY = -3355444;

	/**
	 * The width of the square
	 */
	private float width;

	/**
	 * The height of the square
	 */
	private float height;

	/**
	 * The top left corner of the square.
	 */
	private PVector position;
	private ObjectronGUI gui;
	private PShape shape;

	/**
	 * 
	 * @param objectronGUI
	 */
	public SquareGUI(ObjectronGUI objectronGUI){
		this.gui = objectronGUI;
		this.position = new PVector();
		this.shape = objectronGUI.loadShape(getClass().getResource("/res/powerfailure.svg").getPath());
	}
	
	/**
	 * Constructs a new SquareGUi.
	 * @param Position
	 * @param width
	 * @param height
	 * @param objectronGUI
	 */
	public SquareGUI(PVector Position, float width, float height, ObjectronGUI objectronGUI) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.gui = objectronGUI;
	}

	/**
	 * Draws the square on the screen.
	 */
	@Override
	public void draw() {
		// no stroke on the square.
		gui.noStroke();

		// set the fill color of the square to lighter grey
		gui.fill(LIGHTER_GREY);
		// Draw the square
		gui.rect(position.x , position.y, width, height);
		gui.shape(shape , position.x + GridGui.MARGIN,position.y + GridGui.MARGIN, width -  GridGui.MARGIN*2,height-  GridGui.MARGIN*2);


	}

	/**
	 * Shows a rollover status of the square.
	 */
	private void rollover(){

		// no stroke on the square.
		gui.noStroke();

		// Set the fill color to light grey
		gui.fill(LIGHT_GREY, 75);

		// Draw the square
		gui.rect(position.x, position.y, width, height);
	}

	/**
	 * Method which returns whether the mouse is on the square.
	 */
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		if(mouseX > position.x && mouseX < position.x + width){
			if(mouseY > position.y && mouseY < position.y + height){
				return true;
			}
		}

		return false;

	}

	/**
	 * Checks whether the mouse is over the grid and takes appropiate action
	 * (shows a rollover status)
	 */
	@Override
	public void mouseOver(int mouseX, int mouseY) {
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

}
