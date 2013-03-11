/**
 * 
 */
package gui;

import grid.Grid;
import items.Item;

import java.util.ArrayList;
import java.util.Observable;

import square.Square;
import utils.Coordinate2D;

/**
 * @author Jonas Devlieghere
 *
 */
public class GameModel extends Observable {

	private ArrayList<Coordinate2D> walls;
	
	private Grid grid;
	
	private ArrayList<Coordinate2D> lightTrailBlue;
	private ArrayList<Coordinate2D> lightTrailRed;
	private ArrayList<Item> currentSquareInventory;
	private ArrayList<Item> currentPlayerInventory;
	
	private Coordinate2D player1;
	private Coordinate2D player2;
	
	public GameModel(){
		this.walls = new ArrayList<Coordinate2D>();
		this.lightTrailBlue = new ArrayList<Coordinate2D>();
		this.lightTrailRed = new ArrayList<Coordinate2D>();		
		this.currentPlayerInventory = new ArrayList<Item>();
		this.currentSquareInventory = new ArrayList<Item>();
	}
	
	public void setGrid(Grid grid){
		this.grid = grid;
	}
	
	public Grid getGrid(){
		return this.grid;
	}
	
	public ArrayList<Coordinate2D> getAllGrenades(){
		ArrayList<Coordinate2D> grenades = new ArrayList<Coordinate2D>();
		for(Coordinate2D coordinate : getGrid().getAllCoordinates()){
			if(getGrid().getSquare(coordinate).getInventory().hasLightGrenade()){
				grenades.add(coordinate);
			}
		}
		return grenades;
	}
 	
	public void addToLightTrailBlue(Coordinate2D coordinate){
		lightTrailBlue.add(coordinate);
		setChanged();
		notifyObservers();
	}
	
	public void addToLightTrailRed(Coordinate2D coordinate){
		lightTrailRed.add(coordinate);
		setChanged();
		notifyObservers();
	}
	
		
	public void setPlayer1(Coordinate2D coordinate){
		this.player1 = coordinate;
		setChanged();
		notifyObservers();
	}
	
	public void setPlayer2(Coordinate2D coordinate){
		this.player2 = coordinate;
		setChanged();
		notifyObservers();
	}
	
	public void setWalls(ArrayList<Coordinate2D> walls){
		this.walls = walls;
		setChanged();
		notifyObservers();
	}
	
	public void setLightTrailBlue(ArrayList<Coordinate2D> lightTrail){
		this.lightTrailBlue = lightTrail;
		setChanged();
		notifyObservers();
	}
	
	public void setLightTrailRed(ArrayList<Coordinate2D> lightTrail){
		this.lightTrailRed = lightTrail;
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<Coordinate2D> getWalls(){
		return new ArrayList<Coordinate2D>(walls);
	}

	public ArrayList<Coordinate2D> getLightTrailBlue(){
		return new ArrayList<Coordinate2D> (this.lightTrailBlue);
	}
	
	public ArrayList<Coordinate2D> getLightTrailRed(){
		return new ArrayList<Coordinate2D> (this.lightTrailRed);
	}
	
	public void clear(){
		this.walls.clear();
	}

	public Coordinate2D getPlayer1() {
		return this.player1;
	}
	
	public Coordinate2D getPlayer2() {
		return this.player2;
	}
	
	public ArrayList<Item> getCurrentPlayerInventory(){
		return this.currentPlayerInventory;
	}
	
	public ArrayList<Item> getCurrentSquareInventory(){
		return this.currentSquareInventory;
	}



}
