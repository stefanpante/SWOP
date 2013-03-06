package board;

import items.LightGrenade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import square.obstacles.Brick;
import square.obstacles.Wall;
import utils.Coordinate2D;


/**
 * 
 * Class used for the construction of a complex board object
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */	
public class BoardBuilder {

	/**
	 * Max percentage of squares covered by walls.
	 */
	public static float MAX_PERCENTAGEWALLS = 0.2f;


	/**
	 * Percentage of square covered by grenades
	 */
	public static float PERCENTAGEGRENADES = 0.05f;

	/**
	 * Percentage of max length of a wall
	 */
	public static float MAX_LENGTHPERCENTAGEWALL = 0.5f;

	/**
	 * Horizontal size of this grid.
	 */
	private int hSize;

	/**
	 * Vertical size of this grid.
	 */
	private int vSize;

	/**
	 * Two-dimensional array containing the squares in this grid.
	 */

	private HashMap<Coordinate2D, Square> board;
	private ArrayList<Wall> walls;
	
	/**
	 * Create a new BoardBuilder with the given dimension.
	 * 
	 * @param 	hSize
	 * 			The horizontal size for this board builder
	 * @param 	vSize
	 * 			The vertical size for this board builder
	 */
	public BoardBuilder(int hSize, int vSize) {
		this.hSize = hSize;
		this.vSize = vSize;
		board = new HashMap<Coordinate2D, Square>();
		this.walls = new ArrayList<Wall>();
	}
	
	public void initialize(){
		constructSquares();
		buildWalls();
		placeGrenades();
	}
	
	public void constructSquares(){
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				board.put(new Coordinate2D(i,j), new Square());
			}
		}
		
	}
	
	private void buildWalls(){
		
		
		int randomCoverage = 2 + (int) Math.floor((this.getMaxCoverage() - 2) * Math.random());
		ArrayList<Coordinate2D> candidates = this.constructCandidates();
		
		while(randomCoverage >= 2){
			Wall wall = constructWall(candidates);
			if(wall != null){
				walls.add(wall);
				randomCoverage -= wall.getLength();
			}
			else{
				randomCoverage--;
			}
		}
	}

	private Wall constructWall(ArrayList<Coordinate2D> candidates){
		Wall result = null;	
		Orientation orientation = Orientation.getRandomOrientation();
		int maxLength = getMaxLengthWall(orientation);
		ArrayList<Coordinate2D> sequence = getRandomSequence(candidates);
		
		
		int length = (int) (2 + Math
				.floor(((maxLength - 2) * Math.random())));
		
		if(sequence.size() < 2){
			return result;
		}
		if(sequence.size() >= length){
			ArrayList<Coordinate2D> list = new ArrayList<Coordinate2D>(sequence.subList(0, length));
			replaceWithBricks(list);
			// wat voor wall opbouwen?
			candidates.removeAll(getAllNeighbors(list));
			
		}
		
		if(sequence.size() < length){
			replaceWithBricks(sequence);
			candidates.removeAll(getAllNeighbors(sequence));
		}
		
		return result;
	}
	
	private ArrayList<Coordinate2D> getAllNeighbors(ArrayList<Coordinate2D> seq){
		ArrayList<Coordinate2D> neighbors = new ArrayList<Coordinate2D>();
		for(Coordinate2D c: seq){
			neighbors.add(c);
			neighbors.add(new Coordinate2D(c.getX() - 1, c.getY()));
			neighbors.add(new Coordinate2D(c.getX(), c.getY() - 1));
			neighbors.add(new Coordinate2D(c.getX() + 1, c.getY()));
			neighbors.add(new Coordinate2D(c.getX(), c.getY() + 1));
			neighbors.add(new Coordinate2D(c.getX() - 1, c.getY() - 1));
			neighbors.add(new Coordinate2D(c.getX() + 1, c.getY() - 1));
			neighbors.add(new Coordinate2D(c.getX() - 1, c.getY() + 1));
			neighbors.add(new Coordinate2D(c.getX() + 1, c.getY() + 1));
			
		}
		
		return neighbors;
		
	}
	
	private int getMaxLengthWall(Orientation orientation) throws IllegalArgumentException{
		switch(orientation){
		case HORIZONTAL:
			return (int) (this.hSize * MAX_LENGTHPERCENTAGEWALL);
		case VERTICAL:
			return (int) (this.vSize * MAX_LENGTHPERCENTAGEWALL);
		default:	
			throw new IllegalArgumentException("This orientation is not supported:" + orientation);
		}
	}
	private void replaceWithBricks(ArrayList<Coordinate2D> bricks){
		for(Coordinate2D c: bricks){
			board.put(c, new Brick());
		}
	}
	
	public ArrayList<Coordinate2D> constructCandidates(){
		ArrayList<Coordinate2D> candidates = new ArrayList<Coordinate2D>();
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				Coordinate2D c = new Coordinate2D(i,j);
				if(!board.get(c).isObstructed())
					candidates.add(c);
			}
		}
		Coordinate2D topRight = new Coordinate2D(hSize - 1, 0);
		Coordinate2D bottomLeft = new Coordinate2D(0, vSize -1);
		
		candidates.remove(topRight);
		candidates.remove(bottomLeft);
		return candidates;
	}
	private ArrayList<Coordinate2D> getRandomSequence(ArrayList<Coordinate2D> candidates){
		ArrayList<Coordinate2D> sequence = new ArrayList<Coordinate2D>();
		Random random = new Random();
		Coordinate2D can = candidates.get(random.nextInt(candidates.size()));
		Orientation orientation = Orientation.getRandomOrientation();
		if(orientation == Orientation.VERTICAL)
			sequence = getVerticalSequence(can, candidates);
		if(orientation == Orientation.HORIZONTAL)
			sequence = getHorizontalSequence(can,candidates);
		
		return sequence;
	}
	
	private ArrayList<Coordinate2D> getHorizontalSequence(Coordinate2D can, ArrayList<Coordinate2D> candidates){
		ArrayList<Coordinate2D> seq = new ArrayList<Coordinate2D>();
		int x = can.getX();
		int y = can.getY();
		
		for(int x1 = x; x1 < hSize; x1++){
			Coordinate2D c = new Coordinate2D(x1, y);
			if(candidates.contains(c)){
				seq.add(c);
			}
			else break;
		}
		
		for(int x1 = x - 1; x1 >= 0; x1--){
			Coordinate2D c = new Coordinate2D(x1, y);
			if(candidates.contains(c)){
				seq.add(c);
			}
			else break;
		}
		
		return seq;
		
	}
	
	private ArrayList<Coordinate2D> getVerticalSequence(Coordinate2D can, ArrayList<Coordinate2D> candidates){
		ArrayList<Coordinate2D> seq = new ArrayList<Coordinate2D>();
		int x = can.getX();
		int y = can.getY();
		
		for(int y1 = y; y1 < hSize; y1++){
			Coordinate2D c = new Coordinate2D(x, y1);
			if(candidates.contains(c)){
				seq.add(c);
			}
			else break;
		}
		
		for(int y1 = y - 1; y1 >= 0; y1--){
			Coordinate2D c = new Coordinate2D(x, y1);
			if(candidates.contains(c)){
				seq.add(c);
			}
			else break;
		}
		
		return seq;
	}
	
	private void placeGrenades() {
		ArrayList<Coordinate2D> candidates = this.constructCandidates();
		int grenades = getNumberOfGrenades();
		candidates.remove(new Coordinate2D(hSize-1, 0));
		candidates.remove(new Coordinate2D(0, vSize -1));
		Random generator = new Random();
		//TODO: place first 2 grenades in the 3 x 3 board around the startposition
		int i = 2;
		while (i < grenades && !candidates.isEmpty()) {
			int l = candidates.size();
			Coordinate2D c = candidates.get(generator.nextInt(l));
			// no need for testing for obstructed, candidates ensures this
			board.get(c).getInventory().addItem(new LightGrenade());
			candidates.remove(c);
	
		}
		
	}

	public ArrayList<Wall> getWalls(){
		return walls;
	}

	public HashMap<Coordinate2D, Square> getSquares(){
		return board;
	}

	public int getNumberOfGrenades(){
		return (int) (Math.ceil(this.getGridSize() * PERCENTAGEGRENADES));
	}
	
	public int getMaxCoverage() {
		return (int) ((vSize*hSize) * MAX_PERCENTAGEWALLS);
	}
	
	public int getGridSize(){
		return this.vSize * this.hSize;
	}

	


}
