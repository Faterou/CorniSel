package ca.uqac.lif.cornipickle.assertions;

import ca.uqac.lif.petitpoucet.Queryable;

public interface Value extends Queryable 
{
	public abstract Object get();
	
	public static Value lift(Object o)
	{
		if (o instanceof Value)
		{
			return (Value) o;
		}
		return new ConstantValue(o);
	}
}
