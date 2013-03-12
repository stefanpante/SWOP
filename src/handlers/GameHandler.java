
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
			window.setVisible();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	this.endTurnHandler = new EndTurnHandler(getGame());
    	this.moveHandler = new MoveHandler(getGame());
    	this.pickUpHandler = new PickUpHandler(getGame());
    	this.useItemHandler = new UseItemHandler(getGame());
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
		
		ApplicationWindow.MODEL.setPlayer1(getGame().getGrid().getCoordinate(getGame().getPlayer(0).getPosition()));
		ApplicationWindow.MODEL.setPlayer2(getGame().getGrid().getCoordinate(getGame().getPlayer(1).getPosition()));
	}
	
	
}
