package controller;

public class CheatHandler extends Handler {
	
	public void clearPowerFailures(){
		getGame().clearPowerFailures();
		fireChanges();
	}
	
	public void infiniteActions(){
		getGame().getCurrentPlayer().setRemainingActions(Integer.MAX_VALUE);
	}
	
}
