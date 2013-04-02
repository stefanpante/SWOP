package items;

public abstract class Launchable extends Item {
	
	/**
	 * The range of the launchable
	 */
	private int range;
	
	/**
	 * Creates a new Launchable with a given range
	 * @param Range
	 */
	public Launchable(int range){
		this.setRange(range);
		
	}
	
	/**
	 * Sets the range to the given value, except if it is invalid
	 * @param range		the range to be set.
	 * @throws IllegalArgumentException
	 * 					Thrown when the range is not valid.
	 */
	public void setRange(int range) throws IllegalArgumentException{
		if(!isValidRange(range)){
			throw new IllegalArgumentException("The range is not valid for this launchable");
		}
		
		this.range = range;
	}
	
	/**
	 * Returns whether the range is valid.
	 * valid when larger or equal to 0.
	 * @param range
	 * @return true if the range is valid, otherwise false.
	 */
	public boolean isValidRange(int range){
		return (range >= 0);
	}
	/**
	 * returns the range
	 * @return
	 */
	public int getRange(){
		return range;
	}
}
