
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
import utils.Coordinate2D;

/**
 * @author jonas
 *
 */
public class GameHandler extends Handler {
	
	public GameHandler() {
		super(null);
	}

	/* Handlers */
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	
	/* Application Window */
	private ApplicationWindow applicationWindow;
	
    public static void main(String[] args) {
    	GameHandler gameHandler = new GameHandler();
    	gameHandler.startNewGame();
    }
    
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
    
    public MoveHandler getMoveHandler(){
    	return this.moveHandler;
    }
    
    public PickUpHandler getPickupHandler(){
    	return this.pickUpHandler;
    }
    
    public UseItemHandler getUseItemHandler(){
    	return this.useItemHandler;
    }
    
    public EndTurnHandler getEndTurnHandler(){
    	return this.endTurnHandler;
    }
	
	/**
	 * Gets a coordinate-based representation of the walls in this game
	 * 
	 * @return	An ArrayList of coordinates indicating walls
	 */
	public ArrayList<Coordinate2D> getWallsRepresentation(){
		ArrayList<Coordinate2D> wallCoor = new ArrayList<Coordinate2D>();
		int y = getGame().getGrid().getVSize() -1;
		int x = 0;
		Square s = getGame().getPlayer(1).getStartPosition();
		Square n = s;
		
		while(true){
			while(true){
				if(n.isObstructed())
					wallCoor.add(new Coordinate2D(x,y));
				try{
					n = n.getNeighbor(Direction.EAST);
					x++;
				} catch(Exception e){
					break;
				}
			}
			try{
				s = s.getNeighbor(Direction.NORTH);
				n = s;
				y--;
			}
			catch(Exception e){
				break;
			}
			x = 0;
		}
		return wallCoor;
	}

	/**
	 * @param hSize
	 * @param vSize
	 */
	public void createGame(int hSize, int vSize) {
		setGame(new Game(hSize, vSize));
	}
	
	
}
