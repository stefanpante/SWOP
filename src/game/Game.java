package game;

import grid.Grid;
import grid.GridBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import be.kuleuven.cs.som.taglet.PreTaglet;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import notnullcheckweaver.NotNull;
import player.Player;
import square.Square;
import square.obstacles.LightTrail;
import utils.Coordinate;

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
	 * The possibility of a power failure in a square.
	 */
	private final float CHANCE_POWERFAILURE = 0.05f;
	
	/**
	 * Collection of LightTrails associated with every player.
	 */
	private HashMap<Player, LightTrail> lightTrails = new HashMap<Player, LightTrail>();
	
	/**
	 * Boolean indicating the game is over
	 */
	private boolean active;

	/**
	 *Constructs a new board-based game.
	 * 
	 * @param 	hSize		
	 * 			the horizontal size of the board
	 * @param 	vSize	
	 * 			the vertical size of the board
	 */
	public Game(int hSize, int vSize){
		super();
		start();
		players = new ArrayList<Player>();
		GridBuilder gridBuilder = new GridBuilder(hSize, vSize);
		this.grid = gridBuilder.buildGrid();
		
		Square bottomLeft = grid.getSquare(new Coordinate(0, vSize-1));
		Square topRight = grid.getSquare(new Coordinate(hSize-1, 0));
		
		addPlayer(new Player(bottomLeft, 1));
		addPlayer(new Player(topRight, 2));
		
		setCurrentPlayer(players.get(0));
		powerFailureSquares();
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
		
		addLightTrail(player);
		players.add(player);
	}
	
	/**
	 * Adds a LightTrail for a given player.
	 * 
	 * @param	player	The player which needs a LightTrail.
	 * @throws	IllegalStateException	If there already exists a LightTrail for the player.
	 */
	private void addLightTrail(Player player) throws IllegalStateException {
		if(lightTrails.containsKey(player))
			throw new IllegalStateException("Cannot set a LightTrail twice for the same player " + player);
		
		LightTrail lightTrail = new LightTrail();
		lightTrails.put(player,  lightTrail);
		
		player.addObserver(lightTrail);
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
			candidate.endTurn(ra + Player.MAX_ALLOWED_ACTIONS);
			int nextPlayer = (players.indexOf(candidate) + 1) % players.size();
			candidate = players.get(nextPlayer);
		}
		
		this.currentPlayer = getNextPlayer();
		powerFailureSquares();
		updateStates();
	}
	
	/**
	 * Returns the value of the currentPlayer of this Game as an Player.
	 *
	 * @return 	An object of the Player class.
	 */
	@Basic @Raw
	public Player getCurrentPlayer() {
		return currentPlayer;
	};
	
	/**
	 * Returns the LightTrail associated with the Player.
	 * 
	 * @param	player
	 * @return	LightTrail	If the player has an associated LightTrail.
	 * @throws	IllegalStateException	If the player does not have a LightTrail.
	 * 									Or if the player given is null.
	 */
	public LightTrail getLightTrail(Player player) throws IllegalStateException {
		if(player == null) 
			throw new IllegalStateException("The given player is null.");
		
		if(!lightTrails.containsKey(player)) 
			throw new IllegalStateException("The player " + player +  " does not have a LightTrail.");
		
		return lightTrails.get(player);
	}
	
	/**
	 * Returns all the LightTrails that are in the game.
	 * 
	 * @return	ArrayList<Player,LightTrail>	All LightTrails associated with every player.
	 */
	public HashMap<Player, LightTrail> getLightTrails() {
		HashMap<Player, LightTrail> hashMap = new HashMap<Player, LightTrail>();
		hashMap.putAll(this.lightTrails);
		
		return hashMap;
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
	 * Sets the state of any square to a PowerFailure state with a 5% chance.
	 */
	public void powerFailureSquares() {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		Random random = new Random();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			if(random.nextFloat() <= CHANCE_POWERFAILURE){
				square.powerFail();
				ArrayList<Square> neighbors = getGrid().getNeighborsAsList(square);
				for(Square s: neighbors){
					s.powerFail();
				}
			}
		}
	}
	
	/**
	 * Updates the states of every square, notifying them that a turn has been completed.
	 */
	public void updateStates() {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			square.endTurn();
		}
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
	 * Clears all powerFailures, for testing purposes.
	 */
	public void clearPowerFailures(){
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			square.powerGain();
		}
	}
}
