package processing.button;

import item.Item;
import processing.OConstants;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class ItemButton extends ShapeButton {
	
	private Item item;
	private PVector position;
	private boolean selected;
	private int selectedColor = OConstants.LIGHT_GREY;
	
	
	
	public ItemButton(float width, float height, PShape shape, PApplet gui) {
		super(width, height, shape, gui);
		selected = false;
		this.color = OConstants.LIGHTER_GREY;
		this.position = new PVector();
	}

	public ItemButton(float width, float height, PShape shape,
			PVector position, PApplet gui) {
		super(width, height, shape, position, gui);
		this.color = OConstants.LIGHTER_GREY;
		selected = false;
		this.position = position;
	}

	@Override
	public void draw(){
		gui.noStroke();
		gui.fill(color);
		
		if(selected){
			gui.fill(selectedColor);
		}
		
		gui.rect(position.x, position.y, width, height);
		gui.shape(shape, position.x + OConstants.MARGIN, position.y + OConstants.MARGIN, width - OConstants.MARGIN*2,
				height - OConstants.MARGIN*2);
	}

	public void rollover(int mouseX, int mouseY){
		if(mouseHit(mouseX, mouseY)){
			gui.fill(selectedColor);
			gui.rect(position.x, position.y, width, height);
			gui.shape(shape, position.x + OConstants.MARGIN, position.y + OConstants.MARGIN, width - OConstants.MARGIN*2,
					height - OConstants.MARGIN*2);
		}
	}
	public Item getItem(){
		return item;
	}

	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		if(mouseX >= position.x && mouseX <= position.x + width){
			if(mouseY >= position.y && mouseY <= position.y + height){
				return true;
			}
		}
		
		return false;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
}
