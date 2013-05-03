package gui;

import processing.core.PVector;

public interface Drawable {

	/**
	 * The method to draw the Drawable object onto the screen.
	 */
	public void draw();
	
	/**
	 * Returns whether the mouse is positioned over the element.
	 * @param mouseX	the x coordinate of the mouse cursor
	 * @param mouseY	the y coordinate of the mouse cursor.
	 * @return	true 	if the mouse is over the Drawable
	 */
	public boolean mouseHit(int mouseX, int mouseY);
	
	/**
	 * Returns the color of the Drawable.
	 */
	public int getColor();
	
	/**
	 * Sets the color of the Drawable.
	 */
	public void setColor(int color);
	
	/**
	 * Sets the position of the Drawable.
	 * @param x		the x coordinate of the drawable.
	 * @param y		the y coordinate of the drawable.
	 */
	public void setPosition(float x, float y);
	
	/**
	 * Sets the position of the Drawable.
	 */
	public void setPosition(PVector position);
	
	/**
	 * Returns the position of the Drawable
	 */
	public PVector getPosition();
}
