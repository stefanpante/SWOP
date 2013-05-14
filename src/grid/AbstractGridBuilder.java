package grid;

import item.Item;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import item.ChargedIdentityDisc;
import item.ForceFieldGenerator;
import item.IdentityDisc;
import item.LightGrenade;
import item.Teleport;
import square.Direction;
import square.Square;
import square.obstacle.Wall;
import util.AStar;
import util.Coordinate;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * Super class for implementations of grid builder.
 * Grid builder is responsible for constructing all the
 * squares of which the grid is composed. It also places walls,
 * teleports and all items.
 *
 */
public abstract class AbstractGridBuilder {


    /**
     * The widest point of the grid ( in squares )
     */
	private int hSize;

    /**
     * The maximum height of the grid ( in squares )
     */
	private int vSize;

	/**
	 * the constraints for the grid.
	 */
	private GridConstraint constraintTeleport;
	private GridConstraint constraintLightGrenade;
	private GridConstraint constraintIdentityDisk;
	private GridConstraint constraintWall;
	private GridConstraint constraintForceFieldGenerator;
	
	/**
	 * The grid which has been/will be  built.
	 */
	private Grid grid;
	
	/**
	 * a random generator used in various creation methods
	 */
	private Random random;
	
	/**
	 * The walls which are placed on the grid.
	 */
	protected ArrayList<Wall> walls;

    /**
     * Initializes objects needed by the grid builder.
     */
	public AbstractGridBuilder(){
		this.walls = new ArrayList<Wall>();
	}
	
	/**
	 * Returns the grid.
	 * 
	 * This method should only be called when the gridBuilder has done all his work.
	 */
	@Basic
	public Grid getGrid() {
		return grid;
	}
	
	/**
	 * Sets the given grid as the grid of this gridBuilder.
	 * 
	 * @param grid the grid to set
	 */
	protected void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the random generator.
	 * 
	 * @return the random generator.
	 */	
	protected Random getRandom() {
		return random;
	}
	
	/**
	 * Sets the given random as the random of this GridBuilder.
	 * 
	 * @param random the random to set
	 */
	protected void setRandom(Random random) {
		this.random = random;
	}

    
	/**
	 * Place an item on the given coordinate
	 * 
	 * @param 	square
	 * 			The coordinate to place the given item on
	 * @param 	item
	 * 			The item to be placed on the given coordinate
	 */
	protected void placeItem(Square square, Item item) throws IllegalArgumentException {
		if(square.isObstructed())
			return;
			//			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");
		square.getInventory().addItem(item);
	}
	
    /**
     * Returns the constraint for the teleports.
     */
	public GridConstraint getConstraintTeleport() {
		return constraintTeleport;
	}

    /**
     * sets the constraints for the teleport
     * @param constraintTeleport the GridConstraint for teleports.
     */
	public void setConstraintTeleport(GridConstraint constraintTeleport) {
		this.constraintTeleport = constraintTeleport;
	}

    /**
     * Returns the constraint for the lightgrenades
     */
	public GridConstraint getConstraintLightGrenade() {
		return constraintLightGrenade;
	}

    /**
     * Sets the constraints for the lightgrenades
     */
	public void setConstraintLightGrenade(GridConstraint constraintLightGrenade) {
		this.constraintLightGrenade = constraintLightGrenade;
	}

    /**
     * Returns the constraints for the identity discs
     */
	public GridConstraint getConstraintIdentityDisk() {
		return constraintIdentityDisk;
	}

    /**
     * sets the constraints for the identity discs
     */
	public void setConstraintIdentityDisk(GridConstraint constraintIdentityDisk) {
		this.constraintIdentityDisk = constraintIdentityDisk;
	}
	
	/**
	 * Returns the constraint for the forcefieldGenerators.
	 */
	public GridConstraint getConstraintForceFieldGenerator(){
		return this.constraintForceFieldGenerator;
	}
	
	/**
	 * Set the constraint for the forcefieldGenerator
	 * @param constraintForceFieldGenerator the gridConstraint for forcefieldgenerators.
	 */
	public void setConstraintForceFieldGenerator(GridConstraint constraintForceFieldGenerator){
		this.constraintForceFieldGenerator = constraintForceFieldGenerator;
	}

    /**
     * Returns the constraints for the walls
     */
	public GridConstraint getConstraintWall() {
		return constraintWall;
	}

    /**
     * Returns the constraints for the walls
     */
	public void setConstraintWall(GridConstraint constraintWall) {
		this.constraintWall = constraintWall;
	}

    /**
     * Sets the maximum horizontal size of the grid ( in squares)
     * @param hSize the horizontal size of the grid.
     */
	public void setHSize(int hSize){
		if(!isValidHSize(hSize)){
			throw new IllegalArgumentException("The specified hSize is not valid");
		}
		this.hSize = hSize;
	}

    /**
     * sets the maximum vertical size of the grid ( in squares)
     * @param vSize the vertical size of the grid.
     */
	public void setVSize(int vSize){
		if(!isValidVSize(vSize)){
			throw new IllegalArgumentException("The specified vSize is not valid");
		}
		
		this.vSize = vSize;
	}

    /**
     * Checks  whether the vertical size of the grid is valid
     * @param vSize2  the vertical size of the grid.
     */
	public boolean isValidVSize(int vSize2) {
		return (vSize2 >= 0);
	}

    /**
     * Checks whether the horizontal size of the grid is valid
     * @param hSize the horizontal size of the grid.
     */
	public boolean isValidHSize(int hSize){
		return (hSize >= 0);
	}

    /**
     * Returns the maximum horizontal size of the grid ( in squares)
     */
	public int getHSize(){
		return this.hSize;
	}

    /**
     * returns the maximum vertical size of the grid ( in squares)
     */
	public int getVSize(){
		return this.vSize;
	}


    /**
     * Returns the walls which have been constructed.
     */
	protected ArrayList<Wall> getWalls() {
		return this.walls;
	}


	/**
	 * Returns a random index inside an arrayList
	 * @param a	the list of which a random index is selected.
	 */
    protected int getRandomIndex(@SuppressWarnings("rawtypes") ArrayList a){
        return getRandom().nextInt(a.size());
    }

    /**
     * Selects a number of random coordinates ( in respect to the gridConstraint)
     * corresponding to Squares on the grid.
     * @param constraint	the constraint which needs to be satisfied.
     * @return	An arrayList with coordinates which satisfy the GridConstraint.
     */
    protected ArrayList<Coordinate> randomLocations(GridConstraint constraint){
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
        int max = (int) (constraint.getPercentage() * getGrid().getAllSquares().size());
        // Removed excluded squares from candidates
        candidates.removeAll(constraint.getExcluded());
        // Removed obstructed squares from candidates
        ArrayList<Coordinate> toBeRemoved = new ArrayList<Coordinate>();
        for(Coordinate coordinate : candidates){
            if(getGrid().getSquare(coordinate).isObstructed())
                toBeRemoved.add(coordinate);
        }
        candidates.removeAll(toBeRemoved);

        // Add one square from evey list of included coordinates
        for(ArrayList<Coordinate> includes : constraint.getIncluded()){
            Coordinate candidate = includes.get(getRandomIndex(includes));
            while(!candidates.contains(candidate)){
                candidate = includes.get(getRandomIndex(includes));
            }
            coordinates.add(candidate);
            candidates.remove(candidate);
        }

        // Keep adding coordinates from candidates until max is reached
        while(coordinates.size() < max){
            Coordinate candidate = candidates.get(getRandomIndex(candidates));
            coordinates.add(candidate);
            candidates.remove(candidate);
        }

        return coordinates;
    }

    /**
     * Place walls on the given coordinates
     *
     * @param 	walls
     * 			The coordinates where to place the walls
     */
    protected void placeWalls(ArrayList<ArrayList<Coordinate>> walls) {
        if(!getConstraintWall().satisfiesConstraint(flatten(walls), getGrid()))
            throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
        for(ArrayList<Coordinate> sequence : walls){
            this.walls.add(new Wall(getGrid().getSquares(sequence)));
        }
    }

	/**
	 * Utility method that flattens a two dimensional Arraylist. 
	 * 
	 * @return  a flattened version of the list
	 */
    private ArrayList<Coordinate> flatten(ArrayList<ArrayList<Coordinate>> list) {
        ArrayList<Coordinate> result = new ArrayList<Coordinate>();
        for(ArrayList<Coordinate> L : list){
            result.addAll(L);
        }
        return result;
    }

    protected void placeItems(Item item, ArrayList<Coordinate> coordinates){
    	for(Coordinate coordinate: coordinates){
    		placeItem(getGrid().getSquare(coordinate),item.copy());
    	}
    }
    
    /**
     *
     * @param 	coordinates
     * 			The coordinates of the locations to place.
     */
    protected void placeTeleports(ArrayList<Coordinate> coordinates) {
        if(!getConstraintTeleport().satisfiesConstraint(coordinates, getGrid()))
            throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
        ArrayList<Teleport> teleports = new ArrayList<Teleport>();
        ArrayList<Square> destinations = new ArrayList<Square>();
        Teleport teleport;
        for(Coordinate coordinate : coordinates){
            teleport = new Teleport();
            destinations.add(getGrid().getSquare(coordinate));
            placeItem(getGrid().getSquare(coordinate), teleport);
            teleports.add(teleport);
        }
        linkTeleports(teleports, destinations, true);
    }

    /**
     * Links a list of teleports according to the given boolean value.
     *
     * @param 	teleports
     * 			The list of teleports to link.
     * @param 	linkRandomly
     * 			Boolean that hould be true if the linking should happen randomly.
     * 			Otherwise each teleport will be linked to its next neighbor in the list.
     */
    private void linkTeleports(ArrayList<Teleport> teleports, ArrayList<Square> destinations, boolean linkRandomly) {
        if(linkRandomly){
            for(Teleport tele : teleports){
                Square candidateDestination = destinations.get(getRandomIndex(destinations));
                while(candidateDestination.getInventory().hasItem(tele)){
                    candidateDestination = destinations.get(getRandomIndex(destinations));
                }
                tele.setDestination(candidateDestination);
            }
        } else {
            for(int i=0; i<teleports.size(); i++){
                teleports.get(i).setDestination(destinations.get(i%destinations.size()));
            }
        }
    }
    
    protected void setNeighbors(){
    	 for(Square square: getGrid().getAllSquares()){
         	HashMap<Direction, Square> neighbors = getGrid().getNeighbors(square);
         	square.setNeighbors(neighbors);
         }
    }

    /**
     * Suggest a coordinate for the Charged Disk Location
     *
     * @return A coordinate equally far away from each player
     */
    //FIXME: 	Fix for the support of multiple players.
    protected Coordinate getChargedIdentityDiskLocation() {
//        Square player1Square = getGrid().getSquare(getPlayerOneCoordinate());
//        Square player2Square = getGrid().getSquare(getPlayerTwoCoordinate());
//        Map.Entry<Coordinate,Integer> shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(null,Integer.MAX_VALUE);
//        for(Square square : getGrid().getAllSquares()){
//            if(!square.isObstructed()){
//                Coordinate thisCoordinate = getGrid().getCoordinate(square);
//                try{
//                    AStar aStar = new AStar(getGrid());
//                    int player1Length = aStar.shortestPath(player1Square, square).size();
//                    AStar aStar2 = new AStar(getGrid());
//                    int player2Length = aStar2.shortestPath(player2Square, square).size();
//                    if(Math.abs(player2Length - player1Length) <= 2){
//                        int longest = Math.max(player1Length, player2Length);
//                        if(longest < shortest.getValue()){
//                            shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(thisCoordinate, longest);
//                        }
//                    }
//                }catch(Exception e){
//                    System.err.println(e.getMessage());
//                }
//            }
//        }
//        return shortest.getKey();
    	
    	return null;
    }
    
    /**
     * Adds the squares to the grid.
     */
    protected abstract void setSquares();

    /**
     * Builds the grids
     * @throws IllegalStateException
     */
	protected abstract void build() throws IllegalStateException;
	

	public abstract ArrayList<Coordinate> getStartPositions();
	
}
