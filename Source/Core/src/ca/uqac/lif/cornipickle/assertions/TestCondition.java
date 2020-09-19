package ca.uqac.lif.cornipickle.assertions;

import org.openqa.selenium.WebElement;

public class TestCondition 
{
	protected Function m_function;
	
	protected String m_name;
	
	public TestCondition(String name, Function f)
	{
		super();
		m_function = f;
		m_name = name;
	}
	
	public TestCondition(Function f)
	{
		this("No name", f);
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public Value evaluate(WebElement e)
	{
		return m_function.evaluate(e);
	}
}
