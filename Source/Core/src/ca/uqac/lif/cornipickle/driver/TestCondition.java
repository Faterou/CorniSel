package ca.uqac.lif.cornipickle.driver;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.Value;

public class TestCondition 
{
	protected Function m_function;
	
	protected String m_name;
	
	public TestCondition(Function f)
	{
		super();
		m_function = f;
		m_name = null;
	}
	
	public Value evaluate(WebElement e)
	{
		return m_function.evaluate(e);
	}
}
