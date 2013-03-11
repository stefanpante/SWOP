package game;

import grid.Grid;
import grid.GridBuilder;
import grid.GridBuilder2;
import items.Inventory;
import items.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Stack;

import notnullcheckweaver.NotNull;
import player.Player;
import square.Square;
import utils.Coordinate2D;

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
	 * The Grid.
	 */
	private Grid grid;
	
	/**
	 * The players of this Game object.
	 */
	private ArrayList<Player> players;
	
	/**
	 * The currentPlayer of this Game object.
	 */
	private Player currentPlayer;

	/**
	 *Constructs a new board-based game.
	 * 
	 * @param hSize		the horizontal size of the board
	 * @param vSize		the vertical size of the board
	 */
	public Game(int hSize, int vSize){
		players = new ArrayList<Player>();
		GridBuilder2 gridBuilder = new GridBuilder2(hSize, vSize);
		this.grid = gridBuilder.buildGrid();
		
		Square bottomLeft = grid.getSquare(new Coordinate2D(0, vSize));
		Square topRight = grid.getSquare(new Coordinate2D(hSize, 0));
		
		addPlayer(new Player(bottomLeft, "Player 1"));
		addPlayer(new Player(topRight, "Player 2"));
		
		setCurrentPlayer(players.get(0));	
	}
	/**
	 * Returns the value of the player1 of this Game as an Player.
	 *
	 * @return 	An object of the Player class.
	 * 			| Player
	 */
	public Player getPlayer(int i) throws IllegalArgumentException {
		if(i >= players.size())
			throw new IllegalArgumentException();
		return players.get(i);
	}
	
	/**
	 * Returns the grid of this game
	 * 
	 * @return	The grid of this game
	 * 			| Grid
	 */
	public Grid getGrid(){
		return this.grid;
	}
	
	/**
	 * Add the player to this game
	 * 
	 * @param 	player
	 * 			The player to be set
	 */
	public void addPlayer(Player player){
		players.add(player);
	}

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
		if (currentPlayer == null || !isValidCurrentPlayer(currentPlayer) || !canHaveAsCurrentPlayer(currentPlayer)) {
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
	public void switchToNextPlayer() {
		this.currentPlayer = getNextPlayer();
	}
	
	/**
	 * Gets the player instance which isn't the current player.
	 * e.g.: If the currentPlayer is player 1, player 2 is returned
	 * @pre		currentPlayer == player1 || currentPlayer == player2
	 * @return 	if(currentPlayer == player1) return player2
	 * 			if(currentPlayer == player2) return player1
	 */
	public Player getNextPlayer(){
		int nextPlayer = (players.indexOf(getCurrentPlayer()) + 1) % players.size();
		return players.get(nextPlayer);
	}
	
	
	
	/**
	 * Returns all players, except the current player
	 * @return	Returns all players, except the current player
	 */
	public ArrayList<Player> getOtherPlayers(){
		ArrayList<Player> otherPlayers = new ArrayList<Player>(players);
		otherPlayers.remove(currentPlayer);
		
		return otherPlayers;
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
		return players.contains(currentPlayer);
	}
}