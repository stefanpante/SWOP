package processing;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class GUIButton implements Drawable {

	
	private PApplet gui;
	/**
	 * The position of the button
	 */
	private PVector position;
	
	/**
	 * the width of the gui button.
	 */
	private float width;
	
	/**
	 * The height of the gui button.
	 */
	private float height;
	
	private int color;
	private int rolloverColor;
	
	
	public GUIButton(float width, float height, PApplet gui) {
		this.width = width;
		this.height = height;
		this.gui = gui;
		this.position = new PVector();
		this.color = OConstants.LIGHT_GREY;
		this.rolloverColor = OConstants.LIGHTER_GREY;

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
