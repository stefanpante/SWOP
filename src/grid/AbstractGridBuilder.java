package grid;

import item.Item;

import java.util.ArrayList;
import java.util.Random;

import square.Square;
import util.Coordinate;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class AbstractGridBuilder {

	private GridConstraint constraintTeleport;
	private GridConstraint constraingtLightGrenade;
	private GridConstraint constraintIdentityDisk;
	private GridConstraint constraintWall;
	
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
	
	
	public abstract Coordinate getPlayerOneStart();
	public abstract Coordinate getPlayerTwoStart();

	public GridConstraint getConstraintTeleport() {
		return constraintTeleport;
	}

	public void setConstraintTeleport(GridConstraint constraintTeleport) {
		this.constraintTeleport = constraintTeleport;
	}

	public GridConstraint getConstraingtLightGrenade() {
		return constraingtLightGrenade;
	}

	public void setConstraingtLightGrenade(GridConstraint constraingtLightGrenade) {
		this.constraingtLightGrenade = constraingtLightGrenade;
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

	
}
