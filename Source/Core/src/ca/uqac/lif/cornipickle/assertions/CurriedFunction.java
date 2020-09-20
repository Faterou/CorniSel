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

public class CurriedFunction implements Function
{
	protected Function m_function;
	
	protected Value m_argument;
	
	protected int m_index;
	
	public CurriedFunction(Function f, int index, Value v)
	{
		super();
		m_function = f;
		m_index = index;
		m_argument = v;
	}
	
	@Override
	public CurriedFunction set(String variable, Object value)
	{
		return new CurriedFunction(m_function.set(variable, value), m_index, m_argument);
	}
	
	@Override
	public int getArity()
	{
		return Math.max(0, m_function.getArity() - 1);
	}
	
	@Override
	public Value evaluate(Object... arguments) 
	{
		Value[] new_arguments = new Value[getArity() + 1];
		for (int i = 0; i < m_index; i++)
		{
			new_arguments[i] = Value.lift(arguments[i]);
		}
		new_arguments[m_index] = m_argument;
		for (int i = m_index + 1; i < new_arguments.length; i++)
		{
			new_arguments[i] = Value.lift(arguments[i - 1]);
		}
		Value v = m_function.evaluate((Object[]) new_arguments);
		return new CurriedFunctionReturnValue(v, new_arguments);
	}
	
	@Override
	public String toString()
	{
		return m_function + " at @" + m_index + "=" + m_argument;
	}
	
	public class CurriedFunctionReturnValue implements Value
	{
		protected Value[] m_inputValues;
		
		protected Value m_returnValue;
		
		public CurriedFunctionReturnValue(Value return_value, Value ... arguments)
		{
			super();
			m_inputValues = arguments;
			m_returnValue = return_value;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			if (!(d.peek() instanceof Function.ReturnValue))
			{
				return leaves;
			}
			TraceabilityNode n = factory.getAndNode();
			for (int i = 0; i < m_inputValues.length; i++)
			{
				TraceabilityNode sub_root = null;
				if (i != m_index)
				{
					int index = i;
					if (i > m_index)
					{
						index--;
					}
					sub_root = factory.getObjectNode(Function.InputArgument.get(index), CurriedFunction.this);
				}
				else
				{
					sub_root = factory.getObjectNode(CurriedFunction.CurryArgument.get(i), CurriedFunction.this);
				}
				List<TraceabilityNode> sub_leaves = new ArrayList<TraceabilityNode>();
				sub_leaves = m_inputValues[i].query(q, d, sub_root, factory);
				leaves.addAll(sub_leaves);
				n.addChild(sub_root, Quality.EXACT);
			}
			TraceabilityNode f_root = factory.getObjectNode(d, CurriedFunction.this);
			if (n.getChildren().size() == 1)
			{
				f_root.addChild(n.getChildren().get(0).getNode(), Quality.EXACT);
			}
			else
			{
				f_root.addChild(n, Quality.EXACT);
			}
			root.addChild(f_root, Quality.EXACT);
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
	
	public static class CurryArgument implements Designator
	{
		protected int m_index;

		protected static final transient CurryArgument s_arg0 = new CurryArgument(0);

		protected static final transient CurryArgument s_arg1 = new CurryArgument(1);

		public static CurryArgument get(int index)
		{
			switch (index)
			{
			case 0:
				return s_arg0;
			case 1:
				return s_arg1;
			default:
				return new CurryArgument(index);
			}
		}

		protected CurryArgument(int index)
		{
			super();
			m_index = index;
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
			return "@C"; 
		}
	}
}
