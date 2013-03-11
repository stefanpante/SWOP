/**
 * 
 */
package gui;

import grid.Grid;
import items.Item;

import java.util.ArrayList;
import java.util.Observable;

import square.Square;
import utils.Coordinate;

/**
 * @author Jonas Devlieghere
 *
 */
public class GameModel extends Observable {

	private ArrayList<Coordinate> walls;
	
	private Grid grid;
	
	private ArrayList<Coordinate> lightTrailBlue;
	private ArrayList<Coordinate> lightTrailRed;
	private ArrayList<Item> currentSquareInventory;
	private ArrayList<Item> currentPlayerInventory;
	
	private Coordinate player1;
	private Coordinate player2;
	
	public GameModel(){
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
		setChanged();
		notifyObservers();
	}
	
	public void addToLightTrailRed(Coordinate coordinate){
		lightTrailRed.add(coordinate);
		setChanged();
		notifyObservers();
	}
	
		
	public void setPlayer1(Coordinate coordinate){
		this.player1 = coordinate;
		setChanged();
		notifyObservers();
	}
	
	public void setPlayer2(Coordinate coordinate){
		this.player2 = coordinate;
		setChanged();
		notifyObservers();
	}
	
	public void setWalls(ArrayList<Coordinate> walls){
		this.walls = walls;
		setChanged();
		notifyObservers();
	}
	
	public void setLightTrailBlue(ArrayList<Coordinate> lightTrail){
		this.lightTrailBlue = lightTrail;
		setChanged();
		notifyObservers();
	}
	
	public void setLightTrailRed(ArrayList<Coordinate> lightTrail){
		this.lightTrailRed = lightTrail;
		setChanged();
		notifyObservers();
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
