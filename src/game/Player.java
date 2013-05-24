package game;

import be.kuleuven.cs.som.annotate.Basic;
import effect.Effect;
import effect.imp.PlayerEffect;
import item.Item;
import item.inter.ItemContainer;
import item.inter.Movable;
import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;
import square.Square;
import square.multi.LightTrail;

import java.util.ArrayList;
import java.util.Observable;

/**
 * A Player is a Single Obstacle with an Inventory containing items and 
 * a Multi Obstacle Light Trail.
 * 
 * @author Dieter Castel, Jonas Devlieghere   en Stefan Pante
 *
 */
@NotNull
public class Player extends Observable implements Movable, ItemContainer {

    /**
     * Maximum amount of items a player can have
     */
    public static final int MAX_ITEMS = 6;

    /**
     * List of items on this square
     */
    @NotNull
    private final ArrayList<Item> items;

	/**
	 * The start position of this player
	 */
	private Square startPosition;
	
	/**
	 * the current position of the player
	 */
	@Nullable
	private Square currentPosition;
	
	/**
	 * The player's ID.
	 */
	private final int id;
	
	/**
	 * The player's Light Trail
	 */
	@NotNull
    private final LightTrail lightTrail;
	
	/**
	 * The number of remaining actions the player has left
	 */
	private int remainingActions;
	
	/**
	 * true if the user has moved during his turn
	 */
	private boolean moved;
	
	private boolean alive;

    @NotNull
    private final PlayerEffect playerEffect;
	
	/**
	 * The amount of action a player has during one move
	 */
	public static final int MAX_ALLOWED_ACTIONS = 4;
    private Square previousPosition;


    /**
	 * creates a new player with a given name and start position
	 * 
	 * @param	startPosition	
	 * 			The startposition for the player.
	 * @effect	setStartPosition(startPosition)
	 * @effect	setName(name)
	 * @throws	IllegalArgumentException	
	 * 			If the startPosition is null.
	 * @effect	setInventory(new PlayerInventory())
	 * @throws	IllegalArgumentException	If the startPosition is null.
	 * @throws	IllegalArgumentException	If the given name is not valid.
	 */
	public Player(Square startPosition, int id) throws IllegalArgumentException {
        this.playerEffect = new PlayerEffect();
        this.lightTrail = new LightTrail();
		this.setStartPosition(startPosition);
		this.alive = true;
		this.remainingActions = MAX_ALLOWED_ACTIONS;
		this.moved = false;
		this.id = id;
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * In order for a square to be a valid starting position it cannot
	 * be null. The square can't be obstructed.
	 * 
	 * @param	square
	 * @returns	True	If the square is not null and not obstructed.
	 * 			False	If the square is null or obstructed.
	 */
	public static boolean isValidStartPosition(@org.jetbrains.annotations.Nullable Square square) {
        return square != null;
    }
	
	/**
	 * A move is valid when the destination square is not null
	 * and when it is not obstructed.
	 * 
	 * @param	newPosition
	 * @return	True	If square is not null and not obstructed.
	 * 			False	If square is null or obstructed.
	 */
	private static boolean isValidPosition(@org.jetbrains.annotations.Nullable Square newPosition) {
        return newPosition != null;
    }

	
	/**
	 * Returns if the number of actions is valid for the player
	 * @param actions	the number of actions to be checked
	 * @return	true	if the number of actions equals or is smaller than 
	 * 					the number of allowed actions
	 * 			false 	otherwise
	 */
	public static boolean isValidRemainingActions(int actions){
		return actions <= Player.MAX_ALLOWED_ACTIONS;
	}

	/**
	 * Checks whether a given number of actions is a valid number of actions to lose.
	 * @return 		true if the number is larger than or equal to zero.
	 */
    boolean isValidActionsLost(int actions){
		return (actions >= 0);
	}

	/**
	 * Checks whether turns is a valid number of turns lost for a player.
	 * @param turns
	 * @return		true if the number is larger than or equal to zero.
	 */
    boolean isValidTurnsLost(int turns){
		return (turns >= 0);
	}

	/**
	 * Returns whether the current player has already moved 
	 * 	since the last call of endTurn()
	 */
	public boolean hasMoved(){
		return moved;
	}

    /**
     * Returns the maximum amount of items a player can carry.
     */
    public int getMaxItems(){
        return MAX_ITEMS;
    }

    /**
	 * Returns whether this player still has remaning actions.
	 * 
	 * @return	True	If this player has more then 0 remaining actions.
	 * 			False	otherwise.
	 * 			| getRemainingActions() > 0
	 */
	public boolean hasRemainingActions(){
		return getRemainingActions() > 0;
	}

	/**
	 * Sets the start position for the player
	 * 
	 * @param	pos	the position to be used as the start position for the player
	 * @throws 	IllegalArgumentException
	 * 			If the given position is not a valid startposition
	 */
	private void setStartPosition(Square pos) throws IllegalArgumentException {
		if(!isValidStartPosition(pos))
			throw new IllegalArgumentException("The startposition of a player should not be obstructed");
		this.startPosition = pos;
		this.currentPosition = pos;
        lightTrail.setHead(startPosition);
        startPosition.addSquareEffect(playerEffect);
	}


    /**
     * Moves the player to another square
     *
     * @throws	IllegalStateException
     * 		  	thrown if the player is unable to make this move
     */
    public void move(Square position) throws IllegalStateException{
        if(!isValidPosition(position))
            throw new IllegalStateException("Cannot set the player's position to a square that is obstructed.");
        setPosition(position);
        try{
            previousPosition.removeSquareEffect(getPlayerEffect());
            this.currentPosition.affect(this);
            currentPosition.addSquareEffect(getPlayerEffect());
            this.moved = true;
            decrementActions();
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * Resets the position of the player. This happens if a player
     * is thrown back because of an effect.
     *
     * @param   position
     *          The position where the player is reset.
     */
    public void resetPosition(Square position){
        this.currentPosition = position;
        currentPosition.addSquareEffect(getPlayerEffect());
    }

    /**
     * Set the position of the Player.
     *
     * @param   position
     *          The square on which the player is located.
     */
    public void setPosition(Square position) {
        setPreviousPosition(getPosition());
        this.currentPosition = position;
    }

    /**
     * Set the previous position of the Player
     * @param position
     */
    void setPreviousPosition(Square position){
        this.previousPosition = position;
    }

    /**
     * Return the previous position of the square.
     *
     * @return  The previous position
     */
    @Override
    public Square getPreviousPosition(){
        return this.previousPosition;
    }

    @Override
    public int getRange() {
        return 1;
    }

	/**
	 * Returns the name of the player.
	 * 
	 * @return	The name of this player. 
	 * 
	 */
	@NotNull
    public String getName(){
		return "Player " + getID();
	}

	/**
	 * Returns the player's id.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Returns the current position of the player.
	 */
	@Basic
	public Square getPosition() {
        return currentPosition;
	}

	/**
	 * Returns the current remaining actions of the player.
	 */
	@Basic
	public int getRemainingActions(){
		return remainingActions;
	}

	/**
	 * Returns the start position of this player.
	 */
	public Square getStartPosition(){
		return startPosition;
	}
	
	/**
	 * Boolean which represents the state of the player
	 */
	public boolean isAlive(){
		return this.alive;
	}
	
	/**
	 * Kills of the player, has other side-effects.
	 * Destroys the items of a player. drops the flag, etc.
	 */
	public void kill(){
		setAlive();
	}

	/**
	 * Sets whether the player is dead or alive.
     *
     */
    void setAlive(){
		this.alive = false;
	}



	/**
	 * Decrements the remaining actions by one and notifies the observers of this player.
	 * 
	 */
	public void decrementActions(){
        this.remainingActions--;
        lightTrail.setHead(this.getPreviousPosition());
        endAction();
    }
	

	/**
	 * Causes a player to lose turns.
	 * 
	 * @param	turns	
	 * 			the number of turns a player loses.
	 * @param	accumulating
	 * 			True if the remaining actions don't need to be discarded.
	 * @throws 	IllegalArgumentException
	 * 			Thrown when the number of turns lost is not valid.
	 */
	public void loseTurns(int turns, boolean accumulating) throws IllegalArgumentException{
		if(!isValidTurnsLost(turns))
				throw new IllegalArgumentException("The given turns lost are not valid (Should be a positive value)");
		
		int remActions;
		
		if(!accumulating)
			remActions = 0;
		else
			remActions = remainingActions - Player.MAX_ALLOWED_ACTIONS * turns;
		
		this.setRemainingActions(remActions);
	}
	
	/**
	 * Causes a player to lose actions
	 * 
	 * @param   actions	the number of actions a player loses.
	 * @throws 	IllegalArgumentException
	 * 			thrown when the number of actions lost is not valid.
	 */
	public void loseActions(int actions) throws IllegalArgumentException{
		if(!isValidActionsLost(actions)){
			throw new IllegalArgumentException("The given actions lost are not valid");
		}
		
		int remActions = remainingActions - actions;
		this.setRemainingActions(remActions);
	}
	
	/**
	 * Performs empty actions on the player until the player has no actions left.
	 * Decrements the length of the lightTrail
	 */
	public void performEmptyActions(){
		while(remainingActions > 0){
            lightTrail.setHead(this.getPosition());
            this.decrementActions();
		}
	}

	/**
	 * Adds the item to the player's inventory.
	 * 
	 * @param	item  the item to be picked up.
	 * @throws	IllegalArgumentException
	 * 			Thrown when adding the item would exceed the size of the inventory
	 */
	public void pickUp(Item item) throws IllegalArgumentException {
		addItem(item);
		decrementActions();
	}

	/**
	 * Method to select the item which the player is going to use
	 * 
	 * @param	item
	 * 			The item to use.
	 * @throws	IllegalStateException
	 * 			when the item that is used is not inside the inventory or null.
	 */
	public void useItem(@org.jetbrains.annotations.Nullable Item item) throws IllegalStateException {
		if(item == null)
			throw new IllegalStateException("Can't use a 'null' item");
        removeItem(item);
		decrementActions();
	}

	/**
	 * End's the player his turn. Adds Player.MAX_ALLOWED_ACTIONS to the remaining actions.
	 * 
	 */
	public void endTurn(){
		// should perform empty action for every action left
		while (remainingActions > 0){
			decrementActions();
		}
		remainingActions += Player.MAX_ALLOWED_ACTIONS;
		moved = false;
	}	

	/**
	 * Sets the remainingActions to the given remaining actions
	 * @param remainingActions	the new value for remaining actions
	 * @throws IllegalArgumentException
	 * 			thrown when the given remainingactions is not valid.
	 */
    void setRemainingActions(int remainingActions) throws IllegalArgumentException{
		if(!isValidRemainingActions(remainingActions)){
			throw new IllegalArgumentException("remaining actions is not valid!");
		}
		
		this.remainingActions = remainingActions;
	}
	
	/**
	 * Returns the light trail of this player
	 * 
	 * @return	This player's light trail
	 */
	@NotNull
    public LightTrail getLightTrail(){
		return this.lightTrail;
	}

	
	@NotNull
    @Override
	public String toString() {
		return "Player " + this.getID();
	}

	@Override
	public void resetRange() {

	}

    private void endAction(){
        setChanged();
        notifyObservers();
    }

    @Override
    public void addItem(@org.jetbrains.annotations.Nullable Item item) {
        if(item == null)
            throw new IllegalArgumentException("The item cannot be null");
        if(!item.canAddTo(this))
            throw new IllegalArgumentException("Cannot add " +item+ " to " + this);
        items.add(item);
        item.setContainer(this);
    }

    @Override
    public void removeItem(@org.jetbrains.annotations.Nullable Item item) {
        if(item == null)
            throw new IllegalArgumentException("The item cannot be null");
        if(!hasItem(item))
            throw new IllegalArgumentException("Cannot remove" +item+ " from " + this);
        items.remove(item);
    }

    @Override
    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    @Override
    public boolean hasType(@NotNull Item item) {
        return filterItemsByType(item).size() > 0;
    }

    @NotNull
    @Override
    public ArrayList<Item> filterItemsByType(@NotNull Item item) {
        ArrayList<Item> result = new ArrayList<>();
        for(Item it : getAllItems()){
            if(item.isSameType(it))
                result.add(it);
        }
        return result;
    }

    @NotNull
    @Override
    public ArrayList<Item> getAllItems() {
        return new ArrayList<Item>(items);
    }

    @Override
    public boolean isSameType(ItemContainer itemContainer) {
        return itemContainer instanceof Player;
    }

    @NotNull
    public Effect getPlayerEffect(){
        return this.playerEffect;
    }

}