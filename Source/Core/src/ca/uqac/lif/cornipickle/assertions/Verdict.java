package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.LabeledEdge;
import ca.uqac.lif.petitpoucet.ObjectNode;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.graph.AndNode;
import ca.uqac.lif.petitpoucet.graph.OrNode;

public class Verdict 
{
	protected TestCondition m_condition;
	
	protected Value m_value;
	
	public Verdict(Value v, TestCondition c)
	{
		super();
		m_value = v;
		m_condition = c;
	}
	
	public TestCondition getCondition()
	{
		return m_condition;
	}
	
	public Value getValue()
	{
		return m_value;
	}
	
	public boolean getResult()
	{
		Object o = m_value.get();
		if (o instanceof Boolean && !((Boolean) o))
		{
			return false;
		}
		return true;
	}
	
	public List<List<Object>> getWitnesses()
	{
		List<List<Object>> list = new ArrayList<List<Object>>();
		return list;
	}
	
	public List<Object> getWitness()
	{
		List<Object> list = new ArrayList<Object>();
		TraceabilityNode root = AssertionExplainer.explain(m_value);
		pick(root, list);
		return list;
	}
	
	protected static void pick(TraceabilityNode n, List<Object> list)
	{
		if (n instanceof AndNode)
		{
			for (LabeledEdge le : n.getChildren())
			{
				pick(le.getNode(), list);
			}
		}
		else if (n instanceof OrNode)
		{
			for (LabeledEdge le : n.getChildren())
			{
				pick(le.getNode(), list);
				break;
			}
		}
		else if (n instanceof ObjectNode)
		{
			if (n.getChildren().isEmpty())
			{
				list.add(((ObjectNode) n).getDesignatedObject());
			}
			else
			{
				for (LabeledEdge le : n.getChildren())
				{
					pick(le.getNode(), list);
				}
			}
		}
	}
}