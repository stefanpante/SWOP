package controller;

import game.Game;

import java.util.ArrayList;


import processing.ObjectronGUI;


import util.Coordinate;

public class ProcessingHandler extends Handler {
	
	public static final String WALLS_PROPERTY 				= "Walls";
	public static final String GRENADES_PROPERTY 			= "Grenades";	
	public static final String PLAYERS_PROPERTY 			= "Players";	
	public static final String CURRENT_PLAYER_PROPERTY 		= "CurrentPlayer";
	public static final String CURRENT_POSITION_PROPERTY 	= "CurrentPosition";
	public static final String MESSAGE_PROPERTY 			= "Message";
	public static final String SQUARE_INVENTORY_PROPERTY	= "SquareInventory";
	public static final String PLAYER_INVENTORY_PROPERTY	= "PlayerInventory";
	public static final String END_TURN_PROPERTY 			= "EndTurnProperty";
	public static final String LIGHT_TRAILS_PROPERTY		= "LightTrails";
	public static final String POWER_FAILS_PROPERTY			= "PowerFails";
	public static final String WIN_PROPERTY 				= "Win";
	public static final String LOSE_PROPERTY				= "Lose";
	private ObjectronGUI objectronGUI;
	
	
	public ProcessingHandler(ObjectronGUI objectronGUI){
		this.objectronGUI = objectronGUI;
		startNewGame();
	}
	/**
	 *  Initializes the game handler
	 * 
	 */
	public ProcessingHandler() {
		super(null);
	}

	/* Handlers */
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private TurnHandler turnHandler;
	
    /**
     * Used to start a new game.
     */
    public void startNewGame(){
		try {
			createGame(10,10);
			addPropertyChangeListener(objectronGUI);
	    	this.endTurnHandler = new EndTurnHandler(getGame(), objectronGUI);
	    	this.moveHandler = new MoveHandler(getGame(),objectronGUI);
	    	this.pickUpHandler = new PickUpHandler(getGame(),objectronGUI);
	    	this.useItemHandler = new UseItemHandler(getGame(),objectronGUI);
	    	this.turnHandler = new TurnHandler(getGame(), objectronGUI);
	    	turnHandler.startTurn();
	    	this.populateGui();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    /**
	 * Creates a list of coordinates where grenades, walls and players are located.
	 * This is information that the GUI can use.
	 */
	private void populateGui() {
    	ArrayList<Coordinate> grenades = super.getGrenadeLocations();
    	ArrayList<Coordinate> walls = super.getWallLocations();
    	ArrayList<Coordinate> players = super.getPlayerLocations();
    	walls.removeAll(players);
    	firePropertyChange(GRENADES_PROPERTY, grenades);
    	firePropertyChange(WALLS_PROPERTY, walls);
    	firePropertyChange(PLAYERS_PROPERTY, players);
    	firePropertyChange(CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
    	firePropertyChange(LIGHT_TRAILS_PROPERTY, super.getLightTrailLocations());
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
	 * @param hSize
	 * @param vSize
	 */
	public void createGame(int hSize, int vSize) {
		setGame(new Game(10, 10));
	}

}
