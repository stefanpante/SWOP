package processing.button;

import processing.OConstants;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class TextButton extends GUIButton {

	/**
	 * The text on the text button.
	 */
	private String text;
	
	public TextButton(float width, float height, PVector position, String text,  PApplet gui) {
		super(width, height, position, gui);
		this.text = text;
	}

	@Override
	public void draw() {
		gui.noStroke();
		gui.fill(OConstants.LABELGREEN);
		gui.rect(position.x, position.y, width, height);
		
		gui.fill(gui.color(255));
		gui.textAlign(PConstants.CENTER, PConstants.CENTER);
		gui.text(text, position.x, position.y - 3, width, height);
		
	}

	/**
	 * Mouseover draw
	 */
	@Override
	public void mouseOver(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	

}
