package gui.message;

import processing.core.PApplet;
import processing.core.PVector;
import gui.ObjectronGUI;
import gui.button.TextButton;

public class YesNoDialog extends Message{

	
	private TextButton yes;
	private TextButton no;
	
	public YesNoDialog(float width, float height, PVector position,
			String message, ObjectronGUI gui) {
		super(width, height, position, message, gui);
		float y = position.y + height - 35;
		float x = position.x + width/2;
		int color = getLabel().getColor();
		this.yes = new TextButton(width/5, 25, new PVector(x - 10 - width/5, y), "yes", gui );
		yes.setColor(color);
		yes.addActionListener(gui);
		yes.setActionCommand(ObjectronGUI.CONFIRM);
		this.no = new TextButton(width/5, 25, new PVector(x + 10, y), "no", gui );
		no.addActionListener(gui);
		no.setActionCommand(ObjectronGUI.DENY);
		no.setColor(color);
	}
	
	public void draw(){
		if(isVisible()){
			super.draw();
			yes.draw();
			no.draw();
		}
	}
	
	public TextButton getYesButton(){
		return this.yes;
	}
	
	public void isPressed(int mouseX, int mouseY){
		yes.isPressed(mouseX, mouseY);
		no.isPressed(mouseX, mouseY);
	}
	
	public TextButton getNoButton(){
		return this.no;
	}
	@Override
	public void setColor(int color){
		this.yes.setColor(color);
		this.no.setColor(color);
		this.getLabel().setColor(color);
	}
	
}
