package ca.uqac.lif.cornipickle.assertions;

public class GreaterOrEqual extends AtomicFunction
{
	public GreaterOrEqual()
	{
		super(2);
	}

	@Override
	protected Object get(Object... arguments)
	{
		Object o1 = arguments[0];
		Object o2 = arguments[1];
		if (!(o1 instanceof Number) || !(o2 instanceof Number))
		{
			throw new InvalidArgumentTypeException(this);
		}
		Number n1 = (Number) o1;
		Number n2 = (Number) o2;
		return n1.floatValue() >= n2.floatValue();
	}
	
	@Override
	public String toString()
	{
		return "&ge;";
	}
}
