package game;

import grid.Grid;

import java.util.*;
import java.util.Map.Entry;

import manager.ForceFieldManager;
import manager.PowerGayManager;
import notnullcheckweaver.NotNull;
import square.Direction;
import square.Square;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * Game class, this class controls the flow of the game
 * 
 * @author 	Dieter Castel, Jonas Devlieghere   en Stefan Pante
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
public abstract class Game {
	
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
	
	public Game(){
		this.players = new ArrayList<Player>();
	}

	/**
	 * Sets the grid for the game.
	 * 
	 * @param grid
	 */
	public void setGrid(Grid grid) {
		if(!isValidGrid(grid)){
			throw new IllegalStateException("The given grid is not valid");
		}
		this.grid = grid;		
	}
	
	public boolean isValidGrid(Grid grid){
		return grid != null;
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
		if(players.size() >= getMaximumAmountOfPlayers())
            throw  new IllegalStateException("There is a maximum of " + getMaximumAmountOfPlayers() + " players in this game.");
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
		
		while(candidate.getRemainingActions() < 0 || !candidate.isAlive()){
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
     * Sets all the players at once.
     *
     * @param players
     */
    public void setPlayers(ArrayList<Player> players){
        if(players.size() < getMinimumAmountOfPlayers() ||
                players.size() > getMaximumAmountOfPlayers())
            throw new IllegalStateException("There is a minimum of " + getMinimumAmountOfPlayers()
                    +" and a maximum of " + getMaximumAmountOfPlayers() + " players for this game.");
        for(Player p:players){
            if(p == null)
                throw new NullPointerException("A player should not be null");
        }
        this.players = players;
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

    public void addObserver(Observer observer){
        for (Player player : getPlayers()){
            player.addObserver(observer);
        }
    }
    
    /**
     * Checks if a player has won.
     */
    public abstract void checkWinners();
    /**
     * Checks if a player has lost.
     */
	public abstract void checkLosers();

	/**
	 * Gets the maximum amount of players for this game.
	 * @return
	 */
    public abstract int getMaximumAmountOfPlayers();
    
    /**
     * Get the minimum amount of players for this game.
     * @return
     */
    public abstract int getMinimumAmountOfPlayers();

}
