package processing;

import processing.core.PApplet;
import processing.core.PShape;

public abstract class ShapeButton extends GUIButton {

	/**
	 * The shape of the button.s
	 */
	private PShape shape;
	
	
	public ShapeButton(float width, float height, PShape shape, PApplet gui) {
		super(width, height, gui);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return false;
	}
	

	
}
