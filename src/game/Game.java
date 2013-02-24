package game;

import notnullcheckweaver.NotNull;
import player.Player;
import grid.Grid;

/**
 * Game class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
@NotNull
public class Game {
	
	/**
	 * The grid of this Game object.
	 */
	private Grid grid;
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
	 * Returns the value of the grid of this Game as an Grid.
	 *
	 * @return 	An object of the Grid class.
	 * 			| Grid
	 */
	public Grid getGrid() {
		return grid;
	};

	/**
	 * Sets the value of the grid of Game if the given value is valid. 
	 * 
	 * @param 	grid
	 *			The grid to set.
	 * @post 	The given value is the current value of the grid of this Game.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid grid.
	 *			| !isValidGrid(grid)
	 */
	public void setGrid(Grid grid) {
		if (!isValidGrid(grid)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ grid
							+ ") is not a valid agrument of the field grid from the class Game");
		}
		this.grid = grid;
	};

	/**
	 * Check whether the given grid is a valid grid for all the objects of Game.
	 * @param 	grid
	 *			The grid to check.
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidGrid(Grid grid) {
		if (!(grid instanceof Grid)) {
			return false;
		}
		//TODO: specific constraints for this field.
		return true;
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
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidPlayer(Player player) {
		if (!(player instanceof Player)) {
			return false;
		}
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
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		if (!isValidCurrentPlayer(currentPlayer)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ currentPlayer
							+ ") is not a valid agrument of the field currentPlayer from the class Game");
		}
		this.currentPlayer = currentPlayer;
	};
	
	/**
	 * Switches player. e.g.: if the current player is the first player,
	 *  the currentplayer will be set to player 2
	 */
	public void switchPlayer(){
		if(currentPlayer == player1) currentPlayer = player2;
		if(currentPlayer == player2) currentPlayer = player1;
	}
	
	/**
	 * gets the inventory of the current player 
	 */
	public String getInventoryString(){
		return currentPlayer.getInventory().toString();
	}

	/**
	 * Check whether the given currentPlayer is a valid currentPlayer for all the objects of Game.
	 * @param 	currentPlayer
	 *			The currentPlayer to check.
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidCurrentPlayer(Player currentPlayer) {
		if (!(currentPlayer instanceof Player)) {
			return false;
		}
		//TODO: specific constraints for this field.
		return true;
	}
	
	public void useItem(int itemIndex) {
		currentPlayer.useItem(itemIndex);
		
	}
}
	