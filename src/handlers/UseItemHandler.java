package handlers;

import items.Inventory;
import items.Item;

import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;

/**
 * Handler/Controller for the use case in which 
 * an item is used
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class UseItemHandler {

	private Game game;
	private GuiHandler guiHandler;
	
	/** 
	 * Creates the handler for the usage of an item
	 * @param game
	 */
	public UseItemHandler(Game game) {
		this.game = game;
	}
	
	
	/**
	 * 
	 */
	public void useItem(Item item) {
		game.getCurrentPlayer().useItem(item);
	}
	
	public void showItems(){
		Inventory inventory = game.getCurrentPlayer().getInventory();
		GuiHandler.updateListModel(inventory);
	}

}
