package game;


import items.Inventory;
import items.Item;

import java.util.Observable;

import notnullcheckweaver.NotNull;
import player.Player;

/**
 * Game class, this class controls the flow of the game
 * 
 * @author 	Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 * @version 0.5
 * 
 * @invar	The Grid is a valid grid for this game
 * 			| isValidGrid(getGrid())
 * @invar	player1 is a valid player for this game
 * 			| isValidPlayer(getPlayer1())
 * @invar	player2 is a valid player for this game
 * 			| isValidPlayer(getPlayer1())
 * @invar   currentPlayer is valid current player for the game
 * 			| isValidCurrentPlayer(getCurrentPlayer())
 * 
 */
@NotNull
public class Game {
	
	/**
	 * The player1 of this Game object.
	 */
	private Player player1;
	
	/**
	 * The player2 of this Game object.
	 */
	private Player player2;
	
	/**
	 * The currentPlayer of this Game object.
	 */
	private Player currentPlayer;
	

	
	/**
	 * Zero argument constructor that makes a new game.
	 */
	public Game(){
		setPlayer1(new Player(null, "player1"));
		setPlayer2(new Player(null, "player2"));
	}

	/**
	 * Returns the value of the player1 of this Game as an Player.
	 *
	 * @return 	An object of the Player class.
	 * 			| Player
	 */
	public Player getPlayer1() {
		return player1;
	};

	/**
	 * Sets the value of the player1 of Game if the given value is valid. 
	 * 
	 * @param 	player1
	 *			The player1 to set.
	 * @post 	The given value is the current value of the player1 of this Game.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid player1.
	 *			| !isValidPlayer1(player1)
	 */
	public void setPlayer1(Player player1) {
		if (!isValidPlayer(player1)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ player1
							+ ") is not a valid agrument of the field player1 from the class Game");
		}
		this.player1 = player1;
	};

	/**
	 * Check whether the given player is a valid player for all the objects of Game.
	 * @param 	player
	 *			The player to check.
	 * @return	True
	 */
	public static boolean isValidPlayer(Player player) {
		//TODO: specific constraints for this field.
		return true;
	}

	/**
	 * Returns the value of the player2 of this Game as an Player.
	 *
	 * @return 	An object of the Player class.
	 * 			| Player
	 */
	public Player getPlayer2() {
		return player2;
	};

	/**
	 * Sets the value of the player2 of Game if the given value is valid. 
	 * 
	 * @param 	player2
	 *			The player2 to set.
	 * @post 	The given value is the current value of the player2 of this Game.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid player2.
	 *			| !isValidPlayer2(player2)
	 */
	public void setPlayer2(Player player2) {
		if (!isValidPlayer(player2)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ player2
							+ ") is not a valid agrument of the field player2 from the class Game");
		}
		this.player2 = player2;
	};

	/**
	 * Returns the value of the currentPlayer of this Game as an Player.
	 *
	 * @return 	An object of the Player class.
	 * 			| Player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	};

	/**
	 * Sets the value of the currentPlayer of Game if the given value is valid. 
	 * 
	 * @param 	currentPlayer
	 *			The currentPlayer to set.
	 * @post 	The given value is the current value of the currentPlayer of this Game.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid currentPlayer.
	 *			| !isValidCurrentPlayer(currentPlayer)
	 * @throws 	IllegalArgumentException
	 *			If the given argument can't be currentPlayer of this game.
	 *			| !canHaveAsCurrentPlayer(currentPlayer)
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		if (!isValidCurrentPlayer(currentPlayer) || !canHaveAsCurrentPlayer(currentPlayer)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ currentPlayer
							+ ") is not a valid agrument of the field currentPlayer from the class Game");
		}
		this.currentPlayer = currentPlayer;
	};
	
	/**
	 * Switches player. e.g.: if the current player is the first player,
	 * the current player will be set to player 2
	 * @pre		currentPlayer == player1 || currentPlayer == player2
	 * @post	if(old.currentPlayer == player1) new.currentPlayer == player2
	 * 			if(old.currentPlayer == player2) new.currentPlayer == player1
	 */
	//TODO hoe Controleer deze postcondities en precondities
	public void switchPlayer(){
		if(currentPlayer == player1){
			setCurrentPlayer(player2);
		}
		if(currentPlayer == player2){
			setCurrentPlayer(player1);
		}
	}
	
	/**
	 * Returns the inventory of the current player.
	 */
	public Inventory getCurrentPlayerInventory(){
		return currentPlayer.getInventory();
	}
	
	/**
	 * Returns the inventory of the items on the square where the current player is.
	 * 
	 */
	public Inventory getCurrentPositionInventory(){
		return currentPlayer.getPosition().getInventory();
	}

	/**
	 * Check whether the given currentPlayer is a valid currentPlayer for all the objects of Game.
	 * @param 	currentPlayer
	 *			The currentPlayer to check.
	 * @return	True
	 */
	public static boolean isValidCurrentPlayer(Player currentPlayer) {
		//TODO: specific constraints for this field.
		return true;
	}
	
	/**
	 * Checks whether the given current player is a legal currentPlayer for this game object.
	 * 
	 * @param 	currentPlayer
	 * 			The currentPlayer to check.
	 * @return	True if the given player is either the first or second player.
	 * 			| (currentPlayer == this.getPlayer1() ||  currentPlayer == this.getPlayer2())
	 */
	public boolean canHaveAsCurrentPlayer(Player currentPlayer) {
		return (currentPlayer == this.getPlayer1() ||  currentPlayer == this.getPlayer2());
	}
	

	/**
	 * 
	 * @param itemIndex
	 */
	public void useItem(Item item) throws IllegalStateException {
		if(!canUseAsItem(item)){
			throw new IllegalStateException("The item "
					+ item
					+ " can't be used by " 
					+ currentPlayer 
					+ " right now.");
		}
		currentPlayer.useItem(item);
	}
	
	/**
	 * Returns if the current player can use the given item.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return
	 */
	public boolean canUseAsItem(Item item) {
		//TODO maybe let only player check this.
		return false;
	}

	//TODO: comment
	public void endTurn() {
		currentPlayer.endTurn();	
	}

	public void pickUp(int itemIndex) {
		currentPlayer.pickUp(itemIndex);
	}
}
	
