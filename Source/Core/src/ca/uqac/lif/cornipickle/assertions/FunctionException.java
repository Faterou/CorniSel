package ca.uqac.lif.cornipickle.assertions;

public class FunctionException extends RuntimeException
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The function that is throwing the exception
	 */
	protected transient Function m_function;

	public FunctionException(Function e, String message)
	{
		super(message);
		m_function = e;
	}
	
	public FunctionException(Function e, Throwable t)
	{
		super(t);
		m_function = e;
	}
}
