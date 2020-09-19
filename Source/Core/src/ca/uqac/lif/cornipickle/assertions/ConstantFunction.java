package ca.uqac.lif.cornipickle.assertions;

public class ConstantFunction implements Function
{
	protected Value m_value;
	
	public ConstantFunction(Object o)
	{
		super();
		m_value = Value.lift(o);
	}
	
	@Override
	public Value evaluate(Object... arguments)
	{
		return m_value;
	}
	
	@Override
	public int getArity()
	{
		return 0;
	}
	
	@Override
	public ConstantFunction set(String variable, Object value)
	{
		return this;
	}
}
