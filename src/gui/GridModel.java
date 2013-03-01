/**
 * 
 */
package gui;

import java.util.HashMap;

import square.Square;
import utils.Coordinate2D;

/**
 * @author Jonas Devlieghere
 *
 */
public class GridModel {

	private HashMap<Coordinate2D, Square> walls;
	private HashMap<Coordinate2D, Square> lightTrails;
	private HashMap<Coordinate2D, Square> lightGrenades;
	private HashMap<Coordinate2D, Square> players;
	
	public GridModel(){
		this.walls = new HashMap<Coordinate2D, Square>();
		this.lightTrails = new HashMap<Coordinate2D, Square>();
		this.lightGrenades = new HashMap<Coordinate2D, Square>();
		this.players = new HashMap<Coordinate2D, Square>();
	}
	
	public void addToWalls(Coordinate2D coordinate, Square square){
		walls.put(coordinate, square);
	}
	
	public void addToLightTrails(Coordinate2D coordinate, Square square){
		lightTrails.put(coordinate, square);
	}
	
	public void addToLightGrenades(Coordinate2D coordinate, Square square){
		lightGrenades.put(coordinate, square);
	}
		
	public void addToPlayers(Coordinate2D coordinate, Square square){
		players.put(coordinate, square);
	}
	
	public HashMap<Coordinate2D, Square> getWalls(){
		return new HashMap<Coordinate2D, Square>(this.walls);
	}

	public HashMap<Coordinate2D, Square> getLightTrails(){
		return new HashMap<Coordinate2D, Square>(this.lightTrails);
	}
	
	public HashMap<Coordinate2D, Square> getLightGrenades(){
		return new HashMap<Coordinate2D, Square>(this.lightGrenades);
	}
	
	public HashMap<Coordinate2D, Square> getPlayers(){
		return new HashMap<Coordinate2D, Square>(this.players);
	}
	
	public void clear(){
		this.walls.clear();
		this.lightTrails.clear();
		this.lightGrenades.clear();
		this.players.clear();
	}
	

}
