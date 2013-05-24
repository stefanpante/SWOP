package gui.message;

import gui.ObjectronGUI;
import gui.button.TextButton;
import org.jetbrains.annotations.NotNull;
import processing.core.PVector;

public class YesNoDialog extends Message{

	
	@NotNull
    private final TextButton yes;
	@NotNull
    private final TextButton no;
	
	public YesNoDialog(float width, float height, @NotNull PVector position,
			String message, ObjectronGUI gui) {
		super(width, height, position, message, gui);
		float y = position.y + height - 35;
		float x = position.x + width/2;
		int color = getLabel().getColor();
		this.yes = new TextButton(width/5, new PVector(x - 10 - width/5, y), "yes", gui );
		yes.setColor(color);
		yes.addActionListener(gui);
		yes.setActionCommand(ObjectronGUI.CONFIRM);
		this.no = new TextButton(width/5, new PVector(x + 10, y), "no", gui );
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
	
	@NotNull
    public TextButton getYesButton(){
		return this.yes;
	}
	
	public void isPressed(int mouseX, int mouseY){
		yes.isPressed(mouseX, mouseY);
		no.isPressed(mouseX, mouseY);
	}
	
	@NotNull
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
