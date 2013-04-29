package grid;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;

import square.Square;
import square.obstacle.Wall;
import util.AStar;
import util.Coordinate;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class AbstractGridBuilder {

	
	private int hSize;
	private int vSize;
	/**
	 * the constraints for the grid.
	 */
	private GridConstraint constraintTeleport;
	private GridConstraint constraintLightGrenade;
	private GridConstraint constraintIdentityDisk;
	private GridConstraint constraintWall;
	
	/**
	 * The start position of the first player.
	 */
	private Coordinate startPlayer1;
	
	/**
	 * The start position of the second player.
	 */
	private Coordinate startPlayer2;
	
	/*
	 * The grid which has been built.
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
	 * Adds squares to the grid
	 */
	protected void setSquares() {
		Coordinate coordinate;
		for(int x = 0; x < getGrid().getHSize(); x++){
			for(int y = 0; y < getGrid().getVSize(); y++){
				coordinate = new Coordinate(x, y);
				getGrid().setSquare(coordinate, new Square());
			}
		}		
	}
	
	/**
	 * Check whether the given list of coordinates satisfies the given constraint
	 * 
	 * @param 	coordinates
	 * 			The list of coordinates to be checked
	 * @param 	constraint
	 * 			The constraint to be satisfied
	 * @return	True if and only if the given list satisfies the given constraint
	 */
	protected boolean satisfiesConstraint(ArrayList<Coordinate> coordinates, GridConstraint constraint){
		if(coordinates == null || constraint == null)
			return false;
		float percentage = (float) coordinates.size() / (float) getAmountOfSquares();
		boolean[] includes = new boolean[constraint.getIncluded().size()];
		if(percentage > constraint.getPercentage())
			return false;
		for(Coordinate coordinate : coordinates){
			if(getGrid().getSquare(coordinate).isObstructed())
				return false;
			if(constraint.getExcluded().contains(coordinate))
				return false;
			int i = 0;
			for(ArrayList<Coordinate> include : constraint.getIncluded()){
				if(include.contains(coordinate))
					includes[i] = true;
				i++;
			}
		}
		for(boolean b : includes){
			if(!b)
				return false;
		}
		return true;
	}
	
	protected int getAmountOfSquares(){
		return getGrid().getHSize()*getGrid().getVSize();
	}
	
	

	/**
	 * Place an item on the given coordinate
	 * 
	 * @param 	coordinate
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
	
	protected void setPlayerOneStart(Coordinate coordinate){
		this.startPlayer1 = coordinate;
	}
	
	protected void setPlayerTwoStart(Coordinate coordinate){
		this.startPlayer2 = coordinate;
	}
	
	public Coordinate getPlayerOneStart(){
		return this.startPlayer1;
	}
	
	public Coordinate getPlayerTwoStart(){
		return this.startPlayer2;
	}
	
	public abstract boolean isConsistent();

	public GridConstraint getConstraintTeleport() {
		return constraintTeleport;
	}

	public void setConstraintTeleport(GridConstraint constraintTeleport) {
		this.constraintTeleport = constraintTeleport;
	}

	public GridConstraint getConstraingtLightGrenade() {
		return constraintLightGrenade;
	}

	public void setConstraingtLightGrenade(GridConstraint constraingtLightGrenade) {
		this.constraintLightGrenade = constraingtLightGrenade;
	}

	public GridConstraint getConstraintIdentityDisk() {
		return constraintIdentityDisk;
	}

	public void setConstraintIdentityDisk(GridConstraint constraintIdentityDisk) {
		this.constraintIdentityDisk = constraintIdentityDisk;
	}

	public GridConstraint getConstraintWall() {
		return constraintWall;
	}

	public void setConstraintWall(GridConstraint constraintWall) {
		this.constraintWall = constraintWall;
	}
	
	protected abstract void build() throws IllegalStateException;

	public void setHSize(int hSize){
		if(!isValidHSize(hSize)){
			throw new IllegalArgumentException("The specified hSize is not valid");
		}
		this.hSize = hSize;
	}
	
	public void setVSize(int vSize){
		if(!isValidVsize(vSize)){
			throw new IllegalArgumentException("The specified vSize is not valid");
		}
		
		this.vSize = vSize;
	}
	
	public boolean isValidVsize(int vSize2) {
		return (vSize2 >= 0);
	}
	
	public boolean isValidHSize(int hSize){
		return (hSize >= 0);
	}

	public int getHSize(){
		return this.hSize;
	}
	
	public int getVSize(){
		return this.vSize;
	}

	/**
	 * Suggest a coordinate for the Charged Disk Location
	 * 
	 * @return A coordinate equally far away from each player
	 */
	protected Coordinate chargedIdentityDiskLocation() {
		Square player1Square = getGrid().getSquare(getPlayerOneStart());
		Square player2Square = getGrid().getSquare(getPlayerTwoStart());
		Entry<Coordinate,Integer> shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(null,Integer.MAX_VALUE);
		for(Square square : getGrid().getAllSquares()){
			if(!square.isObstructed()){
				Coordinate thisCoordinate = getGrid().getCoordinate(square);
				try{
					AStar aStar = new AStar(getGrid());
					int player1Length = aStar.shortestPath(player1Square, square).size();
					AStar aStar2 = new AStar(getGrid());
					int player2Length = aStar2.shortestPath(player2Square, square).size();
					if(Math.abs(player2Length - player1Length) <= 2){
						int longest = Math.max(player1Length, player2Length);
						if(longest < shortest.getValue()){
							shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(thisCoordinate, longest);
						}
					}
				}catch(Exception e){
					System.err.println(e.getMessage());
				}
			}
		}
		return shortest.getKey();
	}

	

	protected Coordinate randomChargedIdentityDiskLocation() {
		return null;
	}

	/**
	 * Place light grenades at the the given coordinates, in respect with the given constraint.
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the light grenades
	 * @param 	constraint
	 * 			The constraint for placing light grenades
	 */
	protected void placeLightGrenade(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(Coordinate coordinate : coordinates)
			placeItem(getGrid().getSquare(coordinate), new LightGrenade());
	}

	/**
	 * Place identity disks on the given coordinates, in respect with the given constraint
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the identity disks
	 * @param 	constraint
	 * 			The constraint for placing identity disks
	 */
	protected void placeIdentityDisk(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(Coordinate coordinate : coordinates)
			placeItem(getGrid().getSquare(coordinate), new IdentityDisc());
	}

	protected void placeChargedIdentityDisk(Coordinate coordinate) {
		if(coordinate == null)
			return;
		Square diskSquare = getGrid().getSquare(coordinate);
		placeItem(diskSquare, new ChargedIdentityDisc());
	}

	/**
	 * Place walls on the given coordinates
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the walls
	 * @param 	constraint
	 * 			The constraint for placing walls;
	 */
	protected void placeWalls(ArrayList<ArrayList<Coordinate>> walls, GridConstraint constraint) {
		if(!satisfiesConstraint(flatten(walls), constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(ArrayList<Coordinate> sequence : walls){
			this.walls.add(new Wall(getGrid().getSquares(sequence)));
		}	
	}

	/**
	 * 
	 * @param 	teleports
	 * 			The coordinates of the locations to place.
	 */
	protected void placeTeleports(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		ArrayList<Teleport> teleports = new ArrayList<Teleport>();
		Teleport teleport;
		for(Coordinate coordinate : coordinates){
			teleport = new Teleport();
			placeItem(getGrid().getSquare(coordinate), teleport);
			teleports.add(teleport);
		}
		linkTeleports(teleports, true);		
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
	private void linkTeleports(ArrayList<Teleport> teleports, boolean linkRandomly) {
		if(linkRandomly){
			for(Teleport tele : teleports){
				Teleport candidateDestination = teleports.get(getRandomIndex(teleports));
				while(candidateDestination.equals(tele)){
					candidateDestination = teleports.get(getRandomIndex(teleports));
				}
				tele.setDestination(candidateDestination);
			}
		} else {
			for(int i=0; i<teleports.size(); i++){
				teleports.get(i).setDestination(teleports.get(i%teleports.size()));
			}
		}
	}
	
	@SuppressWarnings("rawtypes") int getRandomIndex(ArrayList a){
		return getRandom().nextInt(a.size());
	}

	/**
	 * Utility method that flattens a two dimensional Arraylist. 
	 * 
	 * @param 	list
	 * @return
	 */
	private ArrayList<Coordinate> flatten(ArrayList<ArrayList<Coordinate>> list) {
		ArrayList<Coordinate> result = new ArrayList<Coordinate>();
		for(ArrayList<Coordinate> L : list){
			result.addAll(L);
		}
		return result;
	}

	protected ArrayList<Wall> getWalls() {
		return this.walls;
	}
	
}
