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

import ca.uqac.lif.petitpoucet.Designator;

public interface Function
{
	public Value evaluate(Object ... arguments);
	
	public Function set(String variable, Object value);
	
	public int getArity();

	public static Function lift(Object o)
	{
		if (o instanceof Function)
		{
			return (Function) o;
		}
		return new ConstantFunction(Value.lift(o));
	}

	public static class ReturnValue implements Designator
	{
		public static final transient ReturnValue instance = new ReturnValue();

		protected ReturnValue()
		{
			super();
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
			return "!"; 
		}
	}

	public static class InputArgument implements Designator
	{
		protected int m_index;

		protected static final transient InputArgument s_arg0 = new InputArgument(0);

		protected static final transient InputArgument s_arg1 = new InputArgument(1);

		public static InputArgument get(int index)
		{
			switch (index)
			{
			case 0:
				return s_arg0;
			case 1:
				return s_arg1;
			default:
				return new InputArgument(index);
			}
		}

		protected InputArgument(int index)
		{
			super();
			m_index = index;
		}
		
		public int getIndex()
		{
			return m_index;
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
			return "@" + m_index; 
		}
	}
}
