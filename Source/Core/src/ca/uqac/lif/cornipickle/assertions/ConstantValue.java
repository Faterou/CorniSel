package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;

public class ConstantValue implements Value
{
	protected Object m_value;
	
	public ConstantValue(Object o)
	{
		super();
		m_value = o;
	}
	
	@Override
	public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory)
	{
		List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>(1);
		ComposedDesignator new_d = new ComposedDesignator(new ConstantDesignator(), d);
		TraceabilityNode n = factory.getObjectNode(new_d, m_value);
		root.addChild(n, Quality.EXACT);
		return leaves;
	}

	@Override
	public Object get()
	{
		return m_value;
	}
	
	@Override
	public String toString()
	{
		return m_value.toString();
	}
	
	protected class ConstantDesignator implements Designator
	{
		public ConstantDesignator()
		{
			super();
		}
		
		@Override
		public boolean appliesTo(Object o) 
		{
			return false;
		}

		@Override
		public Designator peek()
		{
			return this;
		}

		@Override
		public Designator tail() 
		{
			return null;
		}
		
		@Override
		public String toString()
		{
			return "Value";
		}
	}
}
