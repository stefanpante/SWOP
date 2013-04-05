package processing;

import processing.core.PApplet;

public abstract class TextButton extends GUIButton {

	/**
	 * The tekst on the text button.
	 */
	private String text;
	
	public TextButton(float width, float height, String text,  PApplet gui) {
		super(width, height, gui);
		this.text = text;
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
