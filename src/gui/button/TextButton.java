package gui.button;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import util.OConstants;

public class TextButton extends GUIButton {

	/**
	 * The text on the text button.
	 */
	private final String text;
	
	public TextButton(float width, PVector position, String text, PApplet gui) {
		super(width, (float) 25, position, gui);
		this.text = text;
		this.setColor(OConstants.LIGHTER_GREY.getIntColor());
		this.setRolloverColor(OConstants.LIGHT_GREY.getIntColor());
	}

	@Override
	public void draw() {
		gui.noStroke();
		gui.fill(color);
		gui.rect(position.x, position.y, width, height);
		
		gui.fill(gui.color(255));
		gui.textAlign(PConstants.CENTER, PConstants.CENTER);
		gui.text(text, position.x, position.y - 3, width, height);
		
	}

	/**
	 * What to draw when the mouse is over this button.
	 */
	@Override
	public void hover(int mouseX, int mouseY) {
		if(mouseHit(mouseX, mouseY)){
			gui.noStroke();
			gui.fill(rolloverColor);
			gui.rect(position.x, position.y, width, height);
			
			gui.fill(gui.color(255));
			gui.textAlign(PConstants.CENTER, PConstants.CENTER);
			gui.text(text, position.x, position.y - 3, width, height);
		}
		
	}	

}
