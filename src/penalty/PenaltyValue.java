package penalty;

public class PenaltyValue {

	/**
	 * The number of turns lost 
	 */
	private int turnsLost;
	
	/**
	 * the number of actions lost.
	 */
	private int actionsLost;
	
	/**
	 * Constructs a new penaltyValue with the given parameters.
	 * @param turnsLost		the number of turns lost.
	 * @param actionsLost	the number of actions lost.
	 */
	public PenaltyValue(int turnsLost, int actionsLost) {
		this.setActionsLost(actionsLost);
		this.setTurnsLost(turnsLost);
	}
	
	/**
	 * Sets the number of actions lost to the given parameter.
	 * @param actionsLost	the number of actions lost.
	 * @throws
	 */
	public void setActionsLost(int actionsLost) throws IllegalArgumentException{
		if(!isValidActionsLost(actionsLost)){
			throw new IllegalArgumentException("The specified number of actions lost is not valid for penaltyValue!");
		}
		
		this.actionsLost = actionsLost;
		
	}
	
	/**
	 * returns whether the given actions lost is valid for penaltyValue.
	 * @param actionsLost	the given number of actions to check.
	 * @return true if the number of actions is valid, otherwise false.
	 */
	public boolean isValidActionsLost(int actionsLost){
		return actionsLost <= 0;
	}
	
	
	
	/**
	 * Sets the number of turns lost to the given parameter
	 * @param turnsLost		the number of turns lost.
	 * @throws
	 */
	public void setTurnsLost(int turnsLost) throws IllegalArgumentException{
		if(!isValidTurnsLost(turnsLost)){
			throw new IllegalArgumentException("The specified number of turns lost is not valid for penaltyValue!");
		}
		
		this.turnsLost = turnsLost;
	}
	
	/**
	 * Checks whether the given turns lost is valid for penaltyValue.
	 * @param turnsLost		the number of turns lost.
	 * @return true if the number of turns lost is valid, otherwise false.
	 */
	public boolean isValidTurnsLost(int turnsLost){
		return turnsLost >= 0;
	}


}
