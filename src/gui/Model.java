/**
 * 
 */
package gui;

import grid.Grid;
import items.Item;

import java.util.ArrayList;
import java.util.Observable;

import player.Player;
import utils.Coordinate;

/**
 * @author Jonas Devlieghere
 *
 */
public class Model extends Observable {

	private ArrayList<Coordinate> walls;
	
	private Grid grid;
	private Player currentPlayer;

	private ArrayList<Coordinate> lightTrailBlue;
	private ArrayList<Coordinate> lightTrailRed;
	private ArrayList<Item> currentSquareInventory;
	private ArrayList<Item> currentPlayerInventory;
	
	private String message = "";
	
	
	/**
	 * @return the currentPlayer 
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		String msg = message;
		setMessage("");
		return msg;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		if(message.length() != 0)
			update();
		this.message = message;
	}

	private Coordinate player1;
	private Coordinate player2;
	
	public Model(){
		this.walls = new ArrayList<Coordinate>();
		this.lightTrailBlue = new ArrayList<Coordinate>();
		this.lightTrailRed = new ArrayList<Coordinate>();		
		this.currentPlayerInventory = new ArrayList<Item>();
		this.currentSquareInventory = new ArrayList<Item>();
	}
	
	public void setGrid(Grid grid){
		this.grid = grid;
	}
	
	public Grid getGrid(){
		return this.grid;
	}
	
	
	public ArrayList<Coordinate> getAllGrenades(){
		ArrayList<Coordinate> grenades = new ArrayList<Coordinate>();
		for(Coordinate coordinate : getGrid().getAllCoordinates()){
			if(getGrid().getSquare(coordinate).getInventory().hasLightGrenade()){
				grenades.add(coordinate);
			}
		}
		return grenades;
	}
 	
	public void addToLightTrailBlue(Coordinate coordinate){
		lightTrailBlue.add(coordinate);
		update();
	}
	
	public void addToLightTrailRed(Coordinate coordinate){
		lightTrailRed.add(coordinate);
		update();
	}
		
	public void setPlayer1(Coordinate coordinate){
		this.player1 = coordinate;
		update();
	}
	
	public void setPlayer2(Coordinate coordinate){
		this.player2 = coordinate;
		update();
	}
	
	public void setWalls(ArrayList<Coordinate> walls){
		this.walls = walls;
		update();
	}
	
	public void setLightTrailBlue(ArrayList<Coordinate> lightTrail){
		this.lightTrailBlue = lightTrail;
		update();
	}

	/**
	 * 
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}
	
	public void setLightTrailRed(ArrayList<Coordinate> lightTrail){
		this.lightTrailRed = lightTrail;
		update();
	}
	
	public ArrayList<Coordinate> getWalls(){
		return new ArrayList<Coordinate>(walls);
	}

	public ArrayList<Coordinate> getLightTrailBlue(){
		return new ArrayList<Coordinate> (this.lightTrailBlue);
	}
	
	public ArrayList<Coordinate> getLightTrailRed(){
		return new ArrayList<Coordinate> (this.lightTrailRed);
	}
	
	public void clear(){
		this.walls.clear();
	}

	public Coordinate getPlayer1() {
		return this.player1;
	}
	
	public Coordinate getPlayer2() {
		return this.player2;
	}
	
	public ArrayList<Item> getCurrentPlayerInventory(){
		return this.currentPlayerInventory;
	}
	
	public ArrayList<Item> getCurrentSquareInventory(){
		return this.currentSquareInventory;
	}



}
