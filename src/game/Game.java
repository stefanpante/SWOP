package game;


import items.Inventory;
import items.Item;

import java.util.Observable;

import notnullcheckweaver.NotNull;
import player.Player;
import square.GridBuilder;
import square.Square;

/**
 * Game class, this class controls the flow of the game
 * 
 * @author 	Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 * @version 0.5
 * 
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
	 * the minimum vertical and horizontal size are 10 squares.
	 */
	public static final int MIN_VSIZE = 10;
	public static final int MIN_HSIZE = 10;
	
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
	 * the horizontal size and vertical size of the grid
	 */
	private int hSize, vSize;

	
	/**
	 *Constructs a new board-based game.
	 * 
	 * @param hSize		the horizontal size of the board
	 * @param vSize		the vertical size of the board
	 */
	public Game(int hSize, int vSize){
		initialize(hSize, vSize);
	}
	
	private void initialize(int hSize, int vSize){
		GridBuilder builder = new GridBuilder(hSize, vSize);
		setHSize(hSize);
		setVSize(vSize);
		builder.constructSquares();
		builder.constructWalls();
		setPlayer1(new Player(builder.getBottomLeft(), "Player 1"));
		setPlayer2(new Player(builder.getTopRight(), "Player 2"));
		setCurrentPlayer(player1);
	}
	
	public void setHSize(int size) throws IllegalArgumentException{
		if(!isValidHSize(size)) throw new IllegalArgumentException();
		this.hSize = size;
	}
	
	public void setVSize(int size) throws IllegalArgumentException{
		if(!isValidVSize(size)) throw new IllegalArgumentException();
		this.vSize = size;
	}
	
	public int getHSize(){
		return hSize;
	}
	
	public int getVSize(){
		return vSize;
	}
	
	public boolean isValidHSize(int size){
		return size >= this.MIN_HSIZE;
	} 
	
	public boolean isValidVSize(int size){
		return size >= this.MIN_VSIZE;
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
		if (!isValidPlayer(player1) || player1 == player2) {
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
		return player != null;
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
		if (!isValidPlayer(player2) || player2 == player1) {
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
	public void switchPlayer() {
		if(currentPlayer == player1){
			setCurrentPlayer(player2);
		}
		else if(currentPlayer == player2){
			setCurrentPlayer(player1);
		}
	}
	
	/**
	 * Gets the player instance which isn't the current player.
	 * e.g.: If the currentPlayer is player 1, player 2 is returned
	 * @pre		currentPlayer == player1 || currentPlayer == player2
	 * @return 	if(currentPlayer == player1) return player2
	 * 			if(currentPlayer == player2) return player1
	 */
	public Player getOtherPlayer(){
		if(currentPlayer == player1) return player2;
		else return player1;
	}
	
	/**
	 * Check whether the given currentPlayer is a valid currentPlayer for this Game.
	 * @param 	currentPlayer
	 *			The currentPlayer to check.
	 * @return	True
	 */
	public static boolean isValidCurrentPlayer(Player currentPlayer) {
		return !(currentPlayer == null);
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
}
	
