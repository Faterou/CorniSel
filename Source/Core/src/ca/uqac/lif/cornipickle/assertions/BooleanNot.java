package ca.uqac.lif.cornipickle.assertions;

public class BooleanNot extends AtomicFunction
{
	public BooleanNot()
	{
		super(1);
	}
	
	@Override
	protected Object get(Object... arguments) 
	{
		if (!(arguments[0] instanceof Boolean))
		{
			throw new InvalidArgumentTypeException(this);
		}
		return !((Boolean) arguments[0]);
	}
	
	@Override
	public String toString()
	{
		return "Not";
	}
}
