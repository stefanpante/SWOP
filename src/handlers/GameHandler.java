
//TODO: refactor to startNewGameHandler?
/**
 * 
 */
package handlers;

import items.Item;

import java.util.ArrayList;

import javax.swing.UIManager;

import game.Game;
import gui.ApplicationWindow;
import player.Player;
import square.Direction;
import square.Square;
import utils.Coordinate;

/**
 * @author jonas
 *
 */
public class GameHandler extends Handler {
	
	public static final String WALLS_PROPERTY 			= "Walls";
	public static final String GRENADES_PROPERTY 		= "Grenades";	
	public static final String PLAYERS_PROPERTY 		= "Players";	
	public static final String CURRENT_PLAYER_PROPERTY 	= "CurrentPlayer";
	public static final Object MESSAGE_PROPERTY 		= "Message";
	
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.endTurnHandler = new EndTurnHandler(getGame());
    	this.moveHandler = new MoveHandler(getGame());
    	this.pickUpHandler = new PickUpHandler(getGame());
    	this.useItemHandler = new UseItemHandler(getGame());
    	populateGui();
    }
    
    /**
	 * 
	 */
	private void populateGui() {
    	ArrayList<Coordinate> grenades = new ArrayList<Coordinate>();
    	ArrayList<Coordinate> walls = new ArrayList<Coordinate>();
    	ArrayList<Coordinate> players = new ArrayList<Coordinate>();
    	for(Coordinate coordinate : getGame().getGrid().getAllCoordinates()){
    		Square square = getGame().getGrid().getSquare(coordinate);
    		if(square.getInventory().hasLightGrenade()){
    			grenades.add(coordinate);
    		}else if(square.isObstructed()){
    			walls.add(coordinate);
    		}
    			
    	}
    	for(Player player : getGame().getPlayers()){
    		players.add(getGame().getGrid().getCoordinate(player.getPosition()));
    	}
    	walls.removeAll(players);
    	firePropertyChange(GRENADES_PROPERTY, grenades);
    	firePropertyChange(WALLS_PROPERTY, walls);
    	firePropertyChange(PLAYERS_PROPERTY, players);
    	firePropertyChange(CURRENT_PLAYER_PROPERTY, getGame().getGrid().getCoordinate(getGame().getCurrentPlayer().getPosition()));
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
