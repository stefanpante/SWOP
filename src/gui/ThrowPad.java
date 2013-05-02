package gui;

import item.IdentityDisc;

import java.util.HashMap;

import gui.button.DirectionalButton;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import square.Direction;

public class ThrowPad extends DirectionalPad {

	private IdentityDisc identityDisc;
	private PShape shape;

	public ThrowPad(PVector position, float buttonWidth, float buttonHeight,
			PApplet gui) {
		super(position, buttonWidth, buttonHeight, gui);
		setUpDirections();

	}

	private void setUpDirections(){
		HashMap<Direction, DirectionalButton> buttons = this.getButtons();
		buttons.get(Direction.SOUTHEAST).setVisibility(false);
		buttons.get(Direction.SOUTHWEST).setVisibility(false);
		buttons.get(Direction.NORTHWEST).setVisibility(false);
		buttons.get(Direction.NORTHEAST).setVisibility(false);
		
	}
	public void setShape(PShape shape){
		this.shape = shape;
	}

	@Override
	public void draw(){
		if(shape != null){
//			gui.shape(shape , getPosition().x + OConstants.MARGIN,getPosition().y + OConstants.MARGIN, 
//					getButtonWidth() -  OConstants.MARGIN*2, getButtonHeight()-  OConstants.MARGIN*2);
		}
	}
	@Override
	public void mousePressed(int mouseX, int mouseY){
		if(visible){
			System.out.println("Throw pad pressed");
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

	public IdentityDisc getLaunchabelItem(){
		return this.identityDisc;
	}

}
