package controller;

import game.Game;
import game.Player;
import game.gamebuilder.CTFGameBuilder;
import game.gamebuilder.GameBuilder;
import game.gamebuilder.RaceGameBuilder;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.RandomGridBuilder;
import gui.ObjectronGUI;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;




import util.Coordinate;

/**
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class GameHandler extends Handler {

	public static int CTFGAMEMODE = 0;
	public static int RACEGAMEMODE = 1;
	/* Handlers */
	private EndTurnHandler endTurnHandler;
	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private TurnHandler turnHandler;
	private ThrowLaunchableHandler throwLaunchableHandler;

	private PropertyChangeListener gui;


	public GameHandler(PropertyChangeListener gui){
		this.gui = gui;
	}

	/**
	 * Initializes all the used handlers.
	 */
	private void initHandlers(){
		addPropertyChangeListener(gui);
		this.endTurnHandler = new EndTurnHandler(getGame(), gui);
		this.moveHandler = new MoveHandler(getGame(),gui);
		this.pickUpHandler = new PickUpHandler(getGame(),gui);
		this.useItemHandler = new UseItemHandler(getGame(),gui);
		this.turnHandler = new TurnHandler(getGame(), gui);
		this.throwLaunchableHandler = new ThrowLaunchableHandler(getGame(), gui);
		try {
			turnHandler.startTurn();
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @param hSize the hSize of the grid on which the game is played
	 * @param vSize the vSize of the grid on which the game is played
	 */
	public void startNewGame(int hSize, int vSize, int numOfPlayers, int gameMode) {
		GameBuilder builder;

		switch(gameMode){
		case 0:		builder = new CTFGameBuilder(hSize, vSize, numOfPlayers);
		break;
		case 1:		builder = new RaceGameBuilder(hSize, vSize, numOfPlayers);
		break;
		default: 	throw new IllegalStateException(" The specified game mode doesnt exist.");
		}

		setGame(builder.getGame());
		initHandlers();
	}

	public void startNewGame(String filename, int numOfPlayers, int gameMode) throws IOException{
		GameBuilder builder;

		switch(gameMode){
		case 0:		builder = new CTFGameBuilder(filename, numOfPlayers);
		break;
		case 1:		builder = new RaceGameBuilder(filename, numOfPlayers);
		break;
		default: 	throw new IllegalStateException(" The specified game mode doesnt exist.");
		}

		setGame(builder.getGame());
		initHandlers();
	}

}
