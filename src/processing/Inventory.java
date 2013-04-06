package processing;

import item.Item;
import item.LightGrenade;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;

import java.util.ArrayList;

import processing.button.ItemButton;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;


public class Inventory implements Drawable {

	private ArrayList<Item> items;
	private float width;
	private float height;
	private PVector position;
	private int backgroundColor;
	private ItemButton selectedButton;
	private ArrayList<ItemButton> buttons;
	private PApplet gui;
	
	public Inventory(ArrayList<Item> items, PApplet gui) {
		this.items = items;
		this.position = new PVector();
		this.buttons = new ArrayList<ItemButton>();
		this.gui = gui;
		this.backgroundColor = OConstants.LIGHTER_GREY;
		this.width = 155;
		this.height = 155;
		this.selectedButton = null;
		this.initialize();
	}
	
	public Inventory(ArrayList<Item> items, PVector position, PApplet gui){
		this(items, gui);
		this.position = position;
		this.initialize();
	}

	//public ItemButton(float width, float height, PShape shape, PVector position, PApplet gui)
	private void initialize() {
		buttons.clear();
		PVector pos = new PVector(position.x + OConstants.MARGIN, position.y + OConstants.MARGIN);
		for(int i = 0; i < items.size(); i++){
			Item item = items.get(i);			
			// Add the button to the inventory.
			ItemButton button = new ItemButton(OConstants.SQUARE_WIDTH, OConstants.SQUARE_WIDTH, getShape(item), pos, gui);
			buttons.add(button);
			
			//next line when line is full.
			pos = new PVector(pos.x + OConstants.MARGIN + OConstants.SQUARE_WIDTH, 
					pos.y);
			if(pos.x >= (position.x + width)){
				pos = new PVector(position.x + OConstants.MARGIN, pos.y + OConstants.MARGIN + OConstants.SQUARE_WIDTH );
				
			}
		}
		
	}

	private PShape getShape(Item item) {
		if(item instanceof LightGrenade){
			return Shapes.lightgrenade;
		}
		
		if(item instanceof IdentityDisc ){
			return Shapes.identityDisc;
		}
		
		if(item instanceof ChargedDisc){
			return Shapes.chargedIdentityDisc;
		}
		
		return null;
	}

	@Override
	public void draw() {
		gui.noStroke();
		gui.fill(backgroundColor);
		gui.rect(position.x, position.y, width, height);
		for(ItemButton button: buttons){
			button.draw();
		}

	}

	@Override
	public void mouseOver(int mouseX, int mouseY) {
		for(ItemButton button: buttons){
			button.rollover(mouseX, mouseY);
		}

	}

	public void mousePressed(int mouseX, int mouseY){
		for(ItemButton button: buttons){
			if(button.mouseHit(mouseX, mouseY)){
				if(selectedButton != null)
					selectedButton.setSelected(false);
				selectedButton = button;
				button.setSelected(true);
			}
		}
	}
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return false;
	}


}
