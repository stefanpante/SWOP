/**
 * 
 */
package handlers;

import game.Game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Observable;

import player.Player;

import square.Square;
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
    		
    		if(square.getInventory().hasLightGrenade())
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
}
