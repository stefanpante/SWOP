/**
 * 
 */
package gui;

import java.util.ArrayList;
import java.util.HashMap;

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
	private Coordinate2D player1;
	private Coordinate2D player2;
	
	public GridModel(){
		this.walls = new ArrayList<Coordinate2D>();
		this.lightTrailBlue = new ArrayList<Coordinate2D>();
		this.lightTrailRed = new ArrayList<Coordinate2D>();		
		this.lightGrenades = new ArrayList<Coordinate2D>();
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

	public HashMap<Coordinate2D, Square> getLightTrails(){
		return new HashMap<Coordinate2D, Square>(this.lightTrails);
	}
	
	public HashMap<Coordinate2D, Square> getLightGrenades(){
		return new HashMap<Coordinate2D, Square>(this.lightGrenades);
	}
	

	
	public void clear(){
		this.walls.clear();
		this.lightTrails.clear();
		this.lightGrenades.clear();
	}

	public Coordinate2D getPlayer1() {
		return this.player1;
	}
	
	public Coordinate2D getPlayer2() {
		return this.player2;
	}	

}
