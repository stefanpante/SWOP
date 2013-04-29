/**
 * 
 */
package command.action;

import command.AbstractGameCommand;

import game.Game;

/**
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public abstract class ActionCommand extends AbstractGameCommand {
		
	public ActionCommand(Game game) {
		super(game);
	}
	
	@Override
	public void execute() throws Exception {
		try{
			beforeActionCommand();
			beforeGameCommand();
			duringGameCommand();
			afterGameCommand();	
			afterActionCommand();
		}catch(Exception e){
			throw e;
		}finally{
			setChanged();
			notifyObservers();
		}
	}
	
	protected void beforeActionCommand() {
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over.");
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
		if(getGame().isCurrentPlayerStuck())
			throw new IllegalStateException("The current player is stuck.");
	}
	
	protected void afterActionCommand() {
		//Currently nothing is needed here.
	}
	


}
