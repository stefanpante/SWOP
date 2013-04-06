package processing;

import item.Item;
import item.LightGrenade;
import item.launchable.IdentityDisc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import controlP5.Bang;
import controlP5.ControlP5;
import controlP5.Group;
import controller.GameHandler;
import controller.ProcessingHandler;

import player.Player;
import processing.button.TextButton;
import processing.core.PApplet;
import processing.core.PVector;
import square.Direction;
import util.Coordinate;

public class ObjectronGUI extends PApplet implements PropertyChangeListener{

	/**
	 * SearialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The grid representation of the game.
	 */
	private GridGui grid;
	
	/**
	 * The processingHandler to send changes to the game model.
	 */
	private ProcessingHandler obj;
	
	/**
	 * The directionalPad to control the movement of the player.
	 */
	private DirectionalPad directionalpad;

	/**
	 * The name of the currentPlayer
	 */
	private String currentPlayerName;

	/**
	 * The GUI representation of the player inventory
	 */
	private Inventory playerInventory;

	/**
	 * The GUI representation of the square inventory
	 */
	private Inventory squareInventory;
	
	/**
	 * The useitem button
	 */
	private TextButton useItemButton;
	
	/**
	 * The pickUpItem button
	 */
	private TextButton pickUpButton;
	
	/**
	 * The endTurnButton
	 */
	private TextButton endTurnButton;

	/**
	 * initializes the objectron gui
	 */
	public void setup(){
		
		// sets the size from the applet to a fourth of the screen.
		size(710, 580);
		
		// Loads all the shapes used.
		@SuppressWarnings("unused")
		Shapes shapes = new Shapes(this);
		
		//Sets up the grid for usage.
		this.grid = new GridGui(new PVector(25, 55), this, 500,500, 10, 10);
		System.out.println(color(113,151,0));
		// Creates a new ProcessingHandler.
		obj = new ProcessingHandler(this);
		
		// Creates the directionalPad to be used for the movement of the player.
        directionalpad = new DirectionalPad(new PVector(25, 55), this);
        
        // Sets up the inventory representation.
        squareInventory = new Inventory(new ArrayList<Item>(),new PVector(530,55), this);
        playerInventory = new Inventory(new ArrayList<Item>(), new PVector(530,255), this);
        
        // Sets up the buttons  float width, float height, PVector position, String text,  PApplet gui
        this.pickUpButton = new TextButton(145, 25, new PVector(535, 180), "pick up", this);
        this.useItemButton =new TextButton(145, 25, new PVector(535, 380), "use item", this);
        this.endTurnButton = new TextButton(145, 25, new PVector(535, 525), "end turn", this);
        // the current player's name.
        this.currentPlayerName = "Player 1";
        
        
        
	}
	
	/**
	 * Draws the entire game
	 */
	public void draw(){
		
		// Sets the background color to white.
		background(color(255));
		
		// Draws the grid.
		grid.draw();
		
		// Draws the controls for moving the player.
		directionalpad.draw();
		
		// Draws the square inventory.
		squareInventory.draw();
		
		// Draws the player inventory.
		playerInventory.draw();
		
		// Shows the player label.
		this.drawLabels();
		
		// mouseOver on the grid.
		grid.mouseOver(mouseX, mouseY);

		// mouseOver on the directionalpad
		directionalpad.mouseOver(mouseX, mouseY);
		
		// mouseOver on the square inventory
		squareInventory.mouseOver(mouseX, mouseY);
		
		// mouseOver on the player inventory
		playerInventory.mouseOver(mouseX, mouseY);
		
		// Draw the buttons
		useItemButton.draw();
		pickUpButton.draw();
		endTurnButton.draw();
		
		// MouseOVer on the button
		useItemButton.mouseOver(mouseX, mouseY);
		pickUpButton.mouseOver(mouseX, mouseY);
		endTurnButton.mouseOver(mouseX, mouseY);
		
		
		
	}
	
	/**
	 * Draws the player Label on the screen.
	 */
	public void drawLabels(){
		
		// no stroke around the shapes drawn.
		this.noStroke();
		
		// sets the color of the label to baby blue.
		this.fill(OConstants.GREEN);
		
		// draws the background of the labels. //FIXME: Should move to grid and inventories.
		this.rect(25, 25, 495, 25);
		this.rect(530, 25, 155, 25);
		this.rect(530, 225, 155, 25);
		
		// changes the color to white ( color of the text)
		this.fill(color(255));
		
		// sets the text size.
		this.textSize(16);
		
		// alignment of the box.
		this.textAlign(LEFT, CENTER);
		
		// draws the text on the screen.
		this.text(currentPlayerName, 30, 22, 490,25);
		this.text("Square Inventory", 535, 22, 145,25);
		this.text("Player Inventory", 535, 222, 145,25);
	}
	
	/**
	 * Called when the mouse button is pressed.
	 */
	public void mousePressed(){
		
		// Checks if the mouse is pressed on an inventory
		squareInventory.mousePressed(mouseX, mouseY);
		playerInventory.mousePressed(mouseX, mouseY);
		
		// Checks if the directionalpad is pressed
		directionalpad.mousePressed(mouseX, mouseY);
		
	}
	
	public void pickUp(Item item){
		obj.getPickupHandler().pickUp(item);
	}
	
	public void useItem(Item item){
		obj.getUseItemHandler().useItem(item);
	}
	
	public void endTurn(){
		
	}

	/**
	 * Accepts propertychanges.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		Object o = evt.getNewValue();
		if(o == null) System.out.println("Event object is null");
        if (evt.getPropertyName().equals(GameHandler.WALLS_PROPERTY)) {
        	this.grid.setWalls((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.GRENADES_PROPERTY)){
        	this.grid.setGrenades((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.PLAYERS_PROPERTY)){
        	System.out.println("Player positions set");
        	this.grid.setPlayers((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.POWER_FAILS_PROPERTY)){
        	this.grid.setPowerFails((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.LIGHT_TRAILS_PROPERTY)) {
        	this.grid.setLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_PLAYER_PROPERTY)){
        	String playerName = (String)o;
        	if(!this.currentPlayerName.equals(playerName)){
        		this.currentPlayerName = playerName;
        		showMessage("It's now "+playerName+ "'s turn.");
        	}
        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_POSITION_PROPERTY)){
        	this.grid.setCurrentPlayer((Coordinate)o);
//        }else if(evt.getPropertyName().equals(GameHandler.SQUARE_INVENTORY_PROPERTY)){
//        	this.squareInventory.setItem((ArrayList<Item>) o);
//        }else if(evt.getPropertyName().equals(GameHandler.PLAYER_INVENTORY_PROPERTY)){
//        	this.playerInventory.setItem((ArrayList<Item>) o);
        }else if(evt.getPropertyName().equals(GameHandler.END_TURN_PROPERTY)){
        	confirmEndTurn((String)o);
        }else if(evt.getPropertyName().equals(GameHandler.MESSAGE_PROPERTY)){
        	JOptionPane.showMessageDialog(frame, (String)o);
        }else if(evt.getPropertyName().equals(GameHandler.WIN_PROPERTY)){
        	String player = (String)o;
        	JOptionPane.showMessageDialog(frame, player+ " has won the game!");
        }else if(evt.getPropertyName().equals(GameHandler.LOSE_PROPERTY)){
        	String player = (String)o;
        	JOptionPane.showMessageDialog(frame, player+ " has lost the game...");
        }
		
	}
	
	
	private void confirmEndTurn(String o) {
		// TODO Auto-generated method stub
		
	}

	private void showMessage(String string) {
		// TODO Auto-generated method stub
		
	}
	
	


}
