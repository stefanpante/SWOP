package item;


import org.jetbrains.annotations.NotNull;

public class ChargedIdentityDisc extends IdentityDisc {
	
	/**
	 * The maximum travel distance of an identity disc.
	 */
	public static final int MAX_TRAVEL_DISTANCE = Integer.MAX_VALUE;

    /**
     * Basic constructor with zero arguments.
     */
	public ChargedIdentityDisc(){
		super();
		super.setRange(MAX_TRAVEL_DISTANCE);
	}
		
	@NotNull
    @Override
	public String toString() {
		return super.toString() + "ChargedIdentityDisc";
	}
	
	public boolean isSameType(Item o){
		return (o instanceof ChargedIdentityDisc);
	}
	
	@Override
	public void resetRange(){
		super.setRange(MAX_TRAVEL_DISTANCE);
	}
}
