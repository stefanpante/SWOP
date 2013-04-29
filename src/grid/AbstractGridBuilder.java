package grid;

import item.Item;

import java.util.ArrayList;
import java.util.Random;

import square.Square;
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
	
}
