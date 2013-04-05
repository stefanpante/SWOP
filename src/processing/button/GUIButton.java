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
	
	protected int color;
	private int rolloverColor;
	
	
	public GUIButton(float width, float height, PApplet gui) {
		this.width = width;
		this.height = height;
		this.gui = gui;
		this.position = new PVector();
		this.color = OConstants.LIGHT_GREY;
		this.rolloverColor = OConstants.LIGHTER_GREY;

	}
	
	public GUIButton(float width, float height, PVector position,PApplet gui) {
		this(width, height, gui);
		this.position = position;
	}
	
	public int getColor(){
		return this.color;
	}
	
	public int getRolloverColor(){
		return this.color;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public void setRolloverColor(int color){
		this.rolloverColor = color;
		
	}
	
	public PVector getPosition(){
		return this.position;
	}
	
	public void setPosition(float x, float y){
		this.position.x = x;
		this.position.y = y;
	}
	
	public void setPosition(PVector position){
		this.position = position;
	}


}
