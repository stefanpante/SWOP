package gui.button;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import util.OConstants;

public class TextButton extends GUIButton {

	/**
	 * The text on the text button.
	 */
	private String text;
	
	public TextButton(float width, float height, PVector position, String text,  PApplet gui) {
		super(width, height, position, gui);
		this.text = text;
		this.setColor(OConstants.LIGHTER_GREY);
		this.setRolloverColor(OConstants.LIGHT_GREY);
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
