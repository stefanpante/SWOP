package processing;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Represents a GUI element.
 * 
 * @author Stefan
 *
 */
public abstract class GUIElement implements Drawable{

	/**
	 * the height of the GUIElement.
	 */
	protected float height;
	
	/**
	 * The width of the GUIElement.
	 */
	protected float width;
	
	/**
	 * The color of the gui element
	 */
	protected int color;
	/**
	 * whether or not the GUIElement is visible. standard visibility is true.
	 */
	protected boolean visible;
	
	/**
	 * The PApplet used to draw
	 */
	protected PApplet gui;
	
	/**
	 * The position of the GUIElement, the top left corner
	 */
	protected PVector position;
	
	public GUIElement(float height, float width, PVector position, PApplet gui) {
		this.height = height;
		this.width = width;
		this.position = position;
		this.gui = gui;
		this.color = OConstants.LIGHTER_GREY;
		this.visible = true;
	}
	
	/**
	 * Returns the color of the button.
	 * @return
	 */
	public int getColor(){
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
	 * Returns the position of the button.
	 * @return
	 */
	public PVector getPosition(){
		return this.position;
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
	
	public abstract void draw();

}
