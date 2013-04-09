package gui;

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
	private float endFrame;
	private float currentFrame;
	
	
	public Message(float width, float height, PVector position, int duration, PApplet gui) {
		super(height, width, position, gui);
		this.endFrame = duration;
		this.currentFrame = duration;
		// float height, float width, PVector position, PApplet gui
	}

	@Override
	public void draw() {
		if(currentFrame < endFrame){
			// draw everything.
			currentFrame++;
		}
		
	}
	
	public void show(){
		currentFrame = 0;
	}

}
