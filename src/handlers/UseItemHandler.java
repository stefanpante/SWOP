package handlers;

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
	private static final UseItemHandler instance = new UseItemHandler();
	
	/** 
	 * Creates the handler for the usage of an item
	 * @param game
	 */
	private UseItemHandler() {
		this.game = Game.getInstance();
	}
	
	public UseItemHandler getInstance(){
		return instance;
	}
	
	/**
	 * 
	 */
	//TODO: this is an temporary implementation of useItem, 
	// is commandline but should be in GUI
	public void useItem(Item item) {
		System.out.println(game.getCurrentPlayerInventory());
//		InputStreamReader cin = new InputStreamReader(System.in);
//		int itemIndex = cin.read();
//		game.useItem(itemIndex);
//		cin.close();
		game.useItem(item);
	}

}
