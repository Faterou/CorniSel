package ca.uqac.lif.cornipickle.driver;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.cornipickle.assertions.Value;

public class Verdict 
{
	protected List<Value> m_values;
	
	public Verdict(List<Value> values)
	{
		super();
		m_values = new ArrayList<Value>(values.size());
		m_values.addAll(values);
	}
	
	public Boolean getResult()
	{
		for (Value v : m_values)
		{
			Object o = v.get();
			if (o instanceof Boolean && !((Boolean) o))
			{
				return false;
			}
		}
		return true;
	}
}
