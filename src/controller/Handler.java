package controller;

import game.Game;
import game.Player;
import item.Item;
import item.launchable.IdentityDisc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import square.Square;
import square.obstacle.LightTrail;
import square.power.Power;
import util.Coordinate;

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
		this(null);
	}

	/**
	 * Creates a new handler with a given game.
	 * @param game the game that will be used by this handler.
	 */
	public Handler(Game game) {
		propertyChangeSupport = new PropertyChangeSupport(this);
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
	 * Get a list of coordinates of Players that are in the Grid.
	 * 
	 * @return	ArrayList<Coordinate>	List of all coordinates which contain a player.
	 */
	public HashMap<Player,Coordinate> getPlayerLocations() {
		HashMap<Player,Coordinate> players = new HashMap<Player,Coordinate>();
		for(Player player : getGame().getPlayers()){
			players.put(player,getGame().getGrid().getCoordinate(player.getPosition()));
		}	
		return players;
	}


	/**
	 * Get a list of all coordinates which have a LightTrail with their associated Player.
	 * 
	 * @return	ArrayList<Player, ArrayList<Coordinate>> List of coordinates which have a LightTrail.
	 */
	public HashMap<Player, ArrayList<Coordinate>> getLightTrailLocations() {
		HashMap<Player, LightTrail> map = new HashMap<Player, LightTrail>();
		for(Player player : getGame().getPlayers()){
			map.put(player, player.getLightTrail());
		}
		Iterator<Player> iterator = map.keySet().iterator();
		HashMap<Player, Coordinate> players = getPlayerLocations();

		HashMap<Player, ArrayList<Coordinate>> hashMap = new HashMap<Player, ArrayList<Coordinate>>();

		while(iterator.hasNext()) {
			Player player = iterator.next();

			ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
			ArrayList<Square> squares = map.get(player).getSquares();

			for(Coordinate coordinate : getGame().getGrid().getAllCoordinates())
				if(squares.contains(getGame().getGrid().getSquare(coordinate)) && !players.containsValue(coordinate))
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
		HashMap<String, Object> properties = getProperties();

		firePropertyChange(GameHandler.GRENADES_PROPERTY, properties.get(GameHandler.GRENADES_PROPERTY));
		firePropertyChange(GameHandler.POWER_FAILS_PROPERTY,  properties.get(GameHandler.POWER_FAILS_PROPERTY));
		firePropertyChange(GameHandler.IDENTITY_DISK_PROPERTY, properties.get(GameHandler.IDENTITY_DISK_PROPERTY));
		firePropertyChange(GameHandler.CHARGED_DISK_PROPERTY, properties.get(GameHandler.CHARGED_DISK_PROPERTY));
		firePropertyChange(GameHandler.TELEPORT_PROPERTY, properties.get(GameHandler.TELEPORT_PROPERTY));
		firePropertyChange(GameHandler.SQUARES_PROPERTY, properties.get(GameHandler.SQUARES_PROPERTY));
		
		firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
		firePropertyChange(GameHandler.PLAYER_INVENTORY_PROPERTY, getPlayerItems());
		firePropertyChange(GameHandler.SQUARE_INVENTORY_PROPERTY, getSquareItems());   
		firePropertyChange(GameHandler.ITEMS_PROPERTY, properties.get(GameHandler.ITEMS_PROPERTY));
		firePropertyChange(GameHandler.LIGHT_TRAILS_PROPERTY, getLightTrailLocations());
		firePropertyChange(GameHandler.PLAYERS_PROPERTY, properties.get(GameHandler.PLAYERS_PROPERTY));
		firePropertyChange(GameHandler.CURRENT_POSITION_PROPERTY, getGame().getGrid().getCoordinate(getGame().getCurrentPlayer().getPosition()));
	}



	protected HashMap<String, Object> getProperties(){
		HashMap<String, Object> properties = new HashMap<String, Object>();
		HashMap<Power, Coordinate> powerFailures   = new HashMap<Power, Coordinate>();
		
		ArrayList<Coordinate> lightGrenades = new ArrayList<Coordinate>();
		ArrayList<Coordinate> identityDisks = new ArrayList<Coordinate>();
		ArrayList<Coordinate> teleports		= new ArrayList<Coordinate>();
		ArrayList<Coordinate> forceFieldGenerators = new ArrayList<Coordinate>();
		ArrayList<Coordinate> walls			= new ArrayList<Coordinate>();
		ArrayList<Coordinate> chargedDisks  = new ArrayList<Coordinate>(); 
		ArrayList<Coordinate> squares		= new ArrayList<Coordinate>();
		HashMap<Coordinate, ArrayList<Item>> items = new HashMap<Coordinate, ArrayList<Item>>();
		HashMap<Player, Coordinate> players = new HashMap<Player, Coordinate>();


		for(Coordinate coordinate : getGame().getGrid().getAllCoordinates()){
			
			items.put(coordinate, getSquareItems());

			Square square = getGame().getGrid().getSquare(coordinate);
			
			if(square.getPower().isFailing())
				powerFailures.put(square.getPower(), coordinate);

			if(square.getInventory().hasTeleport())
				teleports.add(coordinate);

			if(square.getInventory().hasLightGrenade() && !square.getInventory().getLightGrenade().isActive())
				lightGrenades.add(coordinate);
			

			if(square.getInventory().hasForceFieldGenerator()){
				forceFieldGenerators.add(coordinate);
			}

			for(IdentityDisc id : square.getInventory().getIdentityDiscs()){
				if(id.isCharged()){
					chargedDisks.add(coordinate);
				}
				else{
					identityDisks.add(coordinate);
				}
			}

			boolean player_position = false;
			
			if (square.isObstructed()) {
				player_position = false;
				
				for (Player player: getGame().getPlayers()) {
					if (player.getPosition() == square)
						player_position = true;
				}

				if(!player_position)
					walls.add(coordinate);
			}
			
			squares.add(coordinate);
			
			for (Player player: getGame().getPlayers()){
				if (player.getPosition() == square)
					players.put(player, coordinate);
			}
		}

		properties.put(GameHandler.POWER_FAILS_PROPERTY, powerFailures);
		properties.put(GameHandler.GRENADES_PROPERTY, lightGrenades);
		properties.put(GameHandler.ITEMS_PROPERTY, items);
		properties.put(GameHandler.IDENTITY_DISK_PROPERTY, identityDisks);
		properties.put(GameHandler.TELEPORT_PROPERTY, teleports);
		properties.put(GameHandler.WALLS_PROPERTY, walls);
		properties.put(GameHandler.CHARGED_DISK_PROPERTY, chargedDisks);
		properties.put(GameHandler.SQUARES_PROPERTY, squares);
		properties.put(GameHandler.PLAYERS_PROPERTY, players);

		return properties;
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
		return getGame().isCurrentPlayerStuck();
	}


}
