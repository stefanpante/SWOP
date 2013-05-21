package gui;

import item.IdentityDisc;

import gui.button.DirectionalButton;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import util.Direction;

public class ThrowPad extends DirectionalPad {

	private IdentityDisc identityDisc;
	private PShape shape;

	public ThrowPad(PVector position, float buttonWidth, float buttonHeight,
			PApplet gui) {
		super(position, buttonWidth, buttonHeight, gui);
		setUpDirections();

	}

	private void setUpDirections(){
		buttons.remove(Direction.SOUTHEAST).setVisibility(false);
		buttons.remove(Direction.SOUTHWEST).setVisibility(false);
		buttons.remove(Direction.NORTHWEST).setVisibility(false);
		buttons.remove(Direction.NORTHEAST).setVisibility(false);
		
	}

	@Override
	public void draw(){
	}
	@Override
	public void mousePressed(int mouseX, int mouseY){
		if(visible){
			for(DirectionalButton button: getButtons().values()){
				if(button.mouseHit(mouseX, mouseY)){
					ObjectronGUI gui2= (ObjectronGUI) gui;
					gui2.throwLaunchableItem(identityDisc, button.getDirection());
					break;

				}
			}
		}
	}


	public void setIdentityDisc(IdentityDisc item) {
		this.identityDisc = item;

	}

	public IdentityDisc getIdentityDisc(){
		return this.identityDisc;
	}

}
