package processing;

import item.Item;
import item.LightGrenade;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import controller.GameHandler;
import controller.ProcessingHandler;

import player.Player;
import processing.button.TextButton;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
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
	 * The start new game button
	 */
	private TextButton startNewGameButton;

	private int hSize = 710;

	private int vSize = 580;

	private Label gridLabel;
	private Label squareInventoryLabel;
	private Label playerInventoryLabel;
	/**
	 * initializes the objectron gui
	 */
	public void setup(){

		// sets the size from the applet to a fourth of the screen.
		size(hSize, vSize);
		// Loads all the shapes used.
		@SuppressWarnings("unused")
		Shapes shapes = new Shapes(this);

		//Sets up the grid for usage.
		this.grid = new GridGui(new PVector(25, 55), this, 500,500, 10, 10);
		
		// Creates the directionalPad to be used for the movement of the player.
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new LightGrenade());
		items.add(new ChargedDisc());
		items.add(new IdentityDisc());

		// Sets up the inventory representation.
		squareInventory = new Inventory(items,new PVector(530,55), this);
		playerInventory = new Inventory(items, new PVector(530,255), this);
		
		setupButtons();
		setupLabels();
		// Creates a new ProcessingHandler.
		obj = new ProcessingHandler(this);
		obj.startNewGame();



	}
	
	private void setupLabels(){
		this.gridLabel = new Label(495, 25, new PVector(25,25),"Player 1", this);
		this.gridLabel.setColor(OConstants.PLAYERBLUE);
		this.squareInventoryLabel = new Label(155, 25, new PVector(530,25),"Square Inventory", this);
		this.squareInventoryLabel.setColor(OConstants.PLAYERBLUE);
		this.playerInventoryLabel = new Label(155, 25, new PVector(530,225),"Player Inventory", this);
		this.playerInventoryLabel.setColor(OConstants.PLAYERBLUE);
	}
	
	private void setupButtons(){
		this.pickUpButton = new TextButton(145, 25, new PVector(535, 180), "pick up", this);
		this.pickUpButton.setColor(OConstants.PLAYERBLUE);
		this.useItemButton =new TextButton(145, 25, new PVector(535, 380), "use item", this);
		this.useItemButton.setColor(OConstants.PLAYERBLUE);
		this.endTurnButton = new TextButton(145, 25, new PVector(535, 525), "end turn", this);
		this.endTurnButton.setColor(OConstants.PLAYERBLUE);
		this.startNewGameButton = new TextButton(145, 25, new PVector(535, 495), "start new game", this);
		this.startNewGameButton.setColor(OConstants.PLAYERBLUE);
	}

	/**
	 * Draws the entire game
	 */
	public void draw(){

		// Sets the background color to white.
		background(color(255));
		// draws Everything
		grid.draw();
		grid.hover(mouseX, mouseY);

		drawLabels();
		drawInventories();
		drawButtons(); 

	}


	/**
	 * Draws the player Label on the screen.
	 */
	private void drawLabels(){

		this.gridLabel.draw();
		this.playerInventoryLabel.draw();
		this.squareInventoryLabel.draw();
	}

	private  void drawInventories(){
		// Draws the square inventory.
		squareInventory.draw();
		// Draws the player inventory.
		playerInventory.draw();

		// mouseOver on the square inventory
		squareInventory.hover(mouseX, mouseY);
		// mouseOver on the player inventory
		playerInventory.hover(mouseX, mouseY);
	}

	private void drawButtons(){
		
		// Draw the buttons
		useItemButton.draw();
		pickUpButton.draw();
		endTurnButton.draw();
		this.startNewGameButton.draw();
		
		// MouseOVer on the button
		useItemButton.hover(mouseX, mouseY);
		pickUpButton.hover(mouseX, mouseY);
		endTurnButton.hover(mouseX, mouseY);
		this.startNewGameButton.hover(mouseX, mouseY);
	}

	/**
	 * Called when the mouse button is pressed.
	 */
	public void mousePressed(){

		// Checks if the mouse is pressed on an inventory
		squareInventory.mousePressed(mouseX, mouseY);
		playerInventory.mousePressed(mouseX, mouseY);


	}

	public void pickUp(Item item){
		obj.getPickupHandler().pickUp(item);
	}

	public void useItem(Item item){
		obj.getUseItemHandler().useItem(item);
	}

	public void endTurn(){

	}
	
	public void changePlayer(){
		// set the color of the labels to the currentplayer color
		// set the color of the buttons to the currentPlayer
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
			ArrayList<Coordinate> players = (ArrayList<Coordinate>)o;
			this.grid.setPlayers(players);
		}else if(evt.getPropertyName().equals(GameHandler.POWER_FAILS_PROPERTY)){
			this.grid.setPowerFails((ArrayList<Coordinate>)o);
		}else if(evt.getPropertyName().equals(GameHandler.LIGHT_TRAILS_PROPERTY)) {
			this.grid.setLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
			//        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_PLAYER_PROPERTY)){
			//        	String playerName = (String)o;
			//        	if(!this.currentPlayerName.equals(playerName)){
			//        		this.currentPlayerName = playerName;
			//        		showMessage("It's now "+playerName+ "'s turn.");
			//        	}
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
		
		grid.resetGrid();

	}


	private void confirmEndTurn(String o) {
		// TODO Auto-generated method stub

	}

	private int mHeight = 125;
	private int mWidth = 300;
	private void showMessage(String string) {
		stroke(0, 30);
		fill(OConstants.LIGHTER_GREY);
		rect(hSize/2 - mWidth/2, vSize/2 - mHeight/2, mWidth, mHeight);

		noStroke();
		fill(OConstants.GREEN);
		rect(hSize/2 - mWidth/2+1, vSize/2 - mHeight/2+1, mWidth-1, 25);
		fill(color(255));
		textAlign(PConstants.LEFT, PConstants.CENTER);
		text("Message",hSize/2 - mWidth/2+5, vSize/2 - mHeight/2+1, mWidth-6, 22);

		fill(0, 90);
		textAlign(PConstants.CENTER, PConstants.CENTER);
		text(string,hSize/2 - mWidth/2+5, vSize/2 - mHeight/2+1 + 25, mWidth-6, 73);

	}





}
