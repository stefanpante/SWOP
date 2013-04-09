package gui;

import gui.button.TextButton;
import item.Item;
import item.LightGrenade;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;
import item.launchable.LaunchableItem;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.Textfield;
import controller.GameHandler;
import player.Player;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import square.Direction;
import util.Coordinate;
import util.OConstants;

public class ObjectronGUI extends PApplet implements PropertyChangeListener{

	/**
	 * SearialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Used to input the size of the grid.
	 */
	private ControlP5 inputController;

	private ThrowPad throwPad;
	/**
	 * The grid representation of the game.
	 */
	private GridGui grid;

	/**
	 * The processingHandler to send changes to the game model.
	 */
	private GameHandler obj;


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
	
	/**
	 * The button to throw an item.
	 */
	private int hSize = 710;

	private int vSize = 580;

	private Label gridLabel;
	private Label squareInventoryLabel;
	private Label playerInventoryLabel;
	private PFont standardFont;

	/**
	 * initializes the objectron gui
	 */
	@Override
	public void setup(){
		standardFont = new PFont(this.getFont(), true);
		// sets the size from the applet to a fourth of the screen.
		size(hSize, vSize);
		// Loads all the shapes used.
		@SuppressWarnings("unused")
		Shapes shapes = new Shapes(this);

		// inputController, used for input
		inputController = new ControlP5(this);
		//Sets up the grid for usage.
		this.grid = new GridGui(new PVector(25, 55), this, 500,500, 10, 10);

		// Sets up the inventory representation.
		ArrayList<Item> items = new ArrayList<Item>();
		squareInventory = new Inventory(items,new PVector(530,55), this);
		playerInventory = new Inventory(items, new PVector(530,255), this);

		setupButtons();
		setupLabels();
		// Creates a new ProcessingHandler.
		obj = new GameHandler(this);
		obj.startNewGame(10,10);

		initializeInput();
//		initialized = true;



	}

	private Textfield widthGrid;
	private Textfield heightGrid;
	private Button confirm;
	public void initializeInput(){
		inputController.setFont(standardFont);
		CColor color = new CColor(OConstants.PLAYERBLUE, OConstants.WHITE,OConstants.PLAYERBLUE, OConstants.PLAYERBLUE, OConstants.PLAYERBLUE);
		widthGrid = inputController.addTextfield("hcells");
		widthGrid.setPosition(hSize/4 ,100) ;
		widthGrid.setSize(hSize/2, 35);
		widthGrid.setAutoClear(false);
		widthGrid.setColor(color);
		
		widthGrid.setLabel("Width of the grid");
		widthGrid.setValue("" + 10);
		widthGrid.setColorCursor(OConstants.PLAYERBLUE);
		heightGrid = inputController.addTextfield("vcells");
		heightGrid.setPosition(hSize/4,170);
		heightGrid.setSize(hSize/2, 35);
		heightGrid.setAutoClear(false);
		heightGrid.setColor(color);
		heightGrid.setLabel("Height of grid");
		heightGrid.setValue("" + 10);
		heightGrid.setColorCursor(OConstants.PLAYERBLUE);
		confirm = inputController.addButton("confirm");
		confirm.setPosition(hSize/4,240);
		confirm.setSize(hSize/2, 35);
		confirm.setColor(color);
		confirm.setColorLabel(OConstants.WHITE);
		confirm.setColorBackground(OConstants.PLAYERBLUE);

	}

	private int hCells;
	public void hcells(String value){
		System.out.println(value);
		try{
			hCells = Integer.parseInt(value);
			if(hCells < 10){
				message = "number of cells needs to be equal to or larger than 10.";
				currentFrame = 0;
				hCells = 0;
			}

		}catch(Exception e){
			message = "You need to input a number";
			currentFrame = 0;
		}

	}
	private int vCells;
	public void vcells(String value){
		try{
			vCells = Integer.parseInt(value);
			if(vCells < 10){
				message = "number of cells needs to be equal to or larger than 10.";
				currentFrame = 0;
				vCells = 0;
			}

		}catch(Exception e){
			message = "You need to input a number";
			currentFrame = 0;
		}
		

	}
	
	private boolean initialized = false;;
	
	public void confirm(){
		// needed to get the values of the
		widthGrid.submit();
		heightGrid.submit();
		System.out.println();
		System.out.println("the Grid size" + hCells + " x " + vCells );
		hideInput();
		setUpGame();
	}


	public void hideInput(){
		inputController.hide();

	}
	
	public void showInput(){
		inputController.show();
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
		
		this.endTurnButton = new TextButton(145, 25, new PVector(535, 415), "end turn", this);
		this.endTurnButton.setColor(OConstants.PLAYERBLUE);
		
		this.startNewGameButton = new TextButton(145, 25, new PVector(535, 445), "start new game", this);
		this.startNewGameButton.setColor(OConstants.PLAYERBLUE);
		
		//this.throwButton = new TextButton()
		
	}

	/**
	 * Draws the entire game
	 */
	@Override
	public void draw(){

		// Sets the background color to white.
		background(color(255));
		// draws Everything
		if(initialized){
			grid.draw();
			grid.hover(mouseX, mouseY);
	
			drawLabels();
			drawInventories();
			drawButtons(); 
			showRemainingActions();
			showMessage();
		
		}
	}
	
	
	public void showRemainingActions(){
		fill(currentPlayerColor);
		noStroke();
		textAlign(LEFT,LEFT);
		text("Remaining actions: " + obj.getGame().getCurrentPlayer().getRemainingActions(), grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2, 490 );
	}
	private void setUpGame(){
		
		int w = 50 * hCells;
		int h = 50 * vCells;
		if(h >= displayHeight - 150){
			float sw = (displayHeight - 150)/ vCells;
			h = displayHeight - 150;
			w = (int) (sw * hCells);
			
		}
		
		this.grid = new GridGui(new PVector(25,55), this, w, h, hCells, vCells);
		this.initialized = true;

		size(w + 215, h + 50);
		gridLabel.setWidth(grid.getWidth() - OConstants.MARGIN);
		squareInventoryLabel.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN);
		playerInventoryLabel.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN);
		endTurnButton.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2);
		startNewGameButton.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2);
		pickUpButton.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2);
		useItemButton.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2);
		playerInventory.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN);
		squareInventory.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN);
		// update the positions of the inventories and buttons.
		
		textFont(standardFont);
		obj.startNewGame(hCells, vCells);
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
	@Override
	public void mousePressed(){

		grid.mousePressed(mouseX, mouseY);
		// Checks if the mouse is pressed on an inventory
		squareInventory.mousePressed(mouseX, mouseY);
		playerInventory.mousePressed(mouseX, mouseY);

		buttonPressed();


	}

	private void buttonPressed(){
		if(startNewGameButton.mouseHit(mouseX, mouseY)){
			this.startNewGame();
		}

		if(endTurnButton.mouseHit(mouseX, mouseY)){
			this.endTurn();
		}

		if(useItemButton.mouseHit(mouseX, mouseY)){
			this.useItem();
		}
		if(pickUpButton.mouseHit(mouseX, mouseY)){
			this.pickUp();
		}
	}

	private void startNewGame() {
		this.initialized = false;
		this.showInput();
		
	}

	public void move(Direction direction){
		try{
			obj.getMoveHandler().move(direction);
		}catch(Exception e){
			showException(e);
		}
	}
	public void pickUp(){
		Item item = squareInventory.getSelectedItem();
		if(item == null){
			currentFrame = 0;
			message = "You have no item selected";
			currentFrame = 0;
		}
		else{
			try{
				obj.getPickupHandler().pickUp(item);
			}catch(Exception e){
				showException(e);
			}
		}
	}

	public void useItem(){
		Item item = playerInventory.getSelectedItem();
		if(item == null){
			currentFrame = 0;
		}
		else{
			if(item instanceof LaunchableItem){
				grid.getDirectionalPad().setVisibility(false);
				grid.getThrowPad().setVisibility(true);
				grid.getThrowPad().setShape(Shapes.getShape(item));
				grid.getThrowPad().setLaunchableItem((LaunchableItem)item);
			}
			else{
				try{
					obj.getUseItemHandler().useItem(item);
				}catch(Exception e){
					showException(e);
				}
			}
		}
	}

	public void endTurn(){
		try{
			obj.getEndTurnHandler().endTurn();
		}catch(Exception e){
			showException(e);
		}
	}

	private int currentPlayerColor = OConstants.PLAYERBLUE;
	public void changePlayer(){
		try{
			if(obj  == null){
			}
			if(obj.getGame().getCurrentPlayer().getID() == 1){
				currentPlayerColor = OConstants.PLAYERBLUE;
			}
			else{
				currentPlayerColor = OConstants.PLAYERRED;
			}
			gridLabel.setColor(currentPlayerColor);
			squareInventoryLabel.setColor(currentPlayerColor);
			playerInventoryLabel.setColor(currentPlayerColor);
			useItemButton.setColor(currentPlayerColor);
			pickUpButton.setColor(currentPlayerColor);
			endTurnButton.setColor(currentPlayerColor);
			startNewGameButton.setColor(currentPlayerColor);
		}catch(NullPointerException e){}

	}

	/**
	 * Accepts propertychanges.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		Object o = evt.getNewValue();
		if (evt.getPropertyName().equals(GameHandler.WALLS_PROPERTY)) {
			this.grid.setWalls((ArrayList<Coordinate>)o);
		}else if(evt.getPropertyName().equals(GameHandler.GRENADES_PROPERTY)){
			this.grid.setGrenades((ArrayList<Coordinate>)o);
		}else if(evt.getPropertyName().equals(GameHandler.PLAYERS_PROPERTY)){
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
			this.changePlayer();
		}else if(evt.getPropertyName().equals(GameHandler.SQUARE_INVENTORY_PROPERTY)){
			this.squareInventory.setItems((ArrayList<Item>) o);
		}else if(evt.getPropertyName().equals(GameHandler.PLAYER_INVENTORY_PROPERTY)){
			this.playerInventory.setItems((ArrayList<Item>) o);
		}else if(evt.getPropertyName().equals(GameHandler.END_TURN_PROPERTY)){
			confirmEndTurn((String)o);
		}else if(evt.getPropertyName().equals(GameHandler.MESSAGE_PROPERTY)){
			message = (String) o;
			currentFrame = 0;
		}else if(evt.getPropertyName().equals(GameHandler.WIN_PROPERTY)){
			String player = (String)o;
			message =  player+ " has won the game!";
			currentFrame = 0;
		}else if(evt.getPropertyName().equals(GameHandler.LOSE_PROPERTY)){
			String player = (String)o;
			message = player+ " has lost the game...";
			currentFrame = 0;
		}else if(evt.getPropertyName().equals(GameHandler.TELEPORT_PROPERTY)){
			grid.setTeleport((ArrayList<Coordinate>) o);
		}else if(evt.getPropertyName().equals(GameHandler.IDENTITY_DISK_PROPERTY)){
			grid.setDiscs((ArrayList<Coordinate>) o);
		}else if(evt.getPropertyName().equals(GameHandler.CHARGED_DISK_PROPERTY)){
			grid.setChargedDiscs((ArrayList<Coordinate>) o);
		}
		grid.resetGrid();


	}


	private void confirmEndTurn(String o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(){
		try{
			System.exit(0);
		}
		catch(Exception e)
		{}		

	}

	private int mHeight = 125;
	private int mWidth = 300;
	private int currentFrame = 100;
	private int endFrame = 50;
	private void showMessage() {
		if(currentFrame < endFrame){
			stroke(0, 30);
			fill(OConstants.LIGHTER_GREY);
			rect(hSize/2 - mWidth/2, vSize/2 - mHeight/2, mWidth, mHeight);

			noStroke();
			fill(currentPlayerColor);
			rect(hSize/2 - mWidth/2+1, vSize/2 - mHeight/2+1, mWidth-1, 25);
			fill(color(255));
			textAlign(PConstants.LEFT, PConstants.CENTER);
			text("Message",hSize/2 - mWidth/2+5, vSize/2 - mHeight/2+1, mWidth-6, 22);

			fill(0,96);
			textAlign(PConstants.CENTER, PConstants.CENTER);
			text(message,hSize/2 - mWidth/2+5, vSize/2 - mHeight/2+1 + 25, mWidth-6, 73);

			currentFrame++;
		}

	}

	private String message = "";
	private void showException(Exception exc){
		exc.printStackTrace();
		currentFrame = 0;
		message = exc.getMessage();
	}

	public void throwLaunchableItem(LaunchableItem launchable,
			Direction direction) {
		try{
			obj.getThrowLaunchableHandler().throwLaunchable(launchable, direction);
		}
		catch(Exception e){
			showException(e);
		}
		
		grid.getDirectionalPad().setVisibility(true);
		grid.getThrowPad().setVisibility(false);
		
	}





}
