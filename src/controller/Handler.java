package controller;

import effect.Effect;
import game.Game;
import game.Player;
import item.Item;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import square.Brick;
import square.GridElement;
import square.Square;
import square.multi.LightTrail;
import util.Coordinate;

/**
 * Handler class (abstract) that will be implemented by all our use case handlers.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public abstract class Handler {

	// TODO: Implements effect in property changes.
    /**
     * Property constants
     */
    public static final String SQUARES_PROPERTY = "squares";
    public static final String PLAYERS_PROPERTY = "Players";
    public static final String EFFECTS_PROPERTY = "Effects";
    public static final String CURRENT_PLAYER_PROPERTY = "CurrentPlayer";
    public static final String CURRENT_POSITION_PROPERTY = "CurrentPosition";
    public static final String SQUARE_INVENTORY_PROPERTY = "SquareInventory";
    public static final String PLAYER_INVENTORY_PROPERTY = "PlayerInventory";
    public static final String ITEMS_PROPERTY = "Items";
    public static final String END_TURN_PROPERTY = "EndTurnProperty";
    public static final String MESSAGE_PROPERTY = "Message";
    public static final String WIN_PROPERTY = "Win";
    public static final String LOSE_PROPERTY = "Lose";
	public static final String WALLS_PROPERTY = "walls";
	public static final String LIGHTTRAIL_PROPERTY = "LightTrails";

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
    public Handler() {
        this(null);
    }

    /**
     * Creates a new handler with a given game.
     *
     * @param game the game that will be used by this handler.
     */
    public Handler(Game game) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        this.game = game;
    }

    /**
     * Creates a new handler with a given game and given propertychangelistener
     *
     * @param game     the game which will be used
     * @param listener the propertyChangeListener which will be used.
     */
    public Handler(Game game, PropertyChangeListener listener) {
        this(game);
        addPropertyChangeListener(listener);
    }

    /**
     * Adds a new PropertyChangelistener
     *
     * @param listener the listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a propertychangelistener.
     *
     * @param listener Which listener to remove from this handler/
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Used to fire a propertyChange.
     *
     * @param propertyName the name of the property that has been modified.
     * @param newValue     The new value associated with the property.
     */
    protected void firePropertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, new Object(), newValue);
    }


    /**
     * Get a list of coordinates of Players that are in the Grid.
     *
     * @return ArrayList<Coordinate>	List of all coordinates which contain a player.
     */
    public HashMap<Player, Coordinate> getPlayerLocations() {
        HashMap<Player, Coordinate> players = new HashMap<Player, Coordinate>();
        for (Player player : getGame().getPlayers()) {
            players.put(player, getGame().getGrid().getCoordinate(player.getPosition()));
        }
        return players;
    }

    /**
     * Get a list of Items of the current square.
     *
     * @return ArrayList<item>	List of all items of the current square.
     */
    public ArrayList<Item> getSquareItems() {
    	System.out.println("is Current player position null? " + getGame() == null);
        return getGame().getCurrentPlayer().getPosition().getAllItems();
    }

    /**
     * Get a list of Items of the current player.
     *
     * @return ArrayList<item>	List of all items of the current player.
     */
    public ArrayList<Item> getPlayerItems() {
        return getGame().getCurrentPlayer().getAllItems();
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
     * @param game the game which the nadlers use.
     */
    protected void setGame(Game game) {
        this.game = game;
    }

    /**
     * Fires all property changes when called.
     */
    public void fireChanges() {
        HashMap<String, Object> properties = getProperties();
        
        firePropertyChange(Handler.EFFECTS_PROPERTY, properties.get(Handler.EFFECTS_PROPERTY));
        firePropertyChange(Handler.SQUARES_PROPERTY, properties.get(Handler.SQUARES_PROPERTY));
        firePropertyChange(Handler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
        firePropertyChange(Handler.PLAYER_INVENTORY_PROPERTY, getPlayerItems());
        firePropertyChange(Handler.SQUARE_INVENTORY_PROPERTY, getSquareItems());
        firePropertyChange(Handler.LIGHTTRAIL_PROPERTY,getLightTrailLocations())l
        firePropertyChange(Handler.ITEMS_PROPERTY, properties.get(Handler.ITEMS_PROPERTY));
        firePropertyChange(Handler.PLAYERS_PROPERTY, properties.get(Handler.PLAYERS_PROPERTY));
        firePropertyChange(Handler.CURRENT_POSITION_PROPERTY, getGame().getGrid().getCoordinate(getGame().getCurrentPlayer().getPosition()));
    }


    /**
     * Returns all the game properties needed to fire propertyChanges.
     */
    protected HashMap<String, Object> getProperties() {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        HashMap<Coordinate, ArrayList<Effect>> effects = new HashMap<Coordinate, ArrayList<Effect>>();
        ArrayList<Coordinate> walls = new ArrayList<Coordinate>();
        ArrayList<Coordinate> squares = new ArrayList<Coordinate>();
        HashMap<Coordinate, ArrayList<Item>> items = new HashMap<Coordinate, ArrayList<Item>>();
        HashMap<Player, Coordinate> players = new HashMap<Player, Coordinate>();


        for (Coordinate coordinate : getGame().getGrid().getAllCoordinates()) {

            GridElement gridElement = getGame().getGrid().getGridElement(coordinate);

            if(gridElement.isSameType(new Square())){
                Square square = (Square) gridElement;
                
                items.put(coordinate, square.getAllItems());
                squares.add(coordinate);
                effects.put(coordinate, square.getAllEffects());
                boolean player_position;

                if (square.isObstacle()) {
                    player_position = false;

                    for (Player player : getGame().getPlayers()) {
                        if (player.getPosition() == square)
                            player_position = true;
                    }

                    if (!player_position)
                        walls.add(coordinate);
                }

                for (Player player : getGame().getPlayers()) {
                    if (player.getPosition() == square)
                        players.put(player, coordinate);
                }
            }
            else if(gridElement instanceof Brick){
            	walls.add(coordinate);
            }
          
        }

        properties.put(Handler.EFFECTS_PROPERTY, effects);
        properties.put(Handler.ITEMS_PROPERTY, items);
        properties.put(Handler.WALLS_PROPERTY,walls);
        properties.put(Handler.SQUARES_PROPERTY, squares);
        properties.put(Handler.PLAYERS_PROPERTY, players);

        return properties;
    }
    
    /**
     * Get a list of all coordinates which have a LightTrail with their associated Player.
     *
     * @return HashMap with coordinates of lightTrails per player List of coordinates which have a LightTrail.
     */
    public HashMap<Player, ArrayList<Coordinate>> getLightTrailLocations() {
        HashMap<Player, LightTrail> map = new HashMap<Player, LightTrail>();
        for (Player player : getGame().getPlayers()) {
            map.put(player, player.getLightTrail());
        }
        Iterator<Player> iterator = map.keySet().iterator();
        HashMap<Player, Coordinate> players = getPlayerLocations();

        HashMap<Player, ArrayList<Coordinate>> hashMap = new HashMap<Player, ArrayList<Coordinate>>();

        while (iterator.hasNext()) {
            Player player = iterator.next();

            ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
            ArrayList<Square> squares = map.get(player).getGridElements();

            for(Square square: squares){
            	coords.add(getGame().getGrid().getCoordinate(square));
            }
            hashMap.put(player, coords);
        }

        return hashMap;
    }

}
