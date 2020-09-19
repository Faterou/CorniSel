package ca.uqac.lif.cornipickle.assertions;

import java.util.List;

public class BooleanOr extends BooleanConnective
{	
	public BooleanOr(int arity)
	{
		super(arity);
	}
	
	public BooleanOr()
	{
		super(2);
	}
	
	@Override
	protected Value getBooleanValue(List<Value> false_values, List<Value> true_values) 
	{
		if (true_values.isEmpty())
		{
			return new NaryConjunctiveVerdict(false, false_values);
		}
		return new NaryDisjunctiveVerdict(true, true_values);
	}

	@Override
	protected Object get(Object... arguments) 
	{
		return null;
	}
	
	@Override
	public String toString()
	{
		return "Or";
	}

}
