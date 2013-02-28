/**
 * 
 */
package square;

/**
 * @author jonas
 *
 */
public class SquareBuilder {

	private Square square;
	
	public Square getSquare(){
		return this.square;
	}
	
	public void createSquare(){
		this.square = new Square();
	}

	public void connect(Direction direction, Square square){
		getSquare().setNeigbor(direction, square);
	}
}
