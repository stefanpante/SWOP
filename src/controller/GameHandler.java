package controller;

import game.Game;
import game.Player;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.RandomGridBuilder;
import gui.ObjectronGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;




import util.Coordinate;

public class GameHandler extends Handler {
	
	/**
	 * Property constants
	 */
	public static final String WALLS_PROPERTY 				= "Walls";
	public static final String GRENADES_PROPERTY 			= "Grenades";	
	public static final String PLAYERS_PROPERTY 			= "Players";	
	public static final String CURRENT_PLAYER_PROPERTY 		= "CurrentPlayer";
	public static final String CURRENT_POSITION_PROPERTY 	= "CurrentPosition";
	public static final String MESSAGE_PROPERTY 			= "Message";
	public static final String SQUARE_INVENTORY_PROPERTY	= "SquareInventory";
	public static final String PLAYER_INVENTORY_PROPERTY	= "PlayerInventory";
	public static final String END_TURN_PROPERTY 			= "EndTurnProperty";
	public static final String IDENTITY_DISK_PROPERTY		= "IdentityDisk";
	public static final String CHARGED_DISK_PROPERTY		= "ChargedIdentityDisk";
	public static final String TELEPORT_PROPERTY			= "Teleport";		
	public static final String LIGHT_TRAILS_PROPERTY		= "LightTrails";
	public static final String POWER_FAILS_PROPERTY			= "PowerFails";
	public static final String WIN_PROPERTY 				= "Win";
	public static final String LOSE_PROPERTY				= "Lose";
	public static final String SQUARES_PROPERTY				= "squares";
	public static final String FORCEFIELD_PROPERTY			= "forcefield";
	private ObjectronGUI objectronGUI;
	
	
	public GameHandler(ObjectronGUI objectronGUI){
		this.objectronGUI = objectronGUI;
	}

	/* Handlers */
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private TurnHandler turnHandler;
	private ThrowLaunchableHandler throwLaunchableHandler;
	
    /**
     * Used to start a new game.
     */
    public void startNewGame(int hCells, int vCells){
		try {
			createGame(hCells,vCells);
			initHandlers();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    public void startNewGame(String filename){
    	try{
    		createGame(filename);
    		initHandlers();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void initHandlers(){
    	addPropertyChangeListener(objectronGUI);
    	this.endTurnHandler = new EndTurnHandler(getGame(), objectronGUI);
    	this.moveHandler = new MoveHandler(getGame(),objectronGUI);
    	this.pickUpHandler = new PickUpHandler(getGame(),objectronGUI);
    	this.useItemHandler = new UseItemHandler(getGame(),objectronGUI);
    	this.turnHandler = new TurnHandler(getGame(), objectronGUI);
    	this.throwLaunchableHandler = new ThrowLaunchableHandler(getGame(), objectronGUI);
        try {
            turnHandler.startTurn();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.populateGui();
    }
    
    /**
	 * Creates a list of coordinates where grenades, walls and players are located.
	 * This is information that the GUI can use.
	 */
	private void populateGui() {
		HashMap<String, Object> properties = getProperties();
		HashMap<Player,Coordinate> players = getPlayerLocations();
		
		firePropertyChange(SQUARES_PROPERTY, properties.get(SQUARES_PROPERTY));
    	firePropertyChange(WALLS_PROPERTY, properties.get(WALLS_PROPERTY));
    	firePropertyChange(PLAYERS_PROPERTY, players);
    	fireChanges();
	}

	/**
     * Returns the moveHandler.
     * @return 	the moveHandler.
     */
    public MoveHandler getMoveHandler(){
    	return this.moveHandler;
    }
    
	/**
     * Returns the turnHandler.
     * @return 	the moveHandler.
     */
    public TurnHandler getTurnHandler(){
    	return this.turnHandler;
    }
    
    /**
     * Returns the pickUpHandler.
     * @return 	the pickUpHandler.
     */
    public PickUpHandler getPickupHandler(){
    	return this.pickUpHandler;
    }
    
    /**
     * Returns the useItemHandler.
     * @return	the useItemHandler
     */
    public UseItemHandler getUseItemHandler(){
    	return this.useItemHandler;
    }
    
    /**
     * Returns the endTurnHandler.
     * @return 	the endTurnHandler.
     */
    public EndTurnHandler getEndTurnHandler(){
    	return this.endTurnHandler;
    }
    /**
     * Returns the throwLaunchableHandler.
     * @return	the throwLaunchableHandler.
     */
    public ThrowLaunchableHandler getThrowLaunchableHandler(){
    	return this.throwLaunchableHandler;
    }
    
	/**
	 * @param hSize
	 * @param vSize
	 */
	public void createGame(int hSize, int vSize) {
		RandomGridBuilder builder = new RandomGridBuilder(hSize, vSize);
		setGame(new Game(builder.getGrid()));
	}
	
	public void createGame(String filename){
		FileGridBuilder builder;
		try {
			builder = new FileGridBuilder(filename);
			setGame(new Game(builder.getGrid()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
