package handlers;

import items.Inventory;
import items.Item;

import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;

/**
 * Handler/Controller for the pick up use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class PickUpHandler {

	private Game game;
	public PickUpHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * commandline for now, has to be integrated into the gui.
	 * @throws IOException
	 */
	public void pickUp(Item item){
		game.getCurrentPlayer().getPosition().getInventory().take(item);
		game.getCurrentPlayer().pickUp(item);
		game.getCurrentPlayer().incrementActions();
	}
	
	public void showItems(){
		Inventory inventory = game.getCurrentPlayer().getPosition().getInventory();
		GuiHandler.updateListModel(inventory);
	}
	
	

}
