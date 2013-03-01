/**
 * 
 */
package gui;

import items.Item;
import items.LightGrenade;

import java.util.ArrayList;

import square.Square;
import utils.Coordinate2D;

/**
 * @author Jonas Devlieghere
 *
 */
public class GridModel {

	private ArrayList<Coordinate2D> walls;
	private ArrayList<Coordinate2D> lightTrailBlue;
	private ArrayList<Coordinate2D> lightTrailRed;
	private ArrayList<Coordinate2D> lightGrenades;
	private ArrayList<Item> currentSquareInventory;
	private ArrayList<Item> currentPlayerInventory;
	private Coordinate2D player1;
	private Coordinate2D player2;
	
	public GridModel(){
		this.walls = new ArrayList<Coordinate2D>();
		this.lightTrailBlue = new ArrayList<Coordinate2D>();
		this.lightTrailRed = new ArrayList<Coordinate2D>();		
		this.lightGrenades = new ArrayList<Coordinate2D>();
		this.currentPlayerInventory = new ArrayList<Item>();
		this.currentSquareInventory = new ArrayList<Item>();
		currentPlayerInventory.add(new LightGrenade());
	}
	
	public void addToLightTrailBlue(Coordinate2D coordinate){
		lightTrailBlue.add(coordinate);
	}
	
	public void addToLightTrailRed(Coordinate2D coordinate){
		lightTrailRed.add(coordinate);
	}
	
	public void addToLightGrenades(Coordinate2D coordinate, Square square){
		lightGrenades.add(coordinate);
	}
		
	public void setPlayer1(Coordinate2D coordinate){
		this.player1 = coordinate;
	}
	
	public void setPlayer2(Coordinate2D coordinate){
		this.player2 = coordinate;
	}
	
	public void setWalls(ArrayList<Coordinate2D> walls){
		this.walls = walls;
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
	
	public ArrayList<Coordinate2D> getLightGrenades(){
		return new ArrayList<Coordinate2D> (this.lightGrenades);
	}
	
	public void clear(){
		this.walls.clear();
		this.lightGrenades.clear();
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
