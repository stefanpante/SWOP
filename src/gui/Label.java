package gui;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import util.OConstants;

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
	

	public Label(float width, float height, PVector position, String text, PApplet gui) {
		//float width, float height, PVector position, PApplet gui
		super(width, height, position, gui);
		super.setColor(OConstants.GREEN);
		this.textColor = OConstants.WHITE;
		this.hAlign = PConstants.CENTER;
		this.vAlign = PConstants.CENTER;
		this.textSize = 16;
		this.text = text;
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
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}

	

	@Override
	public void draw() {
		// Draws the background
		gui.fill(color);
		gui.noStroke();
		gui.rect(position.x, position.y, width, height);
		
		// Draws the text.
		gui.fill(textColor);
		gui.textSize(textSize);
		gui.textAlign(hAlign,vAlign);
		gui.text(text, position.x, position.y, width, height - 3);
		
	}

}
