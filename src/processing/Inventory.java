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
	private PVector position;
	private Item selectedItem;
	private ArrayList<ItemButton> buttons;
	private PApplet gui;
	
	public Inventory(ArrayList<Item> items, PApplet gui) {
		this.items = items;
		this.position = new PVector();
		this.buttons = new ArrayList<ItemButton>();
		this.gui = gui;
		this.selectedItem = null;
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
					pos.y + OConstants.SQUARE_WIDTH + OConstants.MARGIN);
			if(pos.x >= (position.x - OConstants.MARGIN - OConstants.SQUARE_WIDTH)){
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseOver(int mouseX, int mouseY) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
