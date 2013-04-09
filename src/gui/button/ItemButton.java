package gui.button;

import item.Item;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import util.OConstants;

public class ItemButton extends ShapeButton {

	/**
	 * The item connected to this button.
	 */
	private Item item;

	/**
	 * If the button is selected, it should be drawn different
	 */
	private boolean selected;

	/**
	 * The background color when the button is selected.
	 */
	private int selectedColor = OConstants.LIGHT_GREY;



	public ItemButton(float width, float height, PShape shape, PApplet gui) {
		super(width, height, shape, gui);
		selected = false;
		this.color = OConstants.LIGHTER_GREY;
		this.visible = true;
	}

	public ItemButton(float width, float height, PShape shape,
			PVector position, PApplet gui) {
		super(width, height, shape, position, gui);
		this.color = OConstants.LIGHTER_GREY;
		selected = false;
		this.visible = true;
		this.position = position;
	}

	@Override
	public void draw(){
		if(visible){
			
			gui.noStroke();
			gui.fill(color);

			if(selected){
				gui.fill(selectedColor);
			}

			gui.rect(position.x, position.y, width, height);
			gui.shape(shape, position.x + OConstants.MARGIN, position.y + OConstants.MARGIN, width - OConstants.MARGIN*2,
					height - OConstants.MARGIN*2);
		}
	}

	@Override
	public void hover(int mouseX, int mouseY){

	}

	public Item getItem(){
		return item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}

	public void setSelected(boolean selected){
		this.selected = selected;
	}

	public void setSelectedColor(int selectedColor){
		this.selectedColor = selectedColor;
	}
}
