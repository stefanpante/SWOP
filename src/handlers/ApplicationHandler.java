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
    	applicationHandler.start();
    }
    
    public void start(){
    	initialize();
    }
    
    private void initialize(){
    	// construction of grid nodig voor game
 
    	this.game = new Game();
    	
    	this.guiHandler = new GuiHandler(game);
    	this.endTurnHandler = new EndTurnHandler(game);
    	this.moveHandler = new MoveHandler(game);
    	this.pickUpHandler = new PickUpHandler(game);
    	this.useItemHandler = new UseItemHandler(game);
    }
}
