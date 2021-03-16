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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.ConstantElaboration;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;
import ca.uqac.lif.petitpoucet.ObjectNode;

public class ComposedFunction implements Function
{
	protected Function m_operator;

	protected Function[] m_operands;

	protected String m_name = null;

	public ComposedFunction(Function operator, Object... operands)
	{
		super();
		m_operator = operator;
		m_operands = new Function[operands.length];
		for (int i = 0; i < operands.length; i++)
		{
			if (operands[i] instanceof String)
			{
				String op = (String) operands[i];
				if (op.startsWith("@"))
				{
					int index = Integer.parseInt(op.substring(1).trim());
					m_operands[i] = new Argument(index);
					continue;
				}
				if (op.startsWith("$"))
				{
					m_operands[i] = new NamedArgument(op.substring(1).trim());
					continue;
				}
			}
			m_operands[i] = Function.lift(operands[i]);
		}
	}

	public ComposedFunction setName(String name)
	{
		m_name = name;
		return this;
	}

	@Override
	public ComposedFunction set(String variable, Object value)
	{
		ComposedFunction cf = new ComposedFunction(m_operator);
		Function[] operands = new Function[m_operands.length];
		for (int i = 0; i < m_operands.length; i++)
		{
			operands[i] = m_operands[i].set(variable, value);
		}
		cf.m_operands = operands;
		return cf;
	}

	@Override
	public int getArity()
	{
		Set<Integer> args = new HashSet<Integer>();
		getArguments(args);
		return args.size();
	}

	protected void getArguments(Set<Integer> args)
	{
		for (int i = 0; i < m_operands.length; i++)
		{
			Function f = m_operands[i];
			if (f instanceof ComposedFunction)
			{
				((ComposedFunction) f).getArguments(args);
			}
			if (f instanceof Argument)
			{
				args.add(((Argument) f).m_index);
			}
			if (f instanceof NamedArgument)
			{
				args.add(i);
			}
		}
	}

	@Override
	public Value evaluate(Object... arguments)
	{
		Value[] values = new Value[m_operands.length];
		for (int i = 0; i < m_operands.length; i++)
		{
			values[i] = m_operands[i].evaluate(arguments);
		}
		Value v = m_operator.evaluate((Object[]) values);
		return new ComposedFunctionValue(v, values);
	}

	@Override
	public String toString()
	{
		if (m_name != null)
		{
			return m_name;
		}
		return "F(" + m_operator.toString() + ")";
	}

	public class ComposedFunctionValue implements Value
	{
		protected Value[] m_inputValues;

		protected Value m_returnValue;

		public ComposedFunctionValue(Value return_value, Value... values)
		{
			super();
			m_inputValues = values;
			m_returnValue = return_value;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			if (!(d.peek() instanceof Function.ReturnValue))
			{
				return leaves;
			}
			Designator new_d = ComposedDesignator.create(Function.ReturnValue.instance, d.tail());
			TraceabilityNode sub_root = factory.getObjectNode(new_d, ComposedFunction.this);
			sub_root.setShortElaboration(new ConstantElaboration(m_operator + " equals " + m_returnValue));
			List<TraceabilityNode> sub_leaves = new ArrayList<TraceabilityNode>();
			sub_leaves = m_returnValue.query(q, d, sub_root, factory);
			List<TraceabilityNode> new_sub_leaves = new ArrayList<TraceabilityNode>(sub_leaves.size());
			for (TraceabilityNode sub_leaf : sub_leaves)
			{
				if (sub_leaf instanceof ObjectNode)
				{
					ObjectNode o_sl = (ObjectNode) sub_leaf;
					Designator des = o_sl.getDesignatedObject().getDesignator();
					if (des.peek() instanceof Function.InputArgument)
					{
						Function.InputArgument fia = (Function.InputArgument) des.peek();
						int index = fia.getIndex();
						new_sub_leaves.addAll(m_inputValues[index].query(q, new_d, sub_leaf, factory));
						continue;
					}
				}
				new_sub_leaves.add(sub_leaf);
			}
			leaves.addAll(new_sub_leaves);
			root.addChild(sub_root, Quality.EXACT);
			return leaves;
		}

		@Override
		public Object get()
		{
			return m_returnValue.get();
		}

		@Override
		public String toString()
		{
			return m_returnValue.toString();
		}
	}

	public class NamedArgument implements Function
	{
		protected String m_name;

		protected Value m_value;
		
		protected boolean m_isSet;

		public NamedArgument(String name)
		{
			super();
			m_name = name;
			m_value = null;
			m_isSet = false;
		}

		@Override
		public NamedArgument set(String name, Object value)
		{
			NamedArgument na = new NamedArgument(m_name);
			if (m_name.compareTo(name) == 0 || ("$" + m_name).compareTo(name) == 0)
			{
				na.m_value = Value.lift(value);
				na.m_isSet = true;
			}
			return na;
		}

		@Override
		public Value evaluate(Object... arguments)
		{
			if (m_isSet)
			{
				return new NamedArgumentValue(m_name, Value.lift(m_value));
			}
			for (int i = 0; i < ComposedFunction.this.m_operands.length; i++)
			{
				if (m_operands[i] instanceof NamedArgument)
				{
					if (m_name.compareTo(((NamedArgument) m_operands[i]).getName()) == 0)
					{
						return new NamedArgumentValue(m_name, Value.lift(arguments[i]));
					}
				}
			}
			return new NamedArgumentValue(m_name, m_value);
		}

		@Override
		public String toString()
		{
			return "$" + m_name;
		}

		@Override
		public int getArity()
		{
			return 0;
		}

		public String getName()
		{
			return m_name;
		}
	}

	protected class NamedArgumentValue implements Value
	{
		protected Value m_value;

		protected String m_name;

		public NamedArgumentValue(String name, Value v)
		{
			super();
			m_value = v;
			m_name = name;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			Designator new_d = ComposedDesignator.create(d.tail(),
					new FunctionNamedArgument(m_name, m_value));
			TraceabilityNode n = factory.getObjectNode(new_d, m_value);
			n.setShortElaboration(new ConstantElaboration(m_name + " equals " + m_value));
			List<TraceabilityNode> sub_leaves = m_value.query(q, d, n, factory);
			/*for (TraceabilityNode sub_leaf : sub_leaves)
			{
				TraceabilityNode to_add = factory.getObjectNode(new_d, m_value);
				sub_leaf.addChild(to_add, Quality.EXACT);
				leaves.add(to_add);
			}*/
			leaves.addAll(sub_leaves);
			root.addChild(n, Quality.EXACT);
			return leaves;
		}

		@Override
		public Object get()
		{
			return m_value.get();
		}

		@Override
		public String toString()
		{
			return m_value.get().toString();
		}
	}

	public static class FunctionNamedArgument implements Designator
	{
		protected String m_name;

		protected Value m_value;

		protected FunctionNamedArgument(String name, Value v)
		{
			super();
			m_name = name;
			m_value = v;
		}

		@Override
		public boolean appliesTo(Object o)
		{
			return o instanceof Function;
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
			return "$" + m_name + "/" + m_value;
		}
	}

	public class Argument implements Function
	{
		protected int m_index;

		public Argument(int index)
		{
			super();
			m_index = index;
		}

		@Override
		public Argument set(String name, Object value)
		{
			return this;
		}

		@Override
		public Value evaluate(Object... arguments)
		{
			Value v = Value.lift(arguments[m_index]);
			return new ArgumentValue(v, m_index);
		}

		@Override
		public String toString()
		{
			return "@" + m_index;
		}

		@Override
		public int getArity()
		{
			return 0;
		}

		public int getIndex()
		{
			return m_index;
		}
	}

	protected class ArgumentValue implements Value
	{
		protected Value m_value;

		protected int m_index;

		public ArgumentValue(Value v, int index)
		{
			super();
			m_value = v;
			m_index = index;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			Designator new_d = ComposedDesignator.create(Function.InputArgument.get(m_index), d.tail());
			TraceabilityNode n = factory.getObjectNode(new_d, ComposedFunction.this);
			n.setShortElaboration(new ConstantElaboration("Argument " + m_index + " equals " + m_value));
			List<TraceabilityNode> sub_leaves = m_value.query(q, d, n, factory);
			for (TraceabilityNode sub_leaf : sub_leaves)
			{
				TraceabilityNode to_add = factory.getObjectNode(new_d, ComposedFunction.this);
				sub_leaf.addChild(to_add, Quality.EXACT);
				leaves.add(to_add);
			}
			root.addChild(n, Quality.EXACT);
			return leaves;
		}

		@Override
		public Object get()
		{
			return m_value.get();
		}
	}
}
