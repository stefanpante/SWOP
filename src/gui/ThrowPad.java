package gui;

import gui.button.DirectionalButton;
import item.launchable.LaunchableItem;
import processing.core.PApplet;
import processing.core.PVector;

public class ThrowPad extends DirectionalPad {

	private LaunchableItem launchable;

	public ThrowPad(PVector position, float buttonWidth, float buttonHeight,
			PApplet gui) {
		super(position, buttonWidth, buttonHeight, gui);

	}
	
	@Override
	public void mousePressed(int mouseX, int mouseY){
		for(DirectionalButton button: getButtons().values()){
			if(button.mouseHit(mouseX, mouseY)){
				ObjectronGUI gui2= (ObjectronGUI) gui;
				gui2.throwLaunchableItem(launchable, button.getDirection());
				break;
				
			}
		}
	}

}
