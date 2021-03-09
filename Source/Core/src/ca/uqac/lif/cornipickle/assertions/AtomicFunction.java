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
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;

public abstract class AtomicFunction implements Function
{
	protected int m_arity;

	public AtomicFunction(int arity)
	{
		super();
		m_arity = arity;
	}

	@Override
	public int getArity()
	{
		return m_arity;
	}

	@Override
	public AtomicFunction set(String variable, Object value)
	{
		return this;
	}

	@Override
	public Value evaluate(Object... arguments)
	{
		Value[] values = new Value[arguments.length];
		for (int i = 0; i < arguments.length; i++)
		{
			values[i] = Value.lift(arguments[i]);
		}
		return compute(values);
	}

	protected Value compute(Value... values)
	{
		if (checkArity() && values.length != m_arity)
		{
			throw new InvalidArgumentNumberException(this, m_arity, values.length);
		}
		Object[] args = new Object[values.length];
		for (int i = 0; i < values.length; i++)
		{
			args[i] = values[i].get();
		}
		Object ret_obj = get(args);
		if (ret_obj instanceof Value)
		{
			return (Value) ret_obj;
		}
		return new AtomicFunctionReturnValue(ret_obj, values);
	}

	protected boolean checkArity()
	{
		return true;
	}

	protected abstract Object get(Object... arguments);

	public class AtomicFunctionReturnValue implements Value
	{
		protected Value[] m_inputValues;

		protected Object m_outputValue;

		public AtomicFunctionReturnValue(Object output_value, Value... input_values)
		{
			super();
			m_outputValue = output_value;
			m_inputValues = input_values;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getAndNode();
			for (int i = 0; i < m_inputValues.length; i++)
			{
				if (m_inputValues[i] == null)
				{
					continue;
				}
				Designator new_d = new ComposedDesignator(d.tail(), Function.InputArgument.get(i));
				TraceabilityNode sub_root = factory.getObjectNode(new_d, AtomicFunction.this);
				List<TraceabilityNode> sub_leaves = new ArrayList<TraceabilityNode>();
				sub_leaves = m_inputValues[i].query(q, Function.ReturnValue.instance, sub_root, factory);
				leaves.addAll(sub_leaves);
				n.addChild(sub_root, Quality.EXACT);
			}
			//TraceabilityNode f_root = factory.getObjectNode(d, AtomicFunction.this);
			if (n.getChildren().size() == 1)
			{
				root.addChild(n.getChildren().get(0));
			}
			else
			{
				root.addChild(n, Quality.EXACT);
			}
			//root.addChild(f_root, Quality.EXACT);
			return leaves;
		}

		@Override
		public Object get()
		{
			return m_outputValue;
		}

		@Override
		public String toString()
		{
			return m_outputValue.toString();
		}
	}
}
