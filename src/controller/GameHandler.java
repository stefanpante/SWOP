
package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.UIManager;

import game.Game;
import gui.ApplicationWindow;
import util.Coordinate;

/**
 * 
 * @author jonas, vreniers
 */
public class GameHandler extends Handler {
	
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
	public static final String IDENTITY_DISK_PROPERTY		= "IdentityDisk";
	public static final String TELEPORT_PROPERTY			= "Teleport";	
	public static final String WIN_PROPERTY 				= "Win";
	public static final String LOSE_PROPERTY				= "Lose";
	
	/**
	 *  Initializes the game handler
	 * 
	 */
	public GameHandler() {
		super(null);
	}

	/* Handlers */
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private TurnHandler turnHandler;
	
	
	/* Application Window */
	
    public static void main(String[] args) {
    	GameHandler gameHandler = new GameHandler();
    	gameHandler.startNewGame();
    }
    
    /**
     * Used to start a new game.
     */
    public void startNewGame(){
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			ApplicationWindow window = new ApplicationWindow(this);
			addPropertyChangeListener(window);
			window.setVisible();
			
	    	this.endTurnHandler = new EndTurnHandler(getGame(), window);
	    	this.moveHandler = new MoveHandler(getGame(),window);
	    	this.pickUpHandler = new PickUpHandler(getGame(),window);
	    	this.useItemHandler = new UseItemHandler(getGame(),window);
	    	this.turnHandler = new TurnHandler(getGame(), window);
	    	
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
		HashMap<String, ArrayList<Coordinate>> properties = getProperties();
		ArrayList<Coordinate> walls = properties.get(WALLS_PROPERTY);
		ArrayList<Coordinate> players = getPlayerLocations();
    	walls.removeAll(players);
    	firePropertyChange(WALLS_PROPERTY, walls);
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
	 * @param hSize
	 * @param vSize
	 */
	public void createGame(int hSize, int vSize) {
		setGame(new Game(hSize, vSize));
	}
}
