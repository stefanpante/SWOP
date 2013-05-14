package gui;

import game.Player;
import gui.button.GUIButton;
import gui.button.TextButton;
import gui.message.Message;
import gui.message.TimedMessage;
import gui.message.YesNoDialog;
import item.IdentityDisc;
import item.Item;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;


import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.Textarea;
import controlP5.Textfield;
import controller.GameHandler;
import controller.Handler;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import square.Direction;
import util.Coordinate;
import util.OConstants;

public class ObjectronGUI extends PApplet implements PropertyChangeListener, ActionListener{

	//TODO: input van size van grid.
	//TODO: message deftig
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
	 * All the self implemented buttons in the game.
	 */
	private ArrayList<GUIButton> buttons;

	private int hSize = 710;

	private int vSize = 580;
	
	private Label gridLabel;
	private PFont standardFont;

	private Button confirm;
	private Button filepick;
	private DropdownList gamemode;

	private Textfield widthGrid;

	private Textfield heightGrid;

	private boolean initialized = false;

	private int hCells;

	private int vCells;

	private int currentPlayerColor = OConstants.LIGHT_GREY;

	/**
	 * initializes the objectron gui
	 */
	@Override
	public void setup(){
		buttons = new ArrayList<GUIButton>();
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
		squareInventory = new Inventory(items,new PVector(530,25), "Square Inventory", this);
		playerInventory = new Inventory(items, new PVector(530,225),"Player Inventory", this);

		setupButtons();
		setupLabels();

		initializeInput();



	}

	@SuppressWarnings("deprecation")
	public void initializeInput(){
		inputController.setFont(standardFont);
		CColor color = new CColor(OConstants.LIGHT_GREY, OConstants.WHITE,OConstants.LIGHT_GREY, OConstants.LIGHT_GREY, OConstants.LIGHT_GREY);
		widthGrid = inputController.addTextfield("hcells");
		widthGrid.setPosition(hSize/4 ,100) ;
		widthGrid.setSize(hSize/2, 35);
		widthGrid.setAutoClear(false);
		widthGrid.setColor(color);

		widthGrid.setLabel("Width of the grid");
		widthGrid.setValue("" + 10);
		widthGrid.setColorCursor(OConstants.LIGHT_GREY);
		heightGrid = inputController.addTextfield("vcells");
		heightGrid.setPosition(hSize/4,170);
		heightGrid.setSize(hSize/2, 35);
		heightGrid.setAutoClear(false);
		heightGrid.setColor(color);
		heightGrid.setLabel("Height of grid");
		heightGrid.setValue("" + 10);
		heightGrid.setColorCursor(OConstants.LIGHT_GREY);

		gamemode = inputController.addDropdownList("gamemode");
		gamemode.setPosition(hSize/4, 240);
		gamemode.setWidth(hSize/2);
		gamemode.getValueLabel().setHeight(35);

		gamemode.actAsPulldownMenu(true);
		gamemode.addItems(new String[]{"Race mode", "Capture the flag"});

		filepick = inputController.addButton("pick");
		filepick.setLabel("Pick grid from file");
		filepick.setPosition(hSize/4,280);
		filepick.setSize(hSize/2, 35);
		filepick.setColor(color);
		filepick.setColorLabel(OConstants.WHITE);
		filepick.setColorBackground(OConstants.LIGHT_GREY);

		confirm = inputController.addButton("confirm");
		confirm.setPosition(hSize/4,320);
		confirm.setSize(hSize/2, 35);
		confirm.setColor(color);
		confirm.setColorLabel(OConstants.WHITE);
		confirm.setColorBackground(OConstants.LIGHT_GREY);

		area = inputController.addTextarea("Warning", "Please make sure your Gridfile is valid!", hSize/4, 320, hSize/2, 100);

		area.setColor(OConstants.LIGHT_GREY);
		area.setColorLabel(OConstants.LIGHT_GREY);
		area.setColorBackground(OConstants.WHITE);
		area.setBorderColor(OConstants.LIGHT_GREY);
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
			}

		}catch(Exception e){
		}

	}
	public void vcells(String value){
		try{
			vCells = Integer.parseInt(value);
			if(vCells < 10){
			}

		}catch(Exception e){
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
	}

	public static final String PICKUP_ACTION = "pickup";
	public static final String USEITEM_ACTION = "useitem";
	public static final String ENDTURN_ACTION = "endTurn";
	public static final String STARTNEWGAME_ACTION = "startnewgame";

	private void setupButtons(){


		TextButton pickUpButton = new TextButton(145, 25, new PVector(535, 180), "pick up", this);
		pickUpButton.setActionCommand(PICKUP_ACTION);

		TextButton useItemButton =new TextButton(145, 25, new PVector(535, 380), "use item", this);
		useItemButton.setActionCommand(USEITEM_ACTION);

		TextButton endTurnButton = new TextButton(145, 25, new PVector(535, 415), "end turn", this);
		endTurnButton.setActionCommand(ENDTURN_ACTION);

		TextButton startNewGameButton = new TextButton(145, 25, new PVector(535, 445), "start new game", this);
		startNewGameButton.setActionCommand(STARTNEWGAME_ACTION);
		
		buttons.add(pickUpButton);
		buttons.add(useItemButton);
		buttons.add(endTurnButton);
		buttons.add(startNewGameButton);

		for(GUIButton button: buttons)
			button.addActionListener(this);

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
		//m.draw();
	}

	Message m = new YesNoDialog(300, 300, new PVector(100,100), "Test",  this);

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
			this.frame.setLocation(frame.getLocation().x, 0);
		}
		gridLabel.setWidth(grid.getWidth() - OConstants.MARGIN);
		for(GUIButton button: buttons){
			button.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2);
		}

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
		for(GUIButton button: buttons){
			button.draw();
			button.hover(mouseX, mouseY);
		}
	}

	@Override
	public void mouseClicked(){
		for(GUIButton button: buttons){
			button.isPressed(mouseX, mouseY);
			grid.mousePressed(mouseX, mouseY);
			// Checks if the mouse is pressed on an inventory
			squareInventory.mousePressed(mouseX, mouseY);
			playerInventory.mousePressed(mouseX, mouseY);
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
		}
		else{
			if(item instanceof IdentityDisc){
				grid.getDirectionalPad().setVisibility(false);
				grid.getThrowPad().setVisibility(true);
				grid.getThrowPad().setShape(Shapes.getShape(item));
				grid.getThrowPad().setIdentityDisc((IdentityDisc) item);
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

		int id = obj.getGame().getCurrentPlayer().getID();
		int color = OConstants.PLAYERCOLORS[id -1];

		gridLabel.setColor(color);

		for(GUIButton button: buttons)
			button.setColor(color);

	}

	/**
	 * Accepts propertychanges.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object o = evt.getNewValue();

		switch(propertyName){
		case Handler.WALLS_PROPERTY:	grid.setWalls((ArrayList<Coordinate>) o);
		break;
		case Handler.PLAYERS_PROPERTY:  grid.setPlayers((HashMap<Player,Coordinate>) o);
		break;
		case Handler.POWER_FAILS_PROPERTY: grid.setPowerFails((ArrayList<Coordinate>) o);
		break;
		case Handler.LIGHT_TRAILS_PROPERTY: grid.updateLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
		break;
		case Handler.CURRENT_POSITION_PROPERTY: grid.setCurrentPlayer((Coordinate) o );
		break;
		case Handler.SQUARE_INVENTORY_PROPERTY: squareInventory.setItems((ArrayList<Item>) o);
		break;
		case Handler.PLAYER_INVENTORY_PROPERTY: playerInventory.setItems((ArrayList<Item>) o);
		break;
		case Handler.END_TURN_PROPERTY:	endTurn = true;
		break;
		case Handler.MESSAGE_PROPERTY: 	break;
		case Handler.WIN_PROPERTY: 		break;
		case Handler.LOSE_PROPERTY : 	break;
		case Handler.SQUARES_PROPERTY: grid.adjustGrid((ArrayList<Coordinate>) o);
		break;
		case Handler.ITEMS_PROPERTY:	HashMap<Coordinate, ArrayList<Item>> items = (HashMap<Coordinate,ArrayList<Item>>) o;
		grid.setItems(items);
		break;
		case Handler.FORCEFIELD_PROPERTY: grid.setForceFields((ArrayList<Coordinate>) o);

		default:					break;
		}

		grid.updateItems();


	}

	private boolean endTurn = false;
	private void confirmEndTurn() {
		if(endTurn){
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


	private void showException(Exception exc){
		exc.printStackTrace();
		String message = exc.getMessage();
		PVector position = new PVector(hSize/2 - mWidth/2, vSize/2 - mHeight/2);
		m = new TimedMessage(mWidth, mHeight, position, message, 4, this);
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

	@Override
	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		switch(command){
		case PICKUP_ACTION:		this.pickUp();
		break;
		case ENDTURN_ACTION:	this.endTurn();
		break;
		case USEITEM_ACTION:	this.useItem();
		break;
		case STARTNEWGAME_ACTION: this.startNewGame();
		break;	
		}
	}

}