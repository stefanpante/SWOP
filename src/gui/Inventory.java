package gui;

import gui.button.ItemButton;
import item.ForceFieldGenerator;
import item.Item;
import item.LightGrenade;
import item.Teleport;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import util.OConstants;


public class Inventory extends GUIElement{

	//TODO: Refactor to accept height and width in constructor.
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
	
	private Label label;

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
	public Inventory(ArrayList<Item> items, PVector position, String inventoryName, PApplet gui){
		super(155,185, position,gui);
		this.items = items;
		this.buttons = new ArrayList<ItemButton>();
		super.setColor(OConstants.LIGHTER_GREY);
		this.selectedButton = null;
		super.setPosition(position);
		this.label = new Label(155, 25, position, inventoryName, gui);
		this.initialize();
	}

	/**
	 * creates all the buttons.
	 */
	private void initialize() {
		buttons.clear();
		selectedButton = null;

		PVector pos = new PVector(position.x + OConstants.MARGIN, position.y + OConstants.MARGIN + 25);
        for (Item item : items) {
            if (item instanceof LightGrenade) {
                LightGrenade lg = (LightGrenade) item;
                if (lg.isActive() || lg.isDropped() || lg.isWornOut()) {
                    continue;
                }
            }

            if (item instanceof ForceFieldGenerator) {
                ForceFieldGenerator ff = (ForceFieldGenerator) item;
                if (ff.isDropped() || ff.isActive()) {
                    continue;
                }
            }
            // Add the button to the inventory.
            ItemButton button = new ItemButton(OConstants.SQUARE_WIDTH, OConstants.SQUARE_WIDTH, Shapes.getShape(item), pos, gui);
            button.setItem(item);
            if (!(item instanceof Teleport)) {
                buttons.add(button);
            }

            //next line when line is full.
            pos = new PVector(pos.x + OConstants.MARGIN + OConstants.SQUARE_WIDTH,
                    pos.y);
            if (pos.x >= (position.x + width )) {
                pos = new PVector(position.x + OConstants.MARGIN, pos.y + OConstants.MARGIN + OConstants.SQUARE_WIDTH);

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
		label.draw();
		for(ItemButton button: buttons){
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
	 * @param mouseX  the x position of the mouse
	 * @param mouseY  the y position of the mouse
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
		return false;
	}

	/**
	 * Sets the items of this inventory
	 * @param items the items in the inventory
	 */
	public void setItems(ArrayList<Item> items) {

		this.items = items;
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
