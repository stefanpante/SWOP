/**
 * 
 */
package handlers;

import game.Game;

/**
 * @author jonas
 *
 */
public class ApplicationHandler extends Handler {
	
	private GuiHandler guiHandler;
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private Game game;
	
    public static void main(String[] args) {
    	ApplicationHandler applicationHandler = new ApplicationHandler();
    	applicationHandler.initialize();
    }
    
    public void initialize(){
    	this.guiHandler = new GuiHandler(this);
		start();
    }
    
    private void start(){    
    	this.guiHandler.setGame(game);
    	this.endTurnHandler = new EndTurnHandler(game);
    	this.moveHandler = new MoveHandler(game);
    	this.pickUpHandler = new PickUpHandler(game);
    	this.useItemHandler = new UseItemHandler(game);
    }
    public GuiHandler getGuiHandler(){
    	return guiHandler;
    	
    }
    public MoveHandler getMoveHandler(){
    	return this.moveHandler;
    }
    
    public PickUpHandler getPickupHandler(){
    	return this.pickUpHandler;
    }

	/**
	 * Create a game based on the given dimensions of the grid
	 * 
	 * @param 	hSize
	 * 			Horizontal size of the game's grid
	 * @param 	vSize
	 * 			Vertical size of the game's grid
	 */
	public void createGame(int hSize, int vSize) {
		this.game = new Game(hSize, vSize);
	}
}
