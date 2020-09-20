/*
    Cornipickle, validation of layout bugs in web applications
    Copyright (C) 2015-2020 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;

public abstract class BooleanConnective extends AtomicFunction 
{
	public BooleanConnective(int arity)
	{
		super(arity);
	}
	
	@Override
	protected Value compute(Value... values) 
	{
		List<Value> false_values = new ArrayList<Value>();
		List<Value> true_values = new ArrayList<Value>();
		for (int i = 0; i < values.length; i++)
		{
			Object o = values[i].get();
			if (!(o instanceof Boolean))
			{
				throw new InvalidArgumentTypeException(this);
			}
			boolean b = (Boolean) o;
			if (b)
			{
				true_values.add(values[i]);
			}
			else
			{
				false_values.add(values[i]);
			}
		}
		return getBooleanValue(false_values, true_values);
	}
	
	protected abstract Value getBooleanValue(List<Value> false_values, List<Value> true_values);
	
	protected static abstract class NaryVerdict implements Value
	{
		protected List<Value> m_verdicts;
		
		protected Boolean m_value;
		
		protected NaryVerdict(boolean value, List<Value> verdicts)
		{
			super();
			m_value = value;
			m_verdicts = verdicts;
		}
		
		@Override
		public Boolean get()
		{
			return m_value;
		}
		
		@Override
		public String toString()
		{
			return m_value.toString();
		}
	}
	
	protected class NaryDisjunctiveVerdict extends NaryVerdict
	{
		public NaryDisjunctiveVerdict(boolean value, List<Value> verdicts)
		{
			super(value, verdicts);
		}
		
		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getOrNode();
			for (Value v : m_verdicts)
			{
				leaves.addAll(v.query(q, Function.ReturnValue.instance, n, factory));
			}
			if (n.getChildren().size() == 1)
			{
				root.addChild(n.getChildren().get(0));
			}
			else
			{
				root.addChild(n, Quality.EXACT);
			}
			return leaves;
		}
	}
	
	protected class NaryConjunctiveVerdict extends NaryVerdict
	{
		public NaryConjunctiveVerdict(boolean value, List<Value> verdicts)
		{
			super(value, verdicts);
		}
		
		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getAndNode();
			for (Value v : m_verdicts)
			{
				leaves.addAll(v.query(q, d, n, factory));
			}
			if (n.getChildren().size() == 1)
			{
				root.addChild(n.getChildren().get(0));
			}
			else
			{
				root.addChild(n, Quality.EXACT);
			}
			return leaves;
		}
	}
}
