package gui;

import gui.button.ItemButton;
import item.ChargedIdentityDisc;
import item.ForceFieldGenerator;
import item.IdentityDisc;
import item.Item;
import item.LightGrenade;
import item.Teleport;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import util.OConstants;


public class Inventory extends GUIElement{

	/**
	 * The items in this inventory representation
	 */
	private ArrayList<Item> items;


	/*
	 * The last button which was pressed by the user.
	 */
	private ItemButton selectedButton;

	/**
	 * The list of buttons (representing the items.
	 */
	private ArrayList<ItemButton> buttons;

	/**
	 * Constructs a new inventory object. Sets the position to (0,0)
	 * @param items the items in the inventory
	 * @param gui the PApplet used for drawing.
	 */
	public Inventory(ArrayList<Item> items, PApplet gui) {
		// float height, float width, PVector position, PApplet gui
		super(155,155, new PVector(),gui);
		this.items = items;
		this.buttons = new ArrayList<ItemButton>();
		super.setColor(OConstants.LIGHTER_GREY);
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
		super.setPosition(position);
		this.initialize();
	}

	/**
	 * creates all the buttons.
	 */
	private void initialize() {
		buttons.clear();
		selectedButton = null;

		PVector pos = new PVector(position.x + OConstants.MARGIN, position.y + OConstants.MARGIN);
		for(int i = 0; i < items.size(); i++){
			Item item = items.get(i);
			if(item instanceof LightGrenade){
				LightGrenade lg = (LightGrenade) item;
				if(lg.isActive() || lg.isDropped() || lg.isWornOut()){
					continue;
				}
			}
			
			if(item instanceof ForceFieldGenerator){
				ForceFieldGenerator ff = (ForceFieldGenerator) item;
				if(ff.isDropped() || ff.isActive()){
					continue;
				}
			}
			// Add the button to the inventory.
			ItemButton button = new ItemButton(OConstants.SQUARE_WIDTH, OConstants.SQUARE_WIDTH, Shapes.getShape(item), pos, gui);
			button.setItem(item);
			if(!(item instanceof Teleport)){
				buttons.add(button);
			}

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

	/**
	 * Draws the inventory on the screen.
	 */
	@Override
	public void draw() {
		gui.noStroke();
		gui.fill(color);
		gui.rect(position.x, position.y, width, height);
		for(ItemButton button: buttons){
			if(button == null){
			}
			button.draw();
		}

	}

	/**
	 * what to draw when the mouse is over the inventory
	 */
	public void hover(int mouseX, int mouseY) {
		for(ItemButton button: buttons){
			button.hover(mouseX, mouseY);
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
				break;
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

	public Item getSelectedItem() {
		if(selectedButton == null){
			System.out.println("null mothafuckas");
			return null;
		}
		return selectedButton.getItem();
	}

	public void removeItem(Item item){
		items.remove(item);
		initialize();
	}
}
