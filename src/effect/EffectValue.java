package effect;

/**
 * Represents the value a penalty holds.
 * A penalty can cause lost turns and actions.
 * This class represents these values.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EffectValue {

	/**
	 * The number of turns lost 
	 */
	private int turnsLost;
	
	/**
	 * the number of actions lost.
	 */
	private int actionsLost;
	
	
	/**
	 * Constructs a new PenaltyValue with turns lost and actions lost initialized to zero. 
	 */
	public EffectValue(){
		this.setActionsLost(0);
		this.setActionsLost(0);
	}
	
	/**
	 * Constructs a new penaltyValue with the given parameters.
	 * @param turnsLost		the number of turns lost.
	 * @param actionsLost	the number of actions lost.
	 */
	public EffectValue(int turnsLost, int actionsLost) {
		this.setActionsLost(actionsLost);
		this.setTurnsLost(turnsLost);
	}
	
	/**
	 * Sets the number of actions lost to the given parameter.
	 * @param actionsLost	the number of actions lost.
	 * @throws IllegalArgumentException
	 * 			When the given actionsLost is not valid.
	 */
	public void setActionsLost(int actionsLost) throws IllegalArgumentException{
		if(!isValidActionsLost(actionsLost)){
			throw new IllegalArgumentException("The specified number of actions lost is not valid for penaltyValue!");
		}
		
		this.actionsLost = actionsLost;
		
	}
	
	/**
	 * returns whether the given actions lost is valid for penaltyValue.
	 * The actions lost is valid when they are equal to or larger than zero.
	 * 
	 * @param actionsLost	the given number of actions to check.
	 * @return true if the number of actions is valid, otherwise false.
	 */
	public boolean isValidActionsLost(int actionsLost){
		return actionsLost >= 0;
	}
	
	/**
	 * Returns the number of actions lost.
	 * @return
	 */
	public int getActionsLost(){
		return this.actionsLost;
	}
	
	
	
	/**
	 * Sets the number of turns lost to the given parameter
	 * @param turnsLost		the number of turns lost.
	 * @throws IllegalArgumentException
	 * 			When the given turnsLost is not valid.
	 */
	public void setTurnsLost(int turnsLost) throws IllegalArgumentException{
		if(!isValidTurnsLost(turnsLost)){
			throw new IllegalArgumentException("The specified number of turns lost is not valid for penaltyValue!");
		}
		
		this.turnsLost = turnsLost;
	}
	
	/**
	 * Checks whether the given turns lost is valid for penaltyValue.
	 * The turnslost is valid when they are equal to or larger than zero.
	 * 
	 * @param turnsLost		the number of turns lost.
	 * @return true if the number of turns lost is valid, otherwise false.
	 */
	public boolean isValidTurnsLost(int turnsLost){
		return turnsLost >= 0;
	}
	
	/**
	 * Returns the number of turns lost.
	 * @return
	 */
	public int getTurnsLost(){
		return turnsLost;
	}


}
