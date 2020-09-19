package ca.uqac.lif.cornipickle.assertions;

public class InvalidArgumentNumberException extends FunctionException
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;

	public InvalidArgumentNumberException(Function e, int expected, int received) 
	{
		super(e, "Invalid number of arguments: expected " + expected + ", received " + received);
	}
}
