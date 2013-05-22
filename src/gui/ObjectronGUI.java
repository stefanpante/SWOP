package gui;

import game.Player;
import game.mode.CTFGameMode;
import grid.RandomGridBuilder;
import gui.button.GUIButton;
import gui.button.TextButton;
import gui.message.Message;
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
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.RadioButton;
import controlP5.Textfield;
import controller.GameHandler;
import controller.Handler;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import util.Direction;
import util.Coordinate;
import util.OConstants;

@SuppressWarnings("serial")
public class ObjectronGUI extends PApplet implements PropertyChangeListener, ActionListener, ControlListener{

	//TODO: input van size van grid.
	//TODO: refactor numbers to other shit
	//TODO: message deftig

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
	private GameHandler gameHandler;

	/**
	 * The GUI representation of the inventories
	 */
	private Inventory playerInventory;
	private Inventory squareInventory;


	/**
	 * All the self implemented buttons in the game.
	 */
	private ArrayList<GUIButton> buttons;

	// The start size of the applet
	private int hSize = 710;
	private int vSize = 580;

	/**
	 * The standard font for the gui.
	 */
	private PFont standardFont;

	/**
	 * Buttons & controllers used in the initialisation of the game.
	 */
	private Button confirm;
	private Button filepick;
	private DropdownList gamemode;
	private Textfield widthGrid;
	private Textfield heightGrid;
	private Textfield numPlayers;

	/**
	 * Boolean which symbolizes if the game has been initialized
	 */
	private boolean initialized = false;
	
	/**
	 * Integer to store the number of horizontal/ vertical cells.
	 */
	private int hCells;
	private int vCells;
	
	private ArrayList<Message> messages;

	/**
	 * initializes the objectron gui
	 */
	@Override
	public void setup(){
		buttons = new ArrayList<GUIButton>();
		standardFont = new PFont(this.getFont(), true);
		// sets the size from the applet to a fourth of the screen.
		size(hSize, vSize);
		this.messages = new ArrayList<Message>();
		// Loads all the shapes used.
		@SuppressWarnings("unused")
		Shapes shapes = new Shapes(this);

		// inputController, used for input
		inputController = new ControlP5(this);
		//Sets up the grid for usage.
		this.grid = new GridGui(new PVector(25, 55), this, 500,500, 10, 10);

		// Sets up the inventory representation.
		ArrayList<Item> items = new ArrayList<Item>();
		squareInventory = new Inventory(155, 185, items, new PVector(530,25), "Square Inventory", this);
		playerInventory = new Inventory(155, 185, items, new PVector(530,225),"Player Inventory", this);

		setupButtons();
		initializeInput();

	}

	@SuppressWarnings("deprecation")
	public void initializeInput(){
		inputController.setFont(standardFont);
		CColor color = new CColor(OConstants.LIGHT_GREY.getIntColor(), 
				OConstants.WHITE.getIntColor(),OConstants.LIGHT_GREY.getIntColor(), 
				OConstants.BLACK.getIntColor(), OConstants.BLACK.getIntColor());
		widthGrid = inputController.addTextfield("Width of the grid");
		widthGrid.setPosition(hSize/4 ,100) ;
		widthGrid.setSize(hSize/2, 35);
		widthGrid.setAutoClear(false);
		widthGrid.setColor(color);
		widthGrid.setValue("" + RandomGridBuilder.MIN_HSIZE);
		widthGrid.setColorForeground(OConstants.LIGHT_GREY.getIntColor());
		widthGrid.setColorCursor(OConstants.LIGHT_GREY.getIntColor());
		
		heightGrid = inputController.addTextfield("Height of the grid");
		heightGrid.setPosition(hSize/4,170);
		heightGrid.setSize(hSize/2, 35);
		heightGrid.setAutoClear(false);
		heightGrid.setColor(color);
		heightGrid.setValue("" + RandomGridBuilder.MIN_HSIZE);
		heightGrid.setColorForeground(OConstants.LIGHT_GREY.getIntColor());
		heightGrid.setColorCursor(OConstants.LIGHT_GREY.getIntColor());

		numPlayers = inputController.addTextfield("Number of players");
		numPlayers.setPosition(hSize/4, 285);
		numPlayers.setSize(hSize/2, 35);
		numPlayers.setAutoClear(false);
		numPlayers.setValue("" + CTFGameMode.MIN_PLAYERS);
		numPlayers.setColor(color);
		numPlayers.setColorForeground(OConstants.LIGHT_GREY.getIntColor());
		this.numPlayers.setText("" +2);
		numPlayers.hide();
		
		heightGrid.setColorCursor(OConstants.LIGHT_GREY.getIntColor());
		gamemode = inputController.addDropdownList(GAMEMODE);
		gamemode.setPosition(hSize/4 - 1, 275);
		gamemode.setSize(hSize/2 +2, 35);
		gamemode.setHeight(110);
		gamemode.actAsPulldownMenu(true);
		gamemode.setItemHeight(35);
		gamemode.setBarHeight(35);
		gamemode.beginItems();
		gamemode.addItems(new String[]{"Race mode", "Capture the flag"});
		gamemode.endItems();
		gamemode.setColorLabel(OConstants.BLACK.getIntColor());
		gamemode.setColorBackground(OConstants.LIGHTER_GREY.getIntColor());
		gamemode.setColorForeground(OConstants.LIGHT_GREY.getIntColor());

		filepick = inputController.addButton("pick");
		filepick.setLabel("Pick grid from file");
		filepick.setPosition(hSize/4,350);
		filepick.setSize(hSize/2, 35);
		filepick.setColor(color);
		filepick.setColorLabel(OConstants.BLACK.getIntColor());
		filepick.setColorBackground(OConstants.LIGHTER_GREY.getIntColor());

		RadioButton rButton = inputController.addRadioButton("Gay");
		rButton.setPosition(hSize/4,500);
		rButton.setSize(hSize/2, 35);
		confirm = inputController.addButton("confirm");
		confirm.setPosition(hSize/4,390);
		confirm.setSize(hSize/2, 35);
		confirm.setColor(color);
		confirm.setColorLabel(OConstants.BLACK.getIntColor());
		confirm.setColorBackground(OConstants.LIGHTER_GREY.getIntColor());
	}

	public void pick(){
		FileDialog fd = new FileDialog(this.frame, "Choose your grid", FileDialog.LOAD);
		fd.setVisible(true);
		hideInput();
		setUpGame(fd.getDirectory() + fd.getFile());
		
	}

	public void confirm(){
		hideInput();
		this.hCells = Integer.parseInt(widthGrid.getText()) ;
		this.vCells = Integer.parseInt(heightGrid.getText());
		setUpGame(Integer.parseInt(widthGrid.getText()), Integer.parseInt(heightGrid.getText()), 
				Integer.parseInt(numPlayers.getText()));
	}


	public void hideInput(){
		inputController.hide();
	}

	public void showInput(){
		inputController.show();
	}

	public static final String PICKUP_ACTION = "pickup";
	public static final String USEITEM_ACTION = "useitem";
	public static final String ENDTURN_ACTION = "endTurn";
	public static final String STARTNEWGAME_ACTION = "startnewgame";
	public static final String GAMEMODE = "gamemode";

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

			drawInventories();
			drawButtons(); 
			drawMessages();
			showRemainingActions();
		}
	}
	
	public void drawMessages(){
		for(Message message: messages){
			message.draw();
		}
	}

	public void showRemainingActions(){
		fill(color(0));
		noStroke();
		textAlign(LEFT,LEFT);
		text("Remaining actions: " + gameHandler.getGame().getCurrentPlayer().getRemainingActions(), grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2, 490 );
	}

	
	
	private void setUpGame(int hCells, int vCells, int numOfPlayers){
		try{
			gameHandler = new GameHandler(this);
			gameHandler.startNewGame(hCells, vCells,numOfPlayers, gameMode);
			initInterface();
			gameHandler.fireChanges();
		}catch(Exception exc){
			this.showException(exc);
		}
	}
	
	private void setUpGame(String filePath){
		try{
		gameHandler = new GameHandler(this);
		//gameHandler.startNewGame(filePath, , currentPlayerColor);
		hCells = gameHandler.getGame().getGrid().getHSize();
		vCells = gameHandler.getGame().getGrid().getVSize();
		initInterface();
		gameHandler.fireChanges();
		}catch(Exception exc){
			this.showException(exc);
		}

	}

	private void initInterface(){
		int w = 60 * hCells;
		int h = 60 * vCells;
		if(h >= displayHeight - 150){
			float sw = (displayHeight - 150)/ vCells;
			h = displayHeight - 150;
			w = (int) (sw * hCells);

		}
		System.out.println("Init the new GridGUI");
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
		for(GUIButton button: buttons){
			button.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2);
		}

		playerInventory.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN);
		squareInventory.setX(grid.getPosition().x + grid.getWidth() + OConstants.MARGIN);
		// update the positions of the inventories and buttons.

		textFont(standardFont);
		gameHandler.populateGui();
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
			gameHandler.getMoveHandler().move(direction);
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
				gameHandler.getPickupHandler().pickUp(item);
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
				grid.getThrowPad().setIdentityDisc((IdentityDisc) item);
			}
			else{
				try{
					gameHandler.getUseItemHandler().useItem(item);
				}catch(Exception e){
					showException(e);
				}
			}
		}
	}

	public void endTurn(){
		try{
			gameHandler.getEndTurnHandler().endTurn();
		}catch(Exception e){
			showException(e);
		}
	}

	public void changePlayer(){

		int id = gameHandler.getGame().getCurrentPlayer().getID();
		int color = OConstants.PLAYERCOLORS[id -1].getIntColor();

		for(GUIButton button: buttons)
			button.setColor(color);
		
		playerInventory.getLabel().setColor(color);
		squareInventory.getLabel().setColor(color);
		grid.getLabel().setColor(color);

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
		case Handler.WALLS_PROPERTY:	grid.updateWalls((ArrayList<Coordinate>) o);
		break;
		case Handler.PLAYERS_PROPERTY:  grid.updatePlayers((HashMap<Player,Coordinate>) o);
		break;
		case Handler.POWER_FAILS_PROPERTY: grid.updatePowerFailures((ArrayList<Coordinate>) o);
		break;
		case Handler.LIGHT_TRAILS_PROPERTY: grid.updateLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
		break;
		case Handler.CURRENT_POSITION_PROPERTY: grid.setCurrentPlayer((Coordinate) o );
												changePlayer();
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
		grid.updateItems(items);
		break;
		case Handler.FORCEFIELD_PROPERTY: grid.updateForceFields((ArrayList<Coordinate>) o);

		default:					break;
		}


	}

	private boolean endTurn = false;

	@Override
	public void stop(){
		try{
			System.exit(0);
		}
		catch(Exception ignored){}		

	}

	private int messageHeight = 125;
	private int messageWidth = 300;


	private void showException(Exception exc){
		exc.printStackTrace();
		String message = exc.getMessage();
		PVector position = new PVector(hSize/2 - messageWidth/2, vSize/2 - messageHeight/2);
		//m = new TimedMessage(messageWidth, messageHeight, position, message, 4, this);
	}

	public void throwLaunchableItem(IdentityDisc identityDisc,
			Direction direction) {
		try{
			gameHandler.getThrowLaunchableHandler().throwLaunchable(identityDisc, direction);
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
	
	private int gameMode = 0;
	@Override
	public void controlEvent(ControlEvent arg0) {
		int mode = (int) arg0.getValue();
		switch(mode){
			case 0: 			numPlayers.hide();
								this.gameMode = GameHandler.RACEGAMEMODE;
								this.numPlayers.setText("" +2);
								break;
								
			case 1:				numPlayers.show();
								this.gameMode = GameHandler.CTFGAMEMODE;
								break;
			default:			break;
		}
		
		
	}

}