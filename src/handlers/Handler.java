/**
 * 
 */
package handlers;

import game.Game;
import items.Item;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import player.Player;
import square.Square;
import square.obstacles.LightTrail;
import utils.Coordinate;

/**
 * Handler  class.
 */
public abstract class Handler {

	private Game game;
    protected PropertyChangeSupport propertyChangeSupport;

    public Handler()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, new Object(), newValue);
    }
    
	public Handler(Game game) {
		this();
		this.game = game;
	}

	public Handler(Game game,  PropertyChangeListener listener) {
		this(game);
		addPropertyChangeListener(listener);
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
		
		HashMap<Player, ArrayList<Coordinate>> hashMap = new HashMap<Player, ArrayList<Coordinate>>();
		
		while(iterator.hasNext()) {
			Player player = iterator.next();
			
			ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
			ArrayList<Square> squares = map.get(player).getSquares();
			
			for(Coordinate coordinate : getGame().getGrid().getAllCoordinates())
	    		if(squares.contains(getGame().getGrid().getSquare(coordinate)))
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
	protected Game getGame() {
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
	
	public void endAction(){
    	firePropertyChange(GameHandler.GRENADES_PROPERTY, getGrenadeLocations());
    	firePropertyChange(GameHandler.PLAYER_INVENTORY_PROPERTY, getPlayerItems());
    	firePropertyChange(GameHandler.SQUARE_INVENTORY_PROPERTY, getPlayerItems());   	
    	firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
    	firePropertyChange(GameHandler.LIGHT_TRAILS_PROPERTY, getLightTrailLocations());
	}
}
