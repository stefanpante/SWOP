package controller;

public class CheatHandler extends Handler {
	
	public void clearPowerFailures(){
		getGame().getPowerManager().clearPowerFailures();
		fireChanges();
	}
	
	public void infiniteActions(){
		getGame().getCurrentPlayer().setRemainingActions(Integer.MAX_VALUE);
	}
	
}
