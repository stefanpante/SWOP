package handlers;

import game.Game;
import items.Item;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import player.Player;
import square.Direction;
import square.Square;
import square.obstacles.LightTrail;
import square.state.PowerFailureState;
import utils.Coordinate;

/**
 * Handler class (abstract) that will be implemented by all our use case handlers.
 */
public abstract class Handler {

	/**
	 * The game which this handler uses.
	 */
	private Game game;
	
	/**
	 * Used to signal property changes
	 */
    protected PropertyChangeSupport propertyChangeSupport;

    /**
     * constructs a new Handler object.
     */
    public Handler()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
	 * Creates a new handler with a given game.
	 * @param game the game that will be used by this handler.
	 */
	public Handler(Game game) {
		this();
		this.game = game;
	}

	/**
	 * Creates a new handler with a given game and given propertychangelistener
	 * @param game		the game which will be used
	 * @param listener	the propertyChangeListener which will be used.
	 */
	public Handler(Game game,  PropertyChangeListener listener) {
		this(game);
		addPropertyChangeListener(listener);
	}

	/**
     * Adds a new PropertyChangelistener
     * @param listener the listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a propertychangelistener.
     * @param listener 	Which listener to remove from this handler/
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Used to fire a propertyChange.
     * @param propertyName 	the name of the property that has been modified.
     * @param newValue		The new value associated with the property.
     */
    protected void firePropertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, new Object(), newValue);
    }
    
    /**
	 * Get a list of coordinates of Grenades that are in the Grid.
	 * 
	 * @return	ArrayList<Coordinate>	List of all coordinates which contain a grenade.
	 */
	public ArrayList<Coordinate> getGrenadeLocations() {
		ArrayList<Coordinate> grenades = new ArrayList<Coordinate>();
    	for(Coordinate coordinate : getGame().getGrid().getAllCoordinates()){
    		Square square = getGame().getGrid().getSquare(coordinate);
    		
    		if(square.getInventory().hasLightGrenade() && !square.getInventory().hasActiveItem())
    			grenades.add(coordinate);
    	}
    	return grenades;
	}
	
	/**
	 * Get a list of coordinates of Players that are in the Grid.
	 * 
	 * @return	ArrayList<Coordinate>	List of all coordinates which contain a player.
	 */
	public ArrayList<Coordinate> getPlayerLocations() {
		ArrayList<Coordinate> players = new ArrayList<Coordinate>();
		
		for(Player player : getGame().getPlayers()){
    		players.add(getGame().getGrid().getCoordinate(player.getPosition()));
    	}
		
		return players;
	}
	
	/**
	 * Get a list of coordinates of Walls that are in the Grid.
	 * 
	 * @return	ArrayList<Coordinate>	List of all coordinates which contain a wall.
	 */
	public ArrayList<Coordinate> getWallLocations() {
		ArrayList<Coordinate> walls = new ArrayList<Coordinate>();
    	ArrayList<Coordinate> players = this.getPlayerLocations();
    	
    	for(Coordinate coordinate : getGame().getGrid().getAllCoordinates()){
    		Square square = getGame().getGrid().getSquare(coordinate);
    		
    		if(square.isObstructed())
    			walls.add(coordinate);
    	}
    	
    	walls.removeAll(players);
    	
    	return walls;
	}
	
	/**
	 * Get a list of all coordinates which have a LightTrail with their associated Player.
	 * 
	 * @return	ArrayList<Player, ArrayList<Coordinate>> List of coordinates which have a LightTrail.
	 */
	public HashMap<Player, ArrayList<Coordinate>> getLightTrailLocations() {
		HashMap<Player, LightTrail> map = getGame().getLightTrails();
		Iterator<Player> iterator = map.keySet().iterator();
		ArrayList<Coordinate> players = getPlayerLocations();
		
		HashMap<Player, ArrayList<Coordinate>> hashMap = new HashMap<Player, ArrayList<Coordinate>>();
		
		while(iterator.hasNext()) {
			Player player = iterator.next();
			
			ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
			ArrayList<Square> squares = map.get(player).getSquares();
			
			for(Coordinate coordinate : getGame().getGrid().getAllCoordinates())
	    		if(squares.contains(getGame().getGrid().getSquare(coordinate)) && !players.contains(coordinate))
	    			coords.add(coordinate);
	    		
	    	hashMap.put(player, coords);
		}
		
		return hashMap;
	}
	
	/**
	 * Get a list of Items of the current square.
	 * 
	 * @return	ArrayList<item>	List of all items of the current square.
	 */
	public ArrayList<Item> getSquareItems(){
		return getGame().getCurrentPlayer().getPosition().getInventory().getAllItems();
	}
	
	/**
	 * Get a list of Items of the current player.
	 * 
	 * @return	ArrayList<item>	List of all items of the current player.
	 */
	public ArrayList<Item> getPlayerItems(){
		return getGame().getCurrentPlayer().getInventory().getAllItems();
	}
	
	/**
	 * Returns the reference to the game.
	 * 
	 * @return
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Sets the game. This normally done in constructor.
	 * 
	 * @param game
	 */
	protected void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Fires all property changes when called.
	 */
	public void fireChanges(){
    	firePropertyChange(GameHandler.GRENADES_PROPERTY, getGrenadeLocations());
    	firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
    	firePropertyChange(GameHandler.PLAYER_INVENTORY_PROPERTY, getPlayerItems());
    	firePropertyChange(GameHandler.SQUARE_INVENTORY_PROPERTY, getSquareItems());   	
    	firePropertyChange(GameHandler.LIGHT_TRAILS_PROPERTY, getLightTrailLocations());
    	firePropertyChange(GameHandler.CURRENT_POSITION_PROPERTY, getGame().getGrid().getCoordinate(getGame().getCurrentPlayer().getPosition()));
    	firePropertyChange(GameHandler.POWER_FAILS_PROPERTY, getPowerFails());
    	if(hasWon()){
    		firePropertyChange(GameHandler.WIN_PROPERTY, getGame().getCurrentPlayer().toString());
    		getGame().end();
    	}else if(hasLost()){
    		firePropertyChange(GameHandler.LOSE_PROPERTY, getGame().getCurrentPlayer().toString());	
    		getGame().end();
    	}
	}

	/**
	 * Returns a list of coordinates with powerFailures.
	 */
	private ArrayList<Coordinate> getPowerFails() {
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		for(Coordinate coordinate : getGame().getGrid().getAllCoordinates()){
			Square square = getGame().getGrid().getSquare(coordinate);
			if(square.getState() instanceof PowerFailureState){
				list.add(coordinate);
			}
		}
		return list;
	}
	
	
	/**
	 * Checks if the end of the move causes the current player to win.
	 * @return 	true if the move causes the player to win. 
	 */
	public boolean hasWon(){
		Player nextPlayer = getGame().getNextPlayer();
		Player currentPlayer = getGame().getCurrentPlayer();

		if(nextPlayer.getStartPosition().equals(currentPlayer.getPosition())){
			return true;
		}

		return false;
	}
	
	/**
	 * Check if the current player is stuck, if he is stuck, he will lose the game.
	 * @return	a boolean which represents the loss of the game.
	 */
	public boolean hasLost(){
		boolean stuck = true;
		for(Entry<Direction, Square> entry : getGame().getGrid().getNeighbors(getGame().getCurrentPlayer().getPosition()).entrySet()){
			if(getGame().getGrid().canMoveTo(entry.getValue(), entry.getKey())){
				stuck = false;
			}
		}
		return stuck;
	}
	
	public void startAction(){
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over");
		fireChanges();
	}
	
	public void endAction(){
		fireChanges();
	}
	
}
