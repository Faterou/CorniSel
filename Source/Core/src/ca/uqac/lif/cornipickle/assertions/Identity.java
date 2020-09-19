package ca.uqac.lif.cornipickle.assertions;

public class Identity extends AtomicFunction
{
	public Identity()
	{
		super(1);
	}

	@Override
	protected Object get(Object... arguments)
	{
		return arguments[0];
	}

}
