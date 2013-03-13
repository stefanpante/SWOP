
//TODO: refactor to startNewGameHandler?
/**
 * 
 */
package handlers;

import java.util.ArrayList;

import javax.swing.UIManager;

import game.Game;
import gui.ApplicationWindow;
import player.Player;
import square.Square;
import utils.Coordinate;

/**
 * 
 * @author jonas, vreniers
 */
public class GameHandler extends Handler {
	
	public static final String WALLS_PROPERTY 				= "Walls";
	public static final String GRENADES_PROPERTY 			= "Grenades";	
	public static final String PLAYERS_PROPERTY 			= "Players";	
	public static final String CURRENT_PLAYER_PROPERTY 		= "CurrentPlayer";
	public static final String MESSAGE_PROPERTY 			= "Message";
	public static final String SQUARE_INVENTORY_PROPERTY	= "SquareInventory";
	public static final String PLAYER_INVENTORY_PROPERTY	= "PlayerInventory";
	public static final String END_TURN_PROPERTY 			= "EndTurnProperty";
	public static final String LIGHT_TRAILS_PROPERTY		= "LightTrails";
	
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
	}

	/**
     * Returns the moveHandler.
     * @return 	the moveHandler.
     */
    public MoveHandler getMoveHandler(){
    	return this.moveHandler;
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
