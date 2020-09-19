package ca.uqac.lif.cornipickle.assertions;

public class IsEqualTo extends AtomicFunction 
{
	public static final Function Equals(Object op1, Object op2)
	{
		return new ComposedFunction(new IsEqualTo(), op1, op2);
	}
	
	public IsEqualTo()
	{
		super(2);
	}

	@Override
	protected Object get(Object... arguments)
	{
		Object o1 = arguments[0];
		Object o2 = arguments[1];
		if (o1 == null && o2 == null)
		{
			return true;
		}
		if ((o1 == null && o2 != null) || (o1 != null && o2 == null))
		{
			return false;
		}
		if (isNumber(o1) && isNumber(o2))
		{
			return floatValue(o1) == floatValue(o2);
		}
		if (o1 instanceof String && o2 instanceof String)
		{
			return ((String) o1).compareTo((String) o2) == 0;
		}
		return false;
	}
	
	protected static boolean isNumber(Object o)
	{
		if (o instanceof Number)
		{
			return true;
		}
		if (o instanceof String)
		{
			try
			{
				Float.parseFloat((String) o);
			}
			catch (NumberFormatException e)
			{
				return false;
			}
			return true;
		}
		return false;
	}
	
	protected static float floatValue(Object o)
	{
		if (o instanceof Number)
		{
			return ((Number) o).floatValue();
		}
		if (o instanceof String)
		{
			return Float.parseFloat((String) o);
		}
		return 0f;
	}
	
	@Override
	public String toString()
	{
		return "=";
	}

}
