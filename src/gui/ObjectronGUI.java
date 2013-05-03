package gui;

import game.Player;
import gui.button.TextButton;
import item.IdentityDisc;
import item.Item;

import java.awt.FileDialog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;


import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.Textarea;
import controlP5.Textfield;
import controller.GameHandler;
import controller.Handler;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import square.Direction;
import square.power.Power;
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

	private TextButton yesButton;
	private TextButton noButton;

	private Label gridLabel;
	private Label squareInventoryLabel;
	private Label playerInventoryLabel;
	private PFont standardFont;

	private Button confirm;
	private Button filepick;

	private Textfield widthGrid;

	private Textfield heightGrid;

	private boolean initialized = false;

	private int hCells;

	private int vCells;

	private int currentPlayerColor = OConstants.PLAYERBLUE;

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
		
		initializeInput();
		//		initialized = true;



	}

	@SuppressWarnings("deprecation")
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
		confirm.setPosition(hSize/4,280);
		confirm.setSize(hSize/2, 35);
		confirm.setColor(color);
		confirm.setColorLabel(OConstants.WHITE);
		confirm.setColorBackground(OConstants.PLAYERBLUE);
		
		filepick = inputController.addButton("pick");
		filepick.setLabel("Pick grid from file");
		filepick.setPosition(hSize/4,240);
		filepick.setSize(hSize/2, 35);
		filepick.setColor(color);
		filepick.setColorLabel(OConstants.WHITE);
		filepick.setColorBackground(OConstants.PLAYERBLUE);
		
		area = inputController.addTextarea("Warning", "Please make sure your Gridfile is valid!", hSize/4, 320, hSize/2, 100);
		
		area.setColor(OConstants.PLAYERBLUE);
		area.setColorLabel(OConstants.PLAYERBLUE);
		area.setColorBackground(OConstants.WHITE);
		area.setBorderColor(OConstants.PLAYERBLUE);
		area.hide();
		 

	}
	
	Textarea area;
	
	public void pick(){
		FileDialog fd = new FileDialog(this.frame, "Choose your grid", FileDialog.LOAD);
		 fd.setVisible(true);
		 setUpGame(fd.getDirectory() + fd.getFile());
        hideInput();
	}
		

	public void hcells(String value){
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

	public void confirm(){
		// needed to get the width and height of the grid
		widthGrid.submit();
		heightGrid.submit();
		
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

		this.yesButton = new TextButton(75, 25, new PVector(hSize/2 - 80, vSize/2 + mHeight/2), "Yes", this);
		this.yesButton.setColor(OConstants.PLAYERBLUE);
		this.noButton = new TextButton(75,25, new PVector(hSize/2 + 5, vSize/2 + mHeight/2), "No", this);
		this.noButton.setColor(OConstants.PLAYERBLUE);

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
			if(!endTurn){
				grid.hover(mouseX, mouseY);
			}

			drawLabels();
			drawInventories();
			drawButtons(); 
			showRemainingActions();
			
			confirmEndTurn();

		}
		showMessage();
	}


	public void showRemainingActions(){
		fill(currentPlayerColor);
		noStroke();
		textAlign(LEFT,LEFT);
		text("Remaining actions: " + obj.getGame().getCurrentPlayer().getRemainingActions(), grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2, 490 );
	}

    private void setUpGame(String filePath){
    	area.show();
        obj = new GameHandler(this);
        obj.startNewGame(filePath);
        hCells = obj.getGame().getGrid().getHSize();
        vCells = obj.getGame().getGrid().getVSize();
        initInterface();
        obj.fireChanges();   

    }
	private void setUpGame(){

		obj = new GameHandler(this);
		obj.startNewGame(hCells, vCells);
        initInterface();
        obj.fireChanges();
	}

    private void initInterface(){
        int w = 60 * hCells;
        int h = 60 * vCells;
        if(h >= displayHeight - 150){
            float sw = (displayHeight - 150)/ vCells;
            h = displayHeight - 150;
            w = (int) (sw * hCells);

        }

        this.grid = new GridGui(new PVector(25,55), this, w, h, hCells, vCells);
        this.initialized = true;
        if( h < 550){
            h = 550;
        }
        size(w + 215, h + 125);
        if(this.frame != null){
            this.frame.setSize(w+215, h + 125);
        }
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
        obj.populateGui();
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

		if(!endTurn){
			// mouseOver on the square inventory
			squareInventory.hover(mouseX, mouseY);
			// mouseOver on the player inventory
			playerInventory.hover(mouseX, mouseY);
		}
	}

	private void drawButtons(){

		// Draw the buttons
		useItemButton.draw();
		pickUpButton.draw();
		endTurnButton.draw();
		this.startNewGameButton.draw();
		if(!endTurn){
			// MouseOVer on the button
			useItemButton.hover(mouseX, mouseY);
			pickUpButton.hover(mouseX, mouseY);
			endTurnButton.hover(mouseX, mouseY);
			this.startNewGameButton.hover(mouseX, mouseY);
		}
	}

	/**
	 * Called when the mouse button is pressed.
	 */
	@Override
	public void mousePressed(){
		if(!endTurn){
			grid.mousePressed(mouseX, mouseY);
			// Checks if the mouse is pressed on an inventory
			squareInventory.mousePressed(mouseX, mouseY);
			playerInventory.mousePressed(mouseX, mouseY);
		}
		buttonPressed();


	}

	private void buttonPressed(){
		if(!endTurn){
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
		}else{
			if(yesButton.mouseHit(mouseX, mouseY)){
				this.obj.getEndTurnHandler().confirm(true);
				this.endTurn();
				this.endTurn = false;
			}

			if(noButton.mouseHit(mouseX, mouseY)){
				this.obj.getEndTurnHandler().confirm(false);
				this.endTurn = false;
			}
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
			if(item instanceof IdentityDisc){
				grid.getDirectionalPad().setVisibility(false);
				grid.getThrowPad().setVisibility(true);
				grid.getThrowPad().setShape(Shapes.getShape(item));
				grid.getThrowPad().setIdentityDisc((IdentityDisc) item);
				message = "Choose a throw direction.";
				currentFrame = 0;
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
			yesButton.setColor(currentPlayerColor);
			noButton.setColor(currentPlayerColor);
		}catch(NullPointerException e){}

	}

	/**
	 * Accepts propertychanges.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		Object o = evt.getNewValue();
		
		if (evt.getPropertyName().equals(Handler.WALLS_PROPERTY)) {
			this.grid.setWalls((ArrayList<Coordinate>)o);
		}else if(evt.getPropertyName().equals(Handler.PLAYERS_PROPERTY)){
			HashMap<Player,Coordinate> players = (HashMap<Player,Coordinate>) o;
			this.grid.setPlayers(players);
		}else if(evt.getPropertyName().equals(Handler.POWER_FAILS_PROPERTY)){
			this.grid.setPowerFails((ArrayList<Coordinate>) o);
		}else if(evt.getPropertyName().equals(Handler.LIGHT_TRAILS_PROPERTY)) {
			this.grid.setLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
		}else if(evt.getPropertyName().equals(Handler.CURRENT_POSITION_PROPERTY)){
			this.grid.setCurrentPlayer((Coordinate)o);
			this.changePlayer();
		}else if(evt.getPropertyName().equals(Handler.SQUARE_INVENTORY_PROPERTY)){
			this.squareInventory.setItems((ArrayList<Item>) o);
		}else if(evt.getPropertyName().equals(Handler.PLAYER_INVENTORY_PROPERTY)){
			this.playerInventory.setItems((ArrayList<Item>) o);
		}else if(evt.getPropertyName().equals(Handler.END_TURN_PROPERTY)){
			endTurn = true;
		}else if(evt.getPropertyName().equals(Handler.MESSAGE_PROPERTY)){
			message = (String) o;
			currentFrame = 0;
		}else if(evt.getPropertyName().equals(Handler.WIN_PROPERTY)){
			String player = (String)o;
			message =  player+ " has won the game!";
			currentFrame = 0;
		}else if(evt.getPropertyName().equals(Handler.LOSE_PROPERTY)){
			String player = (String)o;
			message = player+ " has lost the game...";
			currentFrame = 0;
		}else if(evt.getPropertyName().equals(Handler.SQUARES_PROPERTY)){
			grid.adjustGrid((ArrayList<Coordinate>) o);
		}else if(evt.getPropertyName().equals(Handler.ITEMS_PROPERTY)){
		HashMap<Coordinate, ArrayList<Item>> items = (HashMap<Coordinate,ArrayList<Item>>) o;
			grid.setItems(items);
		}else if(evt.getPropertyName().equals(Handler.FORCEFIELD_PROPERTY)){
			grid.setForceFields((ArrayList<Coordinate>) o);
		}
		grid.updateItems();


	}

	private boolean endTurn = false;
	private void confirmEndTurn() {
		if(endTurn){
			yesButton.setVisibility(true);
			noButton.setVisibility(true);
			stroke(0, 30);
			fill(OConstants.LIGHTER_GREY);
			rect(hSize/2 - mWidth/2, vSize/2 - mHeight/2, mWidth, mHeight + 30);
			noStroke();
			fill(currentPlayerColor);
			rect(hSize/2 - mWidth/2+1, vSize/2 - mHeight/2+1, mWidth-1, 25);
			fill(color(255));
			textAlign(PConstants.LEFT, PConstants.CENTER);
			text("Message",hSize/2 - mWidth/2+5, vSize/2 - mHeight/2+1, mWidth-6, 22);

			fill(0,96);
			textAlign(PConstants.CENTER, PConstants.CENTER);
			text("Are you sure you want to end your turn",hSize/2 - mWidth/2+5, vSize/2 - mHeight/2+1 + 25, mWidth-6, 73);
			noButton.draw();
			yesButton.draw();
			noButton.hover(mouseX, mouseY);
			yesButton.hover(mouseX, mouseY);

		}

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

	public void throwLaunchableItem(IdentityDisc identityDisc,
			Direction direction) {
		try{
			obj.getThrowLaunchableHandler().throwLaunchable(identityDisc, direction);
		}
		catch(Exception e){
			showException(e);
		}

		grid.getDirectionalPad().setVisibility(true);
		grid.getThrowPad().setVisibility(false);

	}

}