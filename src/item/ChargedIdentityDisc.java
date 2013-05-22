package item;


public class ChargedIdentityDisc extends IdentityDisc {
	
	/**
	 * The maximum travel distance of an identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE = Integer.MAX_VALUE;

    /**
     * Basic constructor with zero arguments.
     */
	public ChargedIdentityDisc(){
		super();
		super.setRange(MAX_TRAVEL_DISTANCE);
	}
	
	@Override
	public boolean isCharged() {
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Charged Identity Disc";
	}
	
	public boolean isSameType(Item o){
		return (o instanceof ChargedIdentityDisc);
	}
	
	@Override
	public void resetRange(){
		super.setRange(MAX_TRAVEL_DISTANCE);
	}
}
