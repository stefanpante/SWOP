package exceptions;

/**
 * Exception that can be thrown when a square is used
 * that violates conditions
 * 
 * @author vincentreniers
 */
public class IllegalSquareException extends Exception{

	private static final long serialVersionUID = 871208981310264015L;
	
	/**
	 * IllegalSquareException.
	 * 
	 * @param error	Error message associated with the exception.
	 */
	public IllegalSquareException(String error) {
		super(error);
	}

}
