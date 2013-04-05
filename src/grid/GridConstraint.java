/**
 * 
 */
package grid;

import java.util.ArrayList;
import java.util.HashMap;

import util.Coordinate;

/**
 * @author jonas
 *
 */
public class GridConstraint {
	
	private float percentage;
	private ArrayList<Coordinate> excluded;
	private ArrayList<ArrayList<Coordinate>> included;

	/**
	 * Create a new constraint with no constraints
	 */
	public GridConstraint(){
		this(1);
	}
	
	/**
	 * Create a new constraint with a maximum percentage
	 * 
	 * @param 	percentage
	 * 			The percentage constraint of this contraint
	 */
	public GridConstraint(float percentage){
		this(percentage, new ArrayList<Coordinate>(), new ArrayList<ArrayList<Coordinate>>());
	}
	
	/**
	 * Create a new constraint with a maximum percentage and 
	 * a list of excluded coordinates.
	 * 
	 * @param 	percentage
	 * 			The maximum percentage for this constraint
	 * @param 	excluded
	 * 			The list of excluded coordinates
	 */
	public GridConstraint(float percentage, ArrayList<Coordinate> excluded, ArrayList<ArrayList<Coordinate>> included){
		setPercentage(percentage);
		setExcluded(excluded);
		setIncluded(included);
	}
	
	/**
	 * Return whether the given value is a valid percentage for this constraint
	 * 
	 * @param 	percentage
	 * 			The percentage to check
	 * @return	True if and only if the given value is larger or equal to zero
	 * 			and smaller of equal to 1.
	 */
	public static boolean isValidPercentage(float percentage){
		return percentage >= 0 && percentage <= 1; 
	}
	
	private void setPercentage(float percentage){
		if(!isValidPercentage(percentage))
			throw new IllegalArgumentException("The given value is not a percentage.");
		this.percentage = percentage;
	}
	
	private void setExcluded(ArrayList<Coordinate> excluded){
		this.excluded = new ArrayList<Coordinate>(excluded);
	}
	
	private void setIncluded(ArrayList<ArrayList<Coordinate>> included){
		this.included = included;
	}
	
	/**
	 * Return the percentage constraint of this constraint
	 * 
	 * @return	The maximum percentage of this constraint
	 */
	public float getPercentage(){
		return this.percentage;
	}
	
	/**
	 * Return the list of excluded coordinates for this constraint
	 * 
	 * @return	The list of excluded coordinates for this constraint
	 */
	public ArrayList<Coordinate> getExcluded(){
		return new ArrayList<Coordinate>(this.excluded);
	}
	
	/**
	 * Return the list of coordinates that need to be included
	 * 
	 * @return	The list of lists of included coordinates
	 */
	public ArrayList<ArrayList<Coordinate>> getIncluded(){
		return new ArrayList<ArrayList<Coordinate>>(this.included);
	}
	
	
}
