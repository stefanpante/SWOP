package exceptions;

/**
 * Exception that can be thrown when an illegal square is used
 * possibly as an argument.
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
