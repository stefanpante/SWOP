package processing;

import processing.core.PApplet;
import processing.core.PVector;

public class Message extends GUIElement {

	/**
	 * The label of the message
	 */
	protected Label label;
	
	/**
	 * The duration of the message in sec.
	 */
	protected float showDuration;
	
	
	public Message(float width, float height, PVector position, PApplet gui) {
		super(height, width, position, gui);
		// float height, float width, PVector position, PApplet gui
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

}
