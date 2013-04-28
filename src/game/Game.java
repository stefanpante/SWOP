package game;

import grid.Grid;
import grid.RandomGridBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observer;
import java.util.Random;
import java.util.Map.Entry;

import manager.ActionManager;
import notnullcheckweaver.NotNull;
import square.Direction;
import square.Square;
import square.obstacle.LightTrail;
import util.AStar;
import util.Coordinate;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

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
	 * Boolean indicating the game is over
	 */
	private boolean active;
	
	/**
	 * Manages the power failures in the game.
	 */
	private PowerManager powerManager;

	
	//T
	/**
	 *Constructs a new board-based game.
	 * 
	 * @param 	hSize		
	 * 			the horizontal size of the board
	 * @param 	vSize	
	 * 			the vertical size of the board
	 */
	public Game(int hSize, int vSize){
		// Build the grid
		RandomGridBuilder gridBuilder = new RandomGridBuilder(hSize, vSize);
		this.setGrid(gridBuilder.getGrid());
		

		// Add players
		this.players = new ArrayList<Player>();
		Square bottomLeft = grid.getSquare(new Coordinate(0, vSize-1));
		Square topRight = grid.getSquare(new Coordinate(hSize-1, 0));
		addPlayer(new Player(bottomLeft, 1));
		addPlayer(new Player(topRight, 2));
		
		this.powerManager = new PowerManager(getGrid());
		
		// Start the game
		start();
		setCurrentPlayer(players.get(0));
	}
	
	
	//TODO: get start position from grid instead of with coordinate
	/**
	 *Constructs a new board-based game with a given grid.
	 *
	 * Protected for testing purposes. 
	 * 
	 * @param 	grid
	 * 			The grid on which the game will be played.
	 */
	public Game(Grid grid){
		// Build the grid
		this.setGrid(grid);
		
		// Add players
		this.players = new ArrayList<Player>();
		Square bottomLeft = grid.getSquare(new Coordinate(0, grid.getVSize()-1));
		Square topRight = grid.getSquare(new Coordinate(grid.getHSize()-1, 0));
		addPlayer(new Player(bottomLeft, 1));
		addPlayer(new Player(topRight, 2));

		this.powerManager = new PowerManager(getGrid());
		
		// Start the game
		start();
		setCurrentPlayer(players.get(0));
	}
	
	/**
	 * Sets the grid for the game.
	 * 
	 * @param grid
	 */
	private void setGrid(Grid grid) {
		this.grid = grid;		
	}

	/**
	 * Add the player to this game
	 * 
	 * @param 	player
	 * 			The player to be set
	 */
	public void addPlayer(Player player) throws NullPointerException{
		if(player == null)
			throw new NullPointerException("A Player cant be null");
		
		if(players.contains(player))
			throw new IllegalStateException("Two players should never be the same object");
		
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
	 * Check whether the given currentPlayer is a valid currentPlayer for this Game.
	 * @param 	currentPlayer
	 *			The currentPlayer to check.
	 * @return	True
	 */
	public static boolean isValidCurrentPlayer(Player currentPlayer) {
		return !(currentPlayer == null);
	}

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
	 * @Pre		currentPlayer == player1 || currentPlayer == player2
	 * @post	if(old.currentPlayer == player1) new.currentPlayer == player2
	 * 			if(old.currentPlayer == player2) new.currentPlayer == player1
	 */
	public void switchToNextPlayer() {
		Player candidate = getNextPlayer();
		
		while(candidate.getRemainingActions() < 0){
			int ra = candidate.getRemainingActions();
			candidate.endTurn();
			int nextPlayer = (players.indexOf(candidate) + 1) % players.size();
			candidate = players.get(nextPlayer);
		}
		
		this.currentPlayer = getNextPlayer();
	}
	
	/**
	 * Returns the value of the currentPlayer of this Game as an Player.
	 *
	 * @return 	An object of the Player class.
	 */
	@Basic @Raw
	public Player getCurrentPlayer() {
		return currentPlayer;
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
		ArrayList<Player> otherPlayers = getPlayers();
		otherPlayers.remove(currentPlayer);
		return otherPlayers;
	}
	
	/**
	 * Returns all players
	 * @return Returns all players of this game
	 */
	public ArrayList<Player> getPlayers(){
		return new ArrayList<Player>(this.players);
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
	
	/**
	 * Returns the power manager.
	 */
	public PowerManager getPowerManager() {
		return this.powerManager;
	}
	
	/**
	 * Return if the current game is end
	 * 
	 * @return	The state of the game
	 */
	public boolean isActive(){
		return this.active;
	}

	public void start() {
		this.active = true;
	}
	
	/**
	 * End the current game
	 */
	public void end() {
		this.active = false;
	}
	
	/**
	 * Checks if the current player is unable to make a move.
	 */
	public boolean isCurrentPlayerStuck(){
		if(!getCurrentPlayer().hasMoved()){
			return false; 
		}
		for(Entry<Direction, Square> entry : getGrid().getNeighbors(getCurrentPlayer().getPosition()).entrySet()){
			if(getGrid().canMoveTo(getCurrentPlayer().getPosition(), entry.getKey())){
				return false;
			}
		}
		return true;
	}
}
