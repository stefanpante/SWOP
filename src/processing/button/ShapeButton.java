package processing.button;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public abstract class ShapeButton extends GUIButton {

	/**
	 * The shape of the button.s
	 */
	protected PShape shape;
	protected PShape rolloverShape;
	
	public ShapeButton(float width, float height, PShape shape, PApplet gui) {
		super(width, height, gui);
		this.shape = shape;
	}
	public ShapeButton(float width, float height, PShape shape, PVector position, PApplet gui) {
		super(width, height, position, gui);
		this.shape = shape;

	}
	
	public void setRolloverShape(PShape rolloverShape){
		this.rolloverShape = rolloverShape;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}


	

	
}
