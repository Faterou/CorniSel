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
