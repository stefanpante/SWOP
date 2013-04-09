package gui;

import gui.button.DirectionalButton;
import item.launchable.LaunchableItem;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import util.OConstants;

public class ThrowPad extends DirectionalPad {

	private LaunchableItem launchable;
	private PShape shape;

	public ThrowPad(PVector position, float buttonWidth, float buttonHeight,
			PApplet gui) {
		super(position, buttonWidth, buttonHeight, gui);

	}

	public void setShape(PShape shape){
		this.shape = shape;
	}

	@Override
	public void draw(){
		if(shape != null){
			gui.shape(shape , getPosition().x + OConstants.MARGIN,getPosition().y + OConstants.MARGIN, 
					getButtonWidth() -  OConstants.MARGIN*2, getButtonHeight()-  OConstants.MARGIN*2);
		}
	}
	@Override
	public void mousePressed(int mouseX, int mouseY){
		if(visible){
			for(DirectionalButton button: getButtons().values()){
				if(button.mouseHit(mouseX, mouseY)){
					ObjectronGUI gui2= (ObjectronGUI) gui;
					gui2.throwLaunchableItem(launchable, button.getDirection());
					break;

				}
			}
		}
	}


	public void setLaunchableItem(LaunchableItem item) {
		this.launchable = item;

	}

	public LaunchableItem getLaunchabelItem(){
		return this.launchable;
	}

}
