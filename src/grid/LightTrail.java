/**
 * 
 */
package grid;

import java.util.HashMap;

/**
 * @author jonas
 *
 */
public class LightTrail {
	
	public static final int LENGTH = 2;
	
	HashMap<Integer,Square> trail;
	
	public LightTrail(){
		this.trail = new HashMap<Integer,Square>();
	}
	
	public void addToTrail(Square square, int actionId){
		trail.put(actionId, square);
	}
	
	public void checkTrail(int actionId){
		for(Integer integer: trail.keySet()){
			if(integer < actionId - LENGTH){
				trail.remove(integer);
			}
		}
	}
	
	
	public boolean occupies(Square square){
		return trail.values().contains(square);
	}
	
}
