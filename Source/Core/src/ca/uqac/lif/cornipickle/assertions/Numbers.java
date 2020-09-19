package ca.uqac.lif.cornipickle.assertions;

public class Numbers 
{
	public static final Function IsEven()
	{
		return new Even();
	}
	
	public static final Function IsEven(Object op)
	{
		return new ComposedFunction(new Even(), op);
	}
	
	public static final Function IsOdd()
	{
		return new Odd();
	}
	
	public static final Function IsOdd(Object op)
	{
		return new ComposedFunction(new Odd(), op);
	}
	
	public static final Function IsGreaterThan()
	{
		return new GreaterThan();
	}
	
	public static final Function IsGreaterThan(Object op1, Object op2)
	{
		return new ComposedFunction(new GreaterThan(), op1, op2);
	}
	
	public static final Function IsGreaterOrEqual()
	{
		return new GreaterOrEqual();
	}
	
	public static final Function IsGreaterOrEqual(Object op1, Object op2)
	{
		return new ComposedFunction(new GreaterOrEqual(), op1, op2);
	}
}
