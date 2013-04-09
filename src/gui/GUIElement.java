package gui;

import processing.core.PApplet;
import processing.core.PVector;
import util.OConstants;

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
	
	public GUIElement(float width, float height, PVector position, PApplet gui) {
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
	@Override
	public int getColor(){
		return this.color;
	}
	
	/**
	 * Sets the color for the button.
	 * @param color
	 */
	@Override
	public void setColor(int color){
		this.color = color;
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public float getWidth(){
		return this.width;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public float getHeight(){
		return this.height;
	}
	/**
	 * Returns the position of the button.
	 * @return
	 */
	@Override
	public PVector getPosition(){
		return this.position;
	}
	
	/**
	 * Sets the position for the button.
	 * @param x
	 * @param y
	 */
	@Override
	public void setPosition(float x, float y){
		this.position.x = x;
		this.position.y = y;
	}
	
	/**
	 * Sets the position for the button.
	 * @param position
	 */
	@Override
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
	
	
	public void setX(float x){
		position.x = x;
	}
	
	public void setY(float y){
		position.y = y;
	}
	
	
	/**
	 * Returns whether the given coordinates are over the button.
	 */
	@Override
	public boolean mouseHit(int mouseX, int mouseY){
		if(mouseX >= position.x && mouseX <= position.x + width){
			if(mouseY >= position.y && mouseY <= position.y + height){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public abstract void draw();

}