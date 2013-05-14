package square.obstacle;


import square.MultiSquare;
import square.Square;

/**
 * Super class where any obstacle covering multiple squares inherits its general properties from.
 * Such as a wall or a light trail
 * A player cannot move through an obstacle.
 * 
 * @invar squaresPointBack()
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public abstract class MultiObstacle extends MultiSquare implements Obstacle {

    /**
     * Create a new MultiObstacle
     */
	public MultiObstacle(){
		super();
	}

    @Override
    public void addSquare(Square square){
        super.addSquare(square);
        square.setObstacle(this);
    }

	/**
	 * Since a multiObstacle is implemented bi-directionally this method should always hold 
	 * which means every square points back to it's proper obstacle.
	 * @return
	 */
	protected boolean squaresPointBack(){
		for(Square sq: getSquares()){
			if(!sq.getObstacle().equals(this))
				return false;
		}
		return true;
	}


	
	
}