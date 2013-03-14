package square.state;

import square.Square;

/**
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class RegularState extends State{

	public int getPenalty() {
		return 0;
	}

	public boolean hasPenalty() {
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + "Regular";
	}
}
