package processing.button;

import processing.Drawable;
import processing.OConstants;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class GUIButton implements Drawable {

	
	protected PApplet gui;
	/**
	 * The position of the button
	 */
	protected PVector position;
	
	/**
	 * the width of the gui button.
	 */
	protected float width;
	
	/**
	 * The height of the gui button.
	 */
	protected float height;
	
	/**
	 * The standard color of the button.
	 */
	protected int color;
	
	/**
	 * The color of the button when the mouse hovered.
	 */
	protected int rolloverColor;
	
	/**
	 *  Whether or not the button is visible.
	 */
	protected boolean visible;
	
	/**
	 * constructs a new button, the position is set to (0,0)
	 * @param width		the width for the button.
	 * @param height	the height for the button;
	 * @param gui		the PApplet to draw the button.
	 */
	public GUIButton(float width, float height, PApplet gui) {
		this.width = width;
		this.height = height;
		this.gui = gui;
		this.visible = true;
		this.position = new PVector();
		this.color = OConstants.LIGHT_GREY;
		this.rolloverColor = OConstants.LIGHTER_GREY;

	}
	
	/**
	 * constructs a new button.
	 * @param width		the width for the button.
	 * @param height	the height for the button.
	 * @param position	the position for the button.
	 * @param gui		the PApplet for the button.
	 */
	public GUIButton(float width, float height, PVector position,PApplet gui) {
		this(width, height, gui);
		this.position = position;
	}
	
	/**
	 * Returns the color of the button.
	 * @return
	 */
	public int getColor(){
		return this.color;
	}
	
	/**
	 * Returns the rollover color for the button.
	 * @return
	 */
	public int getRolloverColor(){
		return this.color;
	}
	
	/**
	 * Sets the color for the button.
	 * @param color
	 */
	public void setColor(int color){
		this.color = color;
	}
	
	/**
	 * Sets the rollover color for the button.
	 * @param color
	 */
	public void setRolloverColor(int color){
		this.rolloverColor = color;
		
	}
	
	/**
	 * Returns the position of the button.
	 * @return
	 */
	public PVector getPosition(){
		return this.position;
	}
	
	/**
	 * Returns the visibility of the button.
	 * @return
	 */
	public boolean isVisible(){
		return this.visible;
	}
	
	/**
	 * Sets the visibility of the button.
	 * @param visible
	 */
	public void setVisibility(boolean visible){
		this.visible = visible;
	}
	
	/**
	 * Sets the position for the button.
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y){
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Sets the position for the button.
	 * @param position
	 */
	public void setPosition(PVector position){
		this.position = position;
	}
	
	/**
	 * Returns whether the given coordinates are over the button.
	 */
	public boolean mouseHit(int mouseX, int mouseY){
		if(mouseX >= position.x && mouseX <= position.x + width){
			if(mouseY >= position.y && mouseY <= position.y + height){
				return true;
			}
		}
		
		return false;
	}
}
