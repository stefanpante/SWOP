package controller;

import game.Game;
import game.Player;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.RandomGridBuilder;
import gui.ObjectronGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;




import util.Coordinate;

public class GameHandler extends Handler {
	
	private ObjectronGUI objectronGUI;
	
	
	public GameHandler(ObjectronGUI objectronGUI){
		this.objectronGUI = objectronGUI;
	}

	/* Handlers */
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private TurnHandler turnHandler;
	private ThrowLaunchableHandler throwLaunchableHandler;
	
    /**
     * Used to start a new game.
     */
    public void startNewGame(int hCells, int vCells){
		try {
			createGame(hCells,vCells);
			initHandlers();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    public void startNewGame(String filename){
    	try{
    		createGame(filename);
    		initHandlers();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void initHandlers(){
    	addPropertyChangeListener(objectronGUI);
    	this.endTurnHandler = new EndTurnHandler(getGame(), objectronGUI);
    	this.moveHandler = new MoveHandler(getGame(),objectronGUI);
    	this.pickUpHandler = new PickUpHandler(getGame(),objectronGUI);
    	this.useItemHandler = new UseItemHandler(getGame(),objectronGUI);
    	this.turnHandler = new TurnHandler(getGame(), objectronGUI);
    	//this.throwLaunchableHandler = new ThrowLaunchableHandler(getGame(), objectronGUI);
        try {
            turnHandler.startTurn();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.populateGui();
    }
    
    /**
	 * Creates a list of coordinates where grenades, walls and players are located.
	 * This is information that the GUI can use.
	 */
	public void populateGui() {
		HashMap<String, Object> properties = getProperties();
		HashMap<Player,Coordinate> players = getPlayerLocations();
		
		firePropertyChange(SQUARES_PROPERTY, properties.get(SQUARES_PROPERTY));
    	firePropertyChange(WALLS_PROPERTY, properties.get(WALLS_PROPERTY));
    	firePropertyChange(PLAYERS_PROPERTY, players);
    	fireChanges();
	}

	/**
     * Returns the moveHandler.
     * @return 	the moveHandler.
     */
    public MoveHandler getMoveHandler(){
    	return this.moveHandler;
    }
    
	/**
     * Returns the turnHandler.
     * @return 	the moveHandler.
     */
    public TurnHandler getTurnHandler(){
    	return this.turnHandler;
    }
    
    /**
     * Returns the pickUpHandler.
     * @return 	the pickUpHandler.
     */
    public PickUpHandler getPickupHandler(){
    	return this.pickUpHandler;
    }
    
    /**
     * Returns the useItemHandler.
     * @return	the useItemHandler
     */
    public UseItemHandler getUseItemHandler(){
    	return this.useItemHandler;
    }
    
    /**
     * Returns the endTurnHandler.
     * @return 	the endTurnHandler.
     */
    public EndTurnHandler getEndTurnHandler(){
    	return this.endTurnHandler;
    }
    /**
     * Returns the throwLaunchableHandler.
     * @return	the throwLaunchableHandler.
     */
    public ThrowLaunchableHandler getThrowLaunchableHandler(){
    	return this.throwLaunchableHandler;
    }
    
	/**
	 * @param hSize
	 * @param vSize
	 */
	public void createGame(int hSize, int vSize) {
		RandomGridBuilder builder = new RandomGridBuilder(hSize, vSize);
		setGame(new Game(builder.getGrid()));
	}
	
	public void createGame(String filename){
		FileGridBuilder builder;
		try {
			builder = new FileGridBuilder(filename);
			setGame(new Game(builder.getGrid()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
