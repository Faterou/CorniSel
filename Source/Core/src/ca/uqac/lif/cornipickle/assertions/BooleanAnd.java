package ca.uqac.lif.cornipickle.assertions;

import java.util.List;

public class BooleanAnd extends BooleanConnective
{
	public BooleanAnd(int arity)
	{
		super(arity);
	}
	
	public BooleanAnd()
	{
		super(2);
	}
	
	@Override
	protected Value getBooleanValue(List<Value> false_values, List<Value> true_values) 
	{
		if (false_values.isEmpty())
		{
			return new NaryConjunctiveVerdict(true, true_values);
		}
		return new NaryDisjunctiveVerdict(false, false_values);
	}

	@Override
	protected Object get(Object... arguments) 
	{
		return null;
	}
	
	@Override
	public String toString()
	{
		return "And";
	}

}
