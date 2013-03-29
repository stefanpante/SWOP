package Penalty;

import player.Player;

public class PenaltyValue {
	
	/**
	 * Number of turns lost by the penalty
	 */
	private int turnsLost;
	
	/**
	 * Number of actions lost by the penalty
	 */
	private int actionsLost;

	/**
	 * Constructs a new penaltyValue with a given actionsLost and given turnsLost.
	 * @param actionsLost	the number of actions lost.
	 * @param turnsLost		the number of turns lost.
	 */
	public PenaltyValue(int actionsLost, int turnsLost) {
		this.turnsLost = turnsLost; 
		this.actionsLost = actionsLost;
	}

	/**
	 * 
	 * @param turns
	 * @throws IllegalArgumentException
	 */
	public void setTurnsLost(int turns) throws IllegalArgumentException{
		if(!isValidTurnsLost(turns)){
			throw new IllegalArgumentException("The number of turns lost cannot be negative.");
		}
		
		this.turnsLost = turns;
	}
	/**
	 * return the number of turns lost.
	 */
	public int getTurnsLost(){
		return turnsLost;
	}
	
	
	/**
	 * 
	 * @param actions
	 * @throws IllegalArgumentException
	 */
	public void setactionsLost(int actions)  throws IllegalArgumentException{
		if(!isValidActionsLost(actions)){
			throw new IllegalArgumentException("The number of actions lost cannot be negative");
		}
		
		this.actionsLost = actions;
	}
	
	/**
	 * Returns the number of actions lost.
	 */
	public int getActionsLost(){
		return actionsLost;
	}
	
	/**
	 * Checks whether the given number of actions is valid.
	 */
	public boolean isValidActionsLost(int actions){
		return (actions >= 0);
	}
	
	/**
	 * returns if the number of turns lost is valid.
	 * @return
	 */
	public boolean isValidTurnsLost(int turns){
		return (turns >= 0);
	}

}
