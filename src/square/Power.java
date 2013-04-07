package square;

public class Power {

	public static final int FAILED_ACTIONS = 4;
	public static final int FAILED_TURNS = 3;
	
	private boolean failed;
	
	private int remainingTurns;
	
	public Power(){
		this.failed = false;
	}
	
	/**
	 * Returns whether the power is failing
	 * 
	 * @return	True if and only if the power is failing
	 */
	public boolean isFailing(){
		return this.failed;
	}
	
	/**
	 * Get the amount of turns the power is failing
	 * 
	 * @return	The amount of turns the poweer is failing
	 */
	public int getRemainingTurns(){
		return this.remainingTurns;
	}
	
	/**
	 * Decrease the turns the power is failing.
	 */
	public void decreaseTurn(){
		int newRemainingActions = getRemainingTurns() - 1;
		if(newRemainingActions <= 0){
			regain();
		}else{
			setRemainingTurns(newRemainingActions);
		}
	}
	
	/**
	 * Set remaining turns to the given amount
	 * 
	 * @param	turns
	 * 			The new value of remaining turns
	 */
	private void setRemainingTurns(int turns){
		this.remainingTurns = turns;
	}
	
	/**
	 * Set the power to be failing
	 */
	public void fail(){
		this.failed = true;
		setRemainingTurns(FAILED_TURNS);
	}
	
	/**
	 * Let the power regain by ending the power failure
	 */
	public void regain(){
		this.failed = false;
		setRemainingTurns(0);
	}
	
}