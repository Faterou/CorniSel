package ca.uqac.lif.cornipickle.assertions;

public class Lists
{
	public static final Function Enumerate()
	{
		return new Enumerate();
	}
	
	public static final Function Enumerate(Object op)
	{
		return new ComposedFunction(new Enumerate(), op);
	}
}
