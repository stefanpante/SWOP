package gui.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import gui.GUIElement;
import processing.core.PApplet;
import processing.core.PVector;
import util.OConstants;

public abstract class GUIButton extends GUIElement {


	/**
	 * The actionListeners for the button
	 */
	private ArrayList<ActionListener> actionListeners;
	
	/**
	 * The actionCommand for this button
	 */
	
	private String actionCommand;
	/**
	 * The color of the button when the mouse hovered.
	 */
	protected int rolloverColor;
	
	/**
	 *  Whether or not the button is visible.
	 */
	protected boolean visible;
	
	/**
	 * constructs a new button, the position is set to (0,0)
	 * @param width		the width for the button.
	 * @param height	the height for the button;
	 * @param gui		the PApplet to draw the button.
	 */
	public GUIButton(float width, float height, PApplet gui) {
		//float height, float width, PVector position, PApplet gui
		super(width, height, new PVector(), gui);
		super.setColor(OConstants.LIGHT_GREY);
		this.actionListeners = new ArrayList<ActionListener>();
		this.rolloverColor = OConstants.LIGHTER_GREY;

	}
	
	/**
	 * constructs a new button.
	 * @param width		the width for the button.
	 * @param height	the height for the button.
	 * @param position	the position for the button.
	 * @param gui		the PApplet for the button.
	 */
	public GUIButton(float width, float height, PVector position,PApplet gui) {
		this(width, height, gui);
		super.setPosition(position);
	}
	
	
	/**
	 * Returns the rollover color for the button.
	 */
	public int getRolloverColor(){
		return this.rolloverColor;
	}
	
	/**
	 * Sets the rollover color for the button.
	 * @param color   the rollover color
	 */
	public void setRolloverColor(int color){
		this.rolloverColor = color;
		
	}
	
	/**
	 * Returns whether the given coordinates are over the button.
	 */
	@Override
	public boolean mouseHit(int mouseX, int mouseY){
		if(mouseX >= position.x && mouseX <= position.x + width){
			if(mouseY >= position.y && mouseY <= position.y + height){
				return true;
			}
		}
		
		return false;
	}
	
	public void isPressed(int mouseX, int mouseY){
		if(mouseHit(mouseX, mouseY)){
			notifyActionListeners();
		}
	}
	
	private void notifyActionListeners(){
		ActionEvent evt = new ActionEvent(this,ActionEvent.ACTION_PERFORMED, actionCommand);
		for(ActionListener actionListener: actionListeners){
			actionListener.actionPerformed(evt);
		}
	}
	
	public abstract void hover(int mouseX, int mouseY);
	
	public void addActionListener(ActionListener listener){
		this.actionListeners.add(listener);
	}
	
	public void removeActionListener(ActionListener listener){
		this.actionListeners.remove(listener);
	}
	
	public String getActionCommand(){
		return this.actionCommand;
	}
	
	public void setActionCommand(String actionCommand){
		this.actionCommand = actionCommand;
	}
}
