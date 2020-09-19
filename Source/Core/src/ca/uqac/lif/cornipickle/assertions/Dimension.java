package ca.uqac.lif.cornipickle.assertions;

import org.openqa.selenium.WebElement;

public class Dimension 
{
	public static Function Width(Object o)
	{
		return new ComposedFunction(new DimensionWidth(), o);
	}
	
	public static Function Height(Object o)
	{
		return new ComposedFunction(new DimensionHeight(), o);
	}
	
	public static class DimensionWidth extends WebElementFunction
	{
		public DimensionWidth()
		{
			super("width");
		}

		@Override
		protected Object get(Object ... ins)
		{
			return ((WebElement) ins[0]).getSize().width;
		}		
	}
	
	public static class DimensionHeight extends WebElementFunction
	{
		public DimensionHeight()
		{
			super("height");
		}

		@Override
		protected Object get(Object ... ins)
		{
			return ((WebElement) ins[0]).getSize().height;
		}		
	}
}
