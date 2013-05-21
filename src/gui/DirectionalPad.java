package gui;

import gui.button.DirectionalButton;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;
import util.Direction;
import util.OConstants;

public class DirectionalPad extends GUIElement{

	/**
	 * PApplet used to draw.
	 */
	protected PApplet gui;

	/**
	 * Should be the position of the player.
	 */
	private PVector position;
	
	float buttonWidth;

	private float buttonHeight;
	
	/**
	 * The buttons which compose this directionalPad
	 */
	protected HashMap<Direction,DirectionalButton> buttons;

	/**
	 * Constructs a new DirectionalPad.
	 * @param position	the position of the directionalPad
	 * @param gui		the PApplet used to draw.
	 */
	public DirectionalPad(PVector position, float buttonWidth, float buttonHeight,PApplet gui){
		super(145,145, new PVector(), gui);
		this.buttonHeight = buttonHeight;
		this.buttonWidth = buttonWidth;
		super.setPosition(position);
		initButtons();
	}


	/**
	 * initializes the button.
	 */
	private void initButtons() {

		buttons = new HashMap<Direction,DirectionalButton>();



		PVector pos1 = new PVector(position.x, position.y - OConstants.MARGIN - buttonHeight);
		DirectionalButton b1 = new DirectionalButton(buttonWidth, buttonHeight, Direction.NORTH, pos1, gui);
		buttons.put(Direction.NORTH, b1);

		PVector pos2 = new PVector(position.x, position.y + OConstants.MARGIN + buttonHeight);
		DirectionalButton b2 = new DirectionalButton(buttonWidth, buttonHeight, Direction.SOUTH, pos2, gui);
		buttons.put(Direction.SOUTH, b2);

		PVector pos3 = new PVector(position.x - OConstants.MARGIN - buttonWidth, position.y);
		DirectionalButton b3 = new DirectionalButton(buttonWidth, buttonHeight, Direction.WEST,pos3, gui);
		buttons.put(Direction.WEST,b3);

		PVector pos4 = new PVector(position.x + OConstants.MARGIN + buttonWidth, position.y);
		DirectionalButton b4 = new DirectionalButton(buttonWidth, buttonHeight, Direction.EAST, pos4, gui);
		buttons.put(Direction.EAST, b4);

		PVector pos5 = new PVector(pos4.x, pos1.y);
		DirectionalButton b5 = new DirectionalButton(buttonWidth, buttonHeight, Direction.NORTHEAST, pos5, gui);
		buttons.put(Direction.NORTHEAST, b5);

		PVector pos6 = new PVector(pos3.x, pos1.y);
		DirectionalButton b6 = new DirectionalButton(buttonWidth, buttonHeight, Direction.NORTHWEST, pos6, gui);
		buttons.put(Direction.NORTHWEST,b6);

		PVector pos7 = new PVector(pos3.x, pos2.y );
		DirectionalButton b7 = new DirectionalButton(buttonWidth, buttonHeight, Direction.SOUTHWEST, pos7, gui);
		buttons.put(Direction.SOUTHWEST, b7);

		PVector pos8 = new PVector(pos4.x,pos2.y);
		DirectionalButton b8 = new DirectionalButton(buttonWidth, buttonHeight,Direction.SOUTHEAST, pos8, gui);
		buttons.put(Direction.SOUTHEAST, b8);

	}

	@Override
	public void draw() {
		// the directionalPad doesn't draw anything when not hovered.

	}

	public void hover(int mouseX, int mouseY) {
		if(visible){
			for(DirectionalButton button: buttons.values()){
				button.hover(mouseX, mouseY);
			}
		}

	}

	@Override
	public void setPosition(PVector position){
		this.position = position;
		this.initButtons();
	}

	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		return false;
	}

	public void mousePressed(int mouseX, int mouseY) {
		if(visible){
			for(DirectionalButton button: buttons.values()){
				if(button.mouseHit(mouseX, mouseY)){
					ObjectronGUI gui2= (ObjectronGUI) gui;
					gui2.move(button.getDirection());
				}
			}
		}

	}

	/**
	 * Returns the buttons in the directionalPad as an HashMap.
	 * @return
	 */
	public HashMap<Direction, DirectionalButton> getButtons() {
		return this.buttons;
	}
	
}
