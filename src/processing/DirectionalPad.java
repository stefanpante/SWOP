package processing;

import java.util.ArrayList;

import processing.button.DirectionalButton;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import square.Direction;

public class DirectionalPad implements Drawable{

	private PApplet gui;
	/**
	 * Should be the position of the player.
	 */
	private PVector position;
	private int height;
	private int width;
	private ArrayList<DirectionalButton> buttons;
	
	
	public DirectionalPad(PApplet gui) {
		this.gui = gui;
		this.position = new PVector();
		this.height = 145;
		this.width = 145;
		initButtons();
	}
	
	public DirectionalPad(PVector position, PApplet gui){
		this(gui);
		this.position = position;
		initButtons();
	}

	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setWidth(int width){
		this.width = width;
		
	}
	private void initButtons() {
		int buttonWidth = (width - OConstants.MARGIN*2)/3;
		int buttonHeight = (height - OConstants.MARGIN*2)/3;
		buttons = new ArrayList<DirectionalButton>();
		
		
		
		PVector pos1 = new PVector(position.x, position.y - OConstants.MARGIN - buttonHeight);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.north,Direction.NORTH, pos1, gui));
		
		PVector pos2 = new PVector(position.x, position.y + OConstants.MARGIN + buttonHeight);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.south,Direction.SOUTH, pos2, gui));
		
		PVector pos3 = new PVector(position.x - OConstants.MARGIN - buttonWidth, position.y);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.west,Direction.WEST,pos3, gui));
		
		PVector pos4 = new PVector(position.x + OConstants.MARGIN + buttonWidth, position.y);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.east,Direction.EAST, pos4, gui));
		
		PVector pos5 = new PVector(pos4.x, pos1.y);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.northeast,Direction.NORTHEAST, pos5, gui));
		
		PVector pos6 = new PVector(pos3.x, pos1.y);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.northwest,Direction.NORTHWEST, pos6, gui));
		
		PVector pos7 = new PVector(pos3.x, pos2.y );
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.southwest,Direction.SOUTHWEST, pos7, gui));
		
		
		PVector pos8 = new PVector(pos4.x,pos2.y);
		buttons.add(new DirectionalButton(buttonWidth, buttonHeight, Shapes.southeast,Direction.SOUTHEAST, pos8, gui));
		
	}

	@Override
	public void draw() {
		for(DirectionalButton button: buttons){
			//button.draw();
		}
		
	}

	@Override
	public void mouseOver(int mouseX, int mouseY) {
		for(DirectionalButton button: buttons){
			button.mouseOver(mouseX, mouseY);
		}
		
	}

	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void mousePressed(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}


}
