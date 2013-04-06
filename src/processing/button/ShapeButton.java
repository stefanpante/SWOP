package processing.button;

import processing.OConstants;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public abstract class ShapeButton extends GUIButton {

	/**
	 * The shape of the button.
	 */
	protected PShape shape;
	/**
	 * The rollover shape of this button.
	 */
	protected PShape rolloverShape;
	
	/**
	 * Constructs a new ShapeButton
	 * @param width		the width for the button.
	 * @param height	the height for the button.
	 * @param shape		the PShape to be drawn on the button.
	 * @param gui		the PApplet used to draw.
	 */
	public ShapeButton(float width, float height, PShape shape, PApplet gui) {
		super(width, height, gui);
		this.shape = shape;
	}
	
	/**
	 * Constructs a new ShapeButton
	 * @param width		the width for the button
	 * @param height	the height for the button
	 * @param shape		the PShape to be drawn on the button
	 * @param position	the position of the button
	 * @param gui		the PApplet used to draw.
	 */
	public ShapeButton(float width, float height, PShape shape, PVector position, PApplet gui) {
		super(width, height, position, gui);
		this.shape = shape;

	}
	
	/**
	 * Sets the rolloverShape for this PShape button.
	 * @param rolloverShape
	 */
	public void setRolloverShape(PShape rolloverShape){
		this.rolloverShape = rolloverShape;
	}

	/**
	 * Draws the ShapeButton onto the screen.
	 */
	@Override
	public void draw() {
		gui.noStroke();
		gui.fill(color);
		
		gui.rect(position.x, position.y, width, height);
		gui.shape(shape, position.x + OConstants.MARGIN, position.y + OConstants.MARGIN, width - OConstants.MARGIN*2,
				height - OConstants.MARGIN*2);
		
	}

	/**
	 * checks whether the mouse is over the button and draws
	 * the appropriate rollover state.
	 */
	@Override
	public void mouseOver(int mouseX, int mouseY) {
		if(mouseHit(mouseX,mouseY)){
			gui.noStroke();
			gui.fill(rolloverColor);
			
			gui.rect(position.x, position.y, width, height);
			gui.shape(shape, position.x + OConstants.MARGIN, position.y + OConstants.MARGIN, width - OConstants.MARGIN*2,
					height - OConstants.MARGIN*2);
		}
		
	}


	

	
}
