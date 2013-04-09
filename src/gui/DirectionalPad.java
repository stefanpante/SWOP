package gui;

import gui.button.DirectionalButton;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;
import square.Direction;
import util.OConstants;

public class DirectionalPad extends GUIElement{

	/**
	 * PApplet used to draw.
	 */
	private PApplet gui;
	
	/**
	 * Should be the position of the player.
	 */
	private PVector position;
	/**
	 * The height of the directionalPad
	 */
	private int height;
	/**
	 * The width of the directionalPad
	 */
	private int width;
	
	/**
	 * The buttons which compose this directionalPad
	 */
	private HashMap<Direction,DirectionalButton> buttons;
	
	
	/**
	 * Constructs a new directionalPad. position is set to (0,0)
	 * @param gui 	the PApplet used to draw.
	 */
	public DirectionalPad(PApplet gui) {
		//float height, float width, PVector position, PApplet gui
		super(145,145, new PVector(), gui);
		this.gui = gui;
		this.position = new PVector();
		this.height = 145;
		this.width = 145;
		initButtons();
	}
	
	/**
	 * Constructs a new DirectionalPad.
	 * @param position	the position of the directionalPad
	 * @param gui		the PApplet used to draw.
	 */
	public DirectionalPad(PVector position, PApplet gui){
		this(gui);
		super.setPosition(position);
		initButtons();
	}

	
	/**
	 * initializes the button.
	 */
	private void initButtons() {
		int buttonWidth = (width - OConstants.MARGIN*2)/3;
		int buttonHeight = (height - OConstants.MARGIN*2)/3;
		buttons = new HashMap<Direction,DirectionalButton>();
		
		
		
		PVector pos1 = new PVector(position.x, position.y - OConstants.MARGIN - buttonHeight);
		DirectionalButton b1 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.north,Direction.NORTH, pos1, gui);
		buttons.put(Direction.NORTH, b1);
		
		PVector pos2 = new PVector(position.x, position.y + OConstants.MARGIN + buttonHeight);
		DirectionalButton b2 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.south,Direction.SOUTH, pos2, gui);
		buttons.put(Direction.SOUTH, b2);
		
		PVector pos3 = new PVector(position.x - OConstants.MARGIN - buttonWidth, position.y);
		DirectionalButton b3 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.west,Direction.WEST,pos3, gui);
		buttons.put(Direction.WEST,b3);
		
		PVector pos4 = new PVector(position.x + OConstants.MARGIN + buttonWidth, position.y);
		DirectionalButton b4 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.east,Direction.EAST, pos4, gui);
		buttons.put(Direction.EAST, b4);
		
		PVector pos5 = new PVector(pos4.x, pos1.y);
		DirectionalButton b5 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.northeast,Direction.NORTHEAST, pos5, gui);
		buttons.put(Direction.NORTHEAST, b5);
		
		PVector pos6 = new PVector(pos3.x, pos1.y);
		DirectionalButton b6 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.northwest,Direction.NORTHWEST, pos6, gui);
		buttons.put(Direction.NORTHWEST,b6);
		
		PVector pos7 = new PVector(pos3.x, pos2.y );
		DirectionalButton b7 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.southwest,Direction.SOUTHWEST, pos7, gui);
		buttons.put(Direction.SOUTHWEST, b7);
		
		PVector pos8 = new PVector(pos4.x,pos2.y);
		DirectionalButton b8 = new DirectionalButton(buttonWidth, buttonHeight, Shapes.southeast,Direction.SOUTHEAST, pos8, gui);
		buttons.put(Direction.SOUTHEAST, b8);
		
	}

	@Override
	public void draw() {
		// the directionalPad doesn't draw anything when not hovered.
		
	}

	public void hover(int mouseX, int mouseY) {
		for(DirectionalButton button: buttons.values()){
			button.hover(mouseX, mouseY);
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
		for(DirectionalButton button: buttons.values()){
			button.mousePressed(mouseX, mouseY);
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
