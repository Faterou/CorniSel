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

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.LabeledEdge;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;
import ca.uqac.lif.petitpoucet.graph.ConstantElaboration;

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
		List<Integer> false_positions = new ArrayList<Integer>();
		List<Integer> true_positions = new ArrayList<Integer>();
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
				true_positions.add(i);
			}
			else
			{
				false_values.add(values[i]);
				false_positions.add(i);
			}
		}
		return getBooleanValue(false_values, true_values, false_positions, true_positions);
	}

	protected abstract Value getBooleanValue(List<Value> false_values, List<Value> true_values, List<Integer> false_positions, List<Integer> true_positions);

	protected static abstract class NaryVerdict implements Value
	{
		protected List<Value> m_verdicts;

		protected Boolean m_value;
		
		protected List<Integer> m_positions;

		protected NaryVerdict(boolean value, List<Value> verdicts, List<Integer> positions)
		{
			super();
			m_value = value;
			m_verdicts = verdicts;
			m_positions = positions;
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
		public NaryDisjunctiveVerdict(boolean value, List<Value> verdicts, List<Integer> positions)
		{
			super(value, verdicts, positions);
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getOrNode();
			String val = " is false";
			if (m_value)
			{
				val = " is true";
			}
			ConstantElaboration ce = new ConstantElaboration(BooleanConnective.this.toString() + val);
			n.setShortElaboration(ce);
			for (int i = 0; i < m_verdicts.size(); i++)
			{
				Value v = m_verdicts.get(i);
				Designator new_d = ComposedDesignator.create(d.tail(), InputArgument.get(m_positions.get(i)));
				TraceabilityNode sub_root = factory.getObjectNode(new_d, BooleanConnective.this);
				List<TraceabilityNode> sub_leaves = v.query(q, Function.ReturnValue.instance, sub_root, factory);
				leaves.addAll(sub_leaves);
				n.addChild(sub_root, Quality.EXACT);
			}
			if (n.getChildren().size() == 1)
			{
				LabeledEdge edge = n.getChildren().get(0);
				edge.getNode().setShortElaboration(ce);
				root.addChild(edge);
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
		public NaryConjunctiveVerdict(boolean value, List<Value> verdicts, List<Integer> positions)
		{
			super(value, verdicts, positions);
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getAndNode();
			String val = " is false";
			if (m_value)
			{
				val = " is true";
			}
			ConstantElaboration ce = new ConstantElaboration(BooleanConnective.this.toString() + val);
			n.setShortElaboration(ce);
			for (int i = 0; i < m_verdicts.size(); i++)
			{
				Value v = m_verdicts.get(i);
				Designator new_d = ComposedDesignator.create(d.tail(), InputArgument.get(m_positions.get(i)));
				TraceabilityNode sub_root = factory.getObjectNode(new_d, BooleanConnective.this);
				List<TraceabilityNode> sub_leaves = v.query(q, Function.ReturnValue.instance, sub_root, factory);
				leaves.addAll(sub_leaves);
				n.addChild(sub_root, Quality.EXACT);
			}
			if (n.getChildren().size() == 1)
			{
				LabeledEdge edge = n.getChildren().get(0);
				edge.getNode().setShortElaboration(ce);
				root.addChild(edge);
			}
			else
			{
				root.addChild(n, Quality.EXACT);
			}
			return leaves;
		}
	}
}
