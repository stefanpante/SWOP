package controller;

import game.Game;
import game.Player;
import item.IdentityDisc;
import item.Item;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import square.Square;
import square.field.Field;
import square.obstacle.LightTrail;
import square.power.Power;
import util.Coordinate;

/**
 * Handler class (abstract) that will be implemented by all our use case handlers.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public abstract class Handler {

    /**
     * Property constants
     */
    public static final String WALLS_PROPERTY = "Walls";
    public static final String PLAYERS_PROPERTY = "Players";
    public static final String CURRENT_PLAYER_PROPERTY = "CurrentPlayer";
    public static final String CURRENT_POSITION_PROPERTY = "CurrentPosition";
    public static final String MESSAGE_PROPERTY = "Message";
    public static final String SQUARE_INVENTORY_PROPERTY = "SquareInventory";
    public static final String PLAYER_INVENTORY_PROPERTY = "PlayerInventory";
    public static final String ITEMS_PROPERTY = "items";
    public static final String END_TURN_PROPERTY = "EndTurnProperty";
    public static final String LIGHT_TRAILS_PROPERTY = "LightTrails";
    public static final String POWER_FAILS_PROPERTY = "PowerFails";
    public static final String WIN_PROPERTY = "Win";
    public static final String LOSE_PROPERTY = "Lose";
    public static final String SQUARES_PROPERTY = "squares";
    public static final String FORCEFIELD_PROPERTY = "forcefields";

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
            ArrayList<Square> squares = map.get(player).getSquares();

            for (Coordinate coordinate : getGame().getGrid().getAllCoordinates())
                if (squares.contains(getGame().getGrid().getSquare(coordinate)) && !players.containsValue(coordinate))
                    coords.add(coordinate);

            hashMap.put(player, coords);
        }

        return hashMap;
    }

    /**
     * Get a list of Items of the current square.
     *
     * @return ArrayList<item>	List of all items of the current square.
     */
    public ArrayList<Item> getSquareItems() {
        return getGame().getCurrentPlayer().getPosition().getInventory().getAllItems();
    }

    /**
     * Get a list of Items of the current player.
     *
     * @return ArrayList<item>	List of all items of the current player.
     */
    public ArrayList<Item> getPlayerItems() {
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

        firePropertyChange(Handler.POWER_FAILS_PROPERTY, properties.get(Handler.POWER_FAILS_PROPERTY));
        firePropertyChange(Handler.SQUARES_PROPERTY, properties.get(Handler.SQUARES_PROPERTY));

        firePropertyChange(Handler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
        firePropertyChange(Handler.PLAYER_INVENTORY_PROPERTY, getPlayerItems());
        firePropertyChange(Handler.SQUARE_INVENTORY_PROPERTY, getSquareItems());
        firePropertyChange(Handler.ITEMS_PROPERTY, properties.get(Handler.ITEMS_PROPERTY));
        firePropertyChange(Handler.LIGHT_TRAILS_PROPERTY, getLightTrailLocations());
        firePropertyChange(Handler.PLAYERS_PROPERTY, properties.get(Handler.PLAYERS_PROPERTY));
        firePropertyChange(Handler.FORCEFIELD_PROPERTY, properties.get(Handler.FORCEFIELD_PROPERTY));
        firePropertyChange(Handler.CURRENT_POSITION_PROPERTY, getGame().getGrid().getCoordinate(getGame().getCurrentPlayer().getPosition()));
    }


    /**
     * Returns all the game properties needed to fire propertyChanges.
     */
    protected HashMap<String, Object> getProperties() {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        ArrayList<Coordinate> forcefields = new ArrayList<Coordinate>();
        ArrayList<Coordinate> powerFailures = new ArrayList<Coordinate>();
        ArrayList<Coordinate> walls = new ArrayList<Coordinate>();
        ArrayList<Coordinate> squares = new ArrayList<Coordinate>();
        HashMap<Coordinate, ArrayList<Item>> items = new HashMap<Coordinate, ArrayList<Item>>();
        HashMap<Player, Coordinate> players = new HashMap<Player, Coordinate>();


        for (Coordinate coordinate : getGame().getGrid().getAllCoordinates()) {

            items.put(coordinate, getGame().getGrid().getSquare(coordinate).getInventory().getAllItems());

            Square square = getGame().getGrid().getSquare(coordinate);

            if (square.getPower().isFailing())
                powerFailures.add(coordinate);

            boolean player_position;

            if (square.isObstructed()) {
                player_position = false;

                for (Player player : getGame().getPlayers()) {
                    if (player.getPosition() == square)
                        player_position = true;
                }

                if (!player_position)
                    walls.add(coordinate);
            }

            for (Field field : square.getAllFields()) {
                for (Square sq : field.getSquares()) {
                    if (!forcefields.contains(getGame().getGrid().getCoordinate(sq)))
                        forcefields.add(getGame().getGrid().getCoordinate(sq));
                }
            }

            squares.add(coordinate);

            for (Player player : getGame().getPlayers()) {
                if (player.getPosition() == square)
                    players.put(player, coordinate);
            }
        }

        properties.put(Handler.POWER_FAILS_PROPERTY, powerFailures);
        properties.put(Handler.FORCEFIELD_PROPERTY, forcefields);
        properties.put(Handler.ITEMS_PROPERTY, items);
        properties.put(Handler.WALLS_PROPERTY, walls);
        properties.put(Handler.SQUARES_PROPERTY, squares);
        properties.put(Handler.PLAYERS_PROPERTY, players);

        return properties;
    }

    /**
     * Checks if the end of the move causes the current player to win.
     *
     * @return true if the move causes the player to win.
     */
    public boolean hasWon() {
        Player nextPlayer = getGame().getNextPlayer();
        Player currentPlayer = getGame().getCurrentPlayer();

        return nextPlayer.getStartPosition().equals(currentPlayer.getPosition());

    }

    /**
     * Check if the current player is stuck, if he is stuck, he will lose the game.
     *
     * @return a boolean which represents the loss of the game.
     */
    public boolean hasLost() {
        return getGame().isCurrentPlayerStuck();
    }


}
