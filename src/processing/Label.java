package processing;

import processing.core.PApplet;
import processing.core.PVector;

public class Label extends GUIElement{
	
	/**
	 * The text to be displayed onto the button
	 */
	private String text;
	/**
	 * The size of the text
	 */
	private int textSize;
	
	private int textColor;
	
	private int hAlign;
	private int vAlign;
	/**
	 * 
	 * @param width
	 * @param height
	 * @param position
	 * @param gui
	 */
	

	public Label(float width, float height, PVector position, PApplet gui) {
		//float width, float height, PVector position, PApplet gui
		super(width, height, position, gui);
	}
	
	/**
	 * Set the font size.
	 * @param textSize
	 */
	public void setTextSize(int textSize){
		this.textSize = textSize;
	}
	
	/**
	 * Gets the text size.
	 * @return
	 */
	public int getTextSize(){
		return this.textSize;
	}
	
	/**
	 * Sets the color of the text.
	 * @param textColor
	 */
	public void setTextColor(int textColor){
		this.textColor = textColor;
	}
	
	/**
	 * Returns the textColor
	 * @return
	 */
	public int getTextColor(){
		return textColor;
	}
	
	public void setHorizontalAlign(int align){
		this.hAlign = align;
	}
	
	public int getHorizontalAlign(){
		return hAlign;
	}
	
	public void setVerticalAlign(int align){
		this.vAlign = align;
	}
	
	public int getVerticalAlign(){
		return vAlign;
	}

	

	@Override
	public void draw() {
		gui.fill(color);
		gui.noStroke();
		
		gui.rect(position.x, position.y, width, height);
		
		gui.fill(textColor);
		gui.textSize(textSize);
		gui.textAlign(hAlign,vAlign);
		gui.text(text, position.x, position.y, width, height);
		
	}

}
