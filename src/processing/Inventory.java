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

	/**
	 * The items in this inventory representation
	 */
	private ArrayList<Item> items;
	
	/**
	 * the width of this inventory
	 */
	private float width;
	
	/**
	 * The height of this inventory
	 */
	private float height;
	
	/*
	 * The position of this inventory (topleft)
	 */
	private PVector position;
	
	/**
	 * the backgroundColor of the inventory
	 */
	private int backgroundColor;
	
	/*
	 * The last button which was pressed by the user.
	 */
	private ItemButton selectedButton;
	
	/**
	 * The list of buttons (representing the items.
	 */
	private ArrayList<ItemButton> buttons;
	
	/**
	 * Used for drawing.
	 */
	private PApplet gui;
	
	/**
	 * Constructs a new inventory object. Sets the position to (0,0)
	 * @param items the items in the inventory
	 * @param gui the PApplet used for drawing.
	 */
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
	
	/**
	 * Constructs a new Inventory object
	 * @param items		the items in the inventory
	 * @param position	the position of this inventory
	 * @param gui		The PApplet used for drawing.
	 */
	public Inventory(ArrayList<Item> items, PVector position, PApplet gui){
		this(items, gui);
		this.position = position;
		this.initialize();
	}

	/**
	 * creates all the buttons.
	 */
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

	/**
	 * Returns the PShape( the image) associated with a certain item.
	 * @param item
	 * @return
	 */
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

	/**
	 * Draws the inventory on the screen.
	 */
	@Override
	public void draw() {
		gui.noStroke();
		gui.fill(backgroundColor);
		gui.rect(position.x, position.y, width, height);
		for(ItemButton button: buttons){
			button.draw();
		}

	}

	/**
	 * what to draw when the mouse is over the inventory
	 */
	@Override
	public void mouseOver(int mouseX, int mouseY) {
		for(ItemButton button: buttons){
			button.mouseOver(mouseX, mouseY);
		}

	}

	/**
	 * Checks whether the mouse was pressed on the inventory.
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
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
	
	/**
	 * always returns false, the inventory is a container for objects.
	 */
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets the items of this inventory
	 * @param o
	 */
	public void setItems(ArrayList<Item> items) {
		// Sets the items in the inventory
		this.items = items;
		// inits the buttons.
		initialize();
		
	}
}
