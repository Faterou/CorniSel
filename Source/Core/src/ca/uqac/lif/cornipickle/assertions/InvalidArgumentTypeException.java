package ca.uqac.lif.cornipickle.assertions;

public class InvalidArgumentTypeException extends FunctionException
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;

	public InvalidArgumentTypeException(Function e) 
	{
		super(e, "Invalid argument type");
	}
}
