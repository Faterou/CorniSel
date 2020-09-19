package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;
import ca.uqac.lif.petitpoucet.common.NthOf;

public class Enumerate extends AtomicFunction
{
	public Enumerate()
	{
		super(1);
	}
	
	@Override
	protected Value compute(Value... values) 
	{
		if (!(values[0].get() instanceof List))
		{
			throw new InvalidArgumentTypeException(this);
		}
		List<?> list = (List<?>) values[0].get();
		List<Value> val_list = new ArrayList<Value>(list.size());
		List<Value> out_list = new ArrayList<Value>(list.size());
		for (int i = 0; i < list.size(); i++)
		{
			val_list.add(Value.lift(list.get(i)));
		}
		for (int i = 0; i < list.size(); i++)
		{
			out_list.add(new EnumeratedValue(i, val_list));
		}
		return new AtomicFunctionReturnValue(out_list, values);
	}

	@Override
	protected Object get(Object... arguments) 
	{
		return null;
	}
	
	public static class EnumeratedValue implements Value
	{
		protected int m_index;
		
		protected List<Value> m_inputList;
		
		public EnumeratedValue(int index, List<Value> input_list)
		{
			super();
			m_index = index;
			m_inputList = input_list;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			Designator new_d = new ComposedDesignator(d.tail(), new NthItem(m_index));
			TraceabilityNode n_it = factory.getObjectNode(new_d, m_inputList);
			root.addChild(n_it, Quality.EXACT);
			Value v = m_inputList.get(m_index);
			List<TraceabilityNode> sub_leaves = v.query(q, new_d, n_it, factory);
			leaves.addAll(sub_leaves);
			return leaves;
		}

		@Override
		public Object get() 
		{
			return m_inputList.get(m_index).get();
		}
		
		@Override
		public String toString() 
		{
			return m_inputList.get(m_index).get().toString();
		}
	}
	
	public static class NthItem extends NthOf
	{
		public NthItem(int index)
		{
			super(index);
		}
		
		@Override
		public boolean appliesTo(Object o) 
		{
			return o instanceof List;
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
			return "Element #" + (m_index + 1);
		}
	}
	
	
}
