package ca.uqac.lif.cornipickle.assertions;

public class Addition extends AtomicFunction
{
	public Addition(int arity)
	{
		super(arity);
	}
	
	@Override
	protected Object get(Object ... arguments) 
	{
		float sum = 0;
		for (int i = 0; i < m_arity; i++)
		{
			Object o = arguments[i];
			if (!(o instanceof Number))
			{
				throw new InvalidArgumentTypeException(this);
			}
			Number n = (Number) o;
			sum += n.floatValue();
		}
		return sum;
	}
	
	@Override
	public String toString()
	{
		return "Addition";
	}
}
