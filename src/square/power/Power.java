package square.power;

import java.util.ArrayList;

import util.Rotation;

/**
 * Power class.
 * 
 * @author vincent
 */
public abstract class Power {

	private int remainingTurns;
	
	private int remainingActions;
	
	private Power child = null;
	
	private Rotation rotation = null;
	
	public Power(int turns, int actions) {
		this.remainingTurns = turns;
		this.remainingActions = actions;
	}
	
	public Power(int turns, int actions, Rotation rotation) {
		this.remainingTurns = turns;
		this.remainingActions = actions;
	}
	
	public void decreaseTurn() throws IllegalStateException {
		this.remainingTurns--;
		
		if(this.remainingTurns <= 0)
			throw new IllegalStateException("PowerFail has no more turns.");
	}
	
	public void decreaseAction() throws IllegalStateException {
		this.remainingActions--;
		
		if(this.remainingActions <= 0)
			throw new IllegalStateException("PowerFail has no more actions.");
	}
	
	protected void reset(int turns, int actions) {
		this.remainingTurns = turns;
		this.remainingActions = actions;
	}
	
	protected void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}
	
	protected void setChild(Power power) {
		this.child = power;
	}
	
	/**
	 * Returns the power itself and its nodes.
	 */
	public ArrayList<Power> getChain() {
		ArrayList<Power> chain = new ArrayList<Power>();
		chain.add(this);
		
		Power lastChild = this;
		
		while(lastChild.hasChild()) {
			chain.add(lastChild.getChild());
			lastChild = lastChild.getChild();
		}
		
		return chain;
	}
	
	public boolean hasChild () {
		return this.child != null; 
	}
	
	public Power getChild() {
		return this.child;
	}
	
	public Rotation getRotation() {
		return this.rotation;
	}
	
	public abstract boolean isFailing();
	
	public abstract void resetCount();
}
