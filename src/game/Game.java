package game;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import game.mode.GameMode;
import grid.Grid;
import manager.ForceFieldManager;
import manager.PowerManager;
import notnullcheckweaver.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Observer;

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
	@Nullable
    private Player currentPlayer;
	
	/**
	 * Boolean indicating the game is over
	 */
	private boolean active;
	
	/**
	 * The number of players in the game.
	 */
	private final int numOfPlayers;
	
	/**
	 * The type of game which is played;
	 */
	@NotNull
    private final GameMode gameMode;
	
	public Game(@NotNull GameMode gameMode, int numOfPlayers){
		this.gameMode = gameMode;
		this.numOfPlayers = numOfPlayers;
		gameMode.setGame(this);
		gameMode.build();
		this.setCurrentPlayer(players.get(0));

        addObserver(new ForceFieldManager(getGrid()));
        addObserver(new PowerManager(getGrid()));
	}

	/**
	 * Sets the grid for the game.
	 * 
	 * @param   grid
     *          The grid to be set
	 */
	public void setGrid(Grid grid) {
		if(!isValidGrid(grid)){
			throw new IllegalStateException("The given grid is not valid");
		}
		this.grid = grid;		
	}
	
	boolean isValidGrid(@Nullable Grid grid){
		return grid != null;
	}

	
	/**
	 * Check whether the given currentPlayer is a valid currentPlayer for this Game.
	 * @param 	currentPlayer
	 *			The currentPlayer to check.
	 * @return	True
	 */
	public static boolean isValidCurrentPlayer(@Nullable Player currentPlayer) {
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
	public void setCurrentPlayer(@Nullable Player currentPlayer) {
		if (currentPlayer == null || !isValidCurrentPlayer(currentPlayer) || !canHaveAsCurrentPlayer(currentPlayer)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ currentPlayer
							+ ") is not a valid agrument of the field currentPlayer from the class Game");
		}
		this.currentPlayer = currentPlayer;
	}
	
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
	@Nullable
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
	 * Returns all players
	 * @return Returns all players of this game
	 */
	@NotNull
    public ArrayList<Player> getPlayers(){
		return new ArrayList<>(this.players);
	}

    /**
     * Sets all the players at once.
     *
     * @param players
     */
    public void setPlayers(@NotNull ArrayList<Player> players){
        if(players.size() < gameMode.getMinimumAmountOfPlayers() ||
                players.size() > gameMode.getMaximumAmountOfPlayers())
            throw new IllegalStateException("There is a minimum of " + gameMode.getMinimumAmountOfPlayers()
                    +" and a maximum of " + gameMode.getMaximumAmountOfPlayers() + " players for this game.");
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
//	    for(Entry<Direction, GridElement> entry : getGrid().getNeighbors(getCurrentPlayer().getPosition()).entrySet()){
//			if(getGrid().canMoveTo(getCurrentPlayer().getPosition(), entry.getKey())){
//				return false;
//			}
//		}
		return false;
	}

    public void addObserver(Observer observer){
        for (Player player : getPlayers()){
            player.addObserver(observer);
        }
    }

    
    public int getNumberOfPlayers(){
    	return this.numOfPlayers;
    }

	public void setActivate() {
		this.active = true;
	}

    @NotNull
    public GameMode getGameMode(){
        return this.gameMode;
    }

}
