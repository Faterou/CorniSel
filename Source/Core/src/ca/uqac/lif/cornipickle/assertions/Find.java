package ca.uqac.lif.cornipickle.assertions;

public class Find 
{
	private Find()
	{
		super();
	}
	
	public static Function ByTagName(String tag_name, Object op)
	{
		return new ComposedFunction(new FindByTagName(tag_name), op);
	}
	
	public static Function ByTagName(String tag_name)
	{
		return new FindByTagName(tag_name);
	}
}
