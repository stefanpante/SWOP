package gui;

import effect.Effect;
import game.Player;
import game.mode.CTFGameMode;
import grid.RandomGridBuilder;
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
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.RadioButton;
import controlP5.Textarea;
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

	/* The actions which are used by the buttons */
	public static final String PICKUP_ACTION = "pickup";
	public static final String USEITEM_ACTION = "useitem";
	public static final String ENDTURN_ACTION = "endTurn";
	public static final String STARTNEWGAME_ACTION = "startnewgame";
	public static final String GAMEMODE = "gamemode";
	public static final String CONFIRM = "yes";
	public static final String DENY = "no";
	
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

	/**
	 * The starting size of the applet.
	 */
	private int hSize = 710;
	private int vSize = 580;

	/**
	 * The default width and height of a message
	 */
	private int messageHeight = 125;
	private int messageWidth = 300;
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

	/**
	 * The messages which can be displayed
	 */
	private YesNoDialog endTurnDialog;
	private TimedMessage message;

	/**
	 * The current color of the player.
	 */
	private int currentColor;
	/**
	 * Boolean which is used to control the flow of the end turn
	 */
	private boolean endTurn = false;
	
	/**
	 * Used to determine the gameMode
	 */
	private int gameMode = GameHandler.RACEGAMEMODE;
	
	private Textarea warning;

	private String warningText = "You have done something wrong: \n " +
			"\t - A random grid has a minimum size of 10 x 10 \n " +
			"\t - If you have specified a file, please make sure it is correct \n " +
			"\t - The maximum number of players for a generated grid is 4 \n" +
			"\t   and the maximum amount for a grid from file is equal to \n" +
			"\t   the number of start positions specified in the file";
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
		squareInventory = new Inventory(155, 185, items, new PVector(530,25), "Square Inventory", this);
		playerInventory = new Inventory(155, 185, items, new PVector(530,225),"Player Inventory", this);

		setupButtons();
		initializeInput();

	}

	/**
	 * initializes the form to be displayed when the game is started.
	 */
	@SuppressWarnings("deprecation")
	private void initializeInput(){
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
		
		confirm = inputController.addButton("confirm");
		confirm.setPosition(hSize/4,390);
		confirm.setSize(hSize/2, 35);
		confirm.setColor(color);
		confirm.setColorLabel(OConstants.BLACK.getIntColor());
		confirm.setColorBackground(OConstants.LIGHTER_GREY.getIntColor());
	
		warning = inputController.addTextarea("Hallo");
		warning.setText(warningText);
		warning.setSize(hSize/2, 150);
		warning.setPosition(hSize/4, 430);
		warning.show();
		warning.setColorBackground(color(255, 200, 200));
		warning.setColorValue(color(199,0,0));
		warning.setColorActive(color(199,0,0));
		warning.setColor(color(199,0,0));
		warning.setLineHeight(18);
		warning.setColor(color);
		warning.hide();
		}

	/**
	 * starts a game with a grid from file.
	 */
	@SuppressWarnings("unused")
	private void pick(){
		FileDialog fd = new FileDialog(this.frame, "Choose your grid", FileDialog.LOAD);
		fd.setVisible(true);
		System.out.println(fd.getDirectory() + fd.getFile());
		setUpGame(fd.getDirectory() + fd.getFile());

	}

	/**
	 * Starts a game with a random grid.
	 */
	@SuppressWarnings("unused")
	private void confirm(){
		this.hCells = Integer.parseInt(widthGrid.getText()) ;
		this.vCells = Integer.parseInt(heightGrid.getText());
		setUpGame(Integer.parseInt(widthGrid.getText()), Integer.parseInt(heightGrid.getText()), 
				Integer.parseInt(numPlayers.getText()));
	}


	private void hideInput(){
		inputController.hide();
	}

	private void showInput(){
		inputController.show();
		if(gamemode.equals(GameHandler.RACEGAMEMODE)){
			numPlayers.hide();
		}
		warning.hide();
	}

	/**
	 * Sets up the buttons used by the actual game.
	 */
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

	private void setUpGame(int hCells, int vCells, int numOfPlayers){
		try{
			gameHandler = new GameHandler(this);
			gameHandler.startNewGame(hCells, vCells,numOfPlayers, gameMode);
			initInterface();
			hideInput();
			gameHandler.fireChanges();
		}catch(Exception exc){
			this.warning.show();
			exc.printStackTrace();
		}
	}

	private void setUpGame(String filePath){
		try{
			gameHandler = new GameHandler(this);
			gameHandler.startNewGame(filePath, Integer.parseInt(numPlayers.getText()), gameMode);
			hCells = gameHandler.getGame().getGrid().getHSize();
			vCells = gameHandler.getGame().getGrid().getVSize();
			initInterface();
			hideInput();
			gameHandler.fireChanges();
		}catch(Exception exc){
			this.warning.show();
			exc.printStackTrace();
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

	@Override
	public void mouseClicked(){
		if(!endTurn){
			for(GUIButton button: buttons){
				button.isPressed(mouseX, mouseY);
				grid.mousePressed(mouseX, mouseY);
				// Checks if the mouse is pressed on an inventory
				squareInventory.mousePressed(mouseX, mouseY);
				playerInventory.mousePressed(mouseX, mouseY);
			}
		}
		else{
			endTurnDialog.isPressed(mouseX, mouseY);
		}
	}

	private void drawMessages(){
		if(endTurnDialog != null){
			endTurnDialog.draw();
		}
		if(message != null){
			message.draw();
		}
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

	private void showRemainingActions(){
		fill(color(0));
		noStroke();
		textAlign(LEFT,LEFT);
		text("Remaining actions: " + gameHandler.getGame().getCurrentPlayer().getRemainingActions(), grid.getPosition().x + grid.getWidth() + OConstants.MARGIN*2, 490 );
	}



	private void startNewGame() {
		this.initialized = false;
		hSize = 710;
		vSize = 580;
		size(hSize,vSize);
		if(frame != null){
			frame.setSize(hSize, vSize);
			frame.setLocation(displayWidth/2 - hSize/2, displayHeight/2 - vSize/2);
		}
		this.showInput();

	}

	public void move(Direction direction){
		try{
            gameHandler.getMoveHandler().move(direction);
		}catch(Exception e){
			showException(e);
		}
	}
	
	private void pickUp(){
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

	private void useItem(){
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

	private  void endTurn(){
		endTurn = true;
		
		String message = " Do you really want to end your turn?";
		PVector pos = new PVector(hSize/2 - messageWidth/2, vSize/2 - messageHeight/2);
		endTurnDialog = new YesNoDialog(messageWidth, messageHeight, pos, message, this);
		endTurnDialog.setColor(currentColor);
		
		changePlayer();
	}
	
	private void endTurn(String value){
		if(value.equals(CONFIRM)){
			try{
				gameHandler.getEndTurnHandler().confirm(true);
				gameHandler.getEndTurnHandler().endTurn();
			}catch(Exception e){
				showException(e);
			}
		}
		endTurnDialog = null;
		endTurn = false;
		
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
	public void stop(){
		try{
			System.exit(0);
		}
		catch(Exception ignored){}		
	
	}

	private void changePlayer(){

		int id = gameHandler.getGame().getCurrentPlayer().getID();
		currentColor = OConstants.PLAYERCOLORS[id -1].getIntColor();

		for(GUIButton button: buttons)
			button.setColor(currentColor);

		playerInventory.getLabel().setColor(currentColor);
		squareInventory.getLabel().setColor(currentColor);
		grid.getLabel().setColor(currentColor);

	}

	private void showException(Exception exc){
		exc.printStackTrace();
		String text = exc.getMessage();
		showMessage(text);
	}
	
	private void showMessage(String text){
		PVector position = new PVector(hSize/2 - messageWidth/2, vSize/2 - messageHeight/2);
		message = new TimedMessage(messageWidth, messageHeight, position, text, 4, this);
		message.getLabel().setColor(currentColor);
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
		case Handler.EFFECTS_PROPERTY: grid.updateEffects((HashMap<Coordinate, ArrayList<Effect>>) o);
		break;
		case Handler.CURRENT_POSITION_PROPERTY: grid.setCurrentPlayer((Coordinate) o );
		changePlayer();
		break;
		case Handler.SQUARE_INVENTORY_PROPERTY: squareInventory.setItems((ArrayList<Item>) o);
		break;
		case Handler.PLAYER_INVENTORY_PROPERTY: playerInventory.setItems((ArrayList<Item>) o);
		break;
		case Handler.END_TURN_PROPERTY:	endTurn();
		break;
		case Handler.MESSAGE_PROPERTY: 	showMessage((String) o);
		break;
		case Handler.WIN_PROPERTY: 		showMessage((String) o);
		break;
		case Handler.LOSE_PROPERTY : 	showMessage((String) o);
		break;
		case Handler.SQUARES_PROPERTY: grid.adjustGrid((ArrayList<Coordinate>) o);
		break;
		case Handler.ITEMS_PROPERTY:	HashMap<Coordinate, ArrayList<Item>> items = (HashMap<Coordinate,ArrayList<Item>>) o;
		grid.updateItems(items);
		break;
		case Handler.LIGHTTRAIL_PROPERTY:  grid.updateLightTrails((HashMap<Player, ArrayList<Coordinate>>) o );
		break;
		default:					break;
		}
	
	
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
		case CONFIRM : this.endTurn(command);
		break;
		case DENY:		this.endTurn(command);
		}
	}

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