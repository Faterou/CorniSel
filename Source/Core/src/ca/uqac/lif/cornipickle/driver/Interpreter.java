package ca.uqac.lif.cornipickle.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.Value;

public class Interpreter
{
	protected List<TestCondition> m_conditions;
	
	protected List<Value> m_returnedValues;
	
	public Interpreter()
	{
		super();
		m_conditions = new ArrayList<TestCondition>();
		m_returnedValues = new ArrayList<Value>();
	}
	
	public void resetHistory()
	{
		
	}
	
	public void clear()
	{
		m_conditions.clear();
	}
	
	public void check(Function property)
	{
		m_conditions.add(new TestCondition(property));
	}
	
	public void check(TestCondition property)
	{
		m_conditions.add(property);
	}
	
	public void evaluateAll(WebElement root)
	{
		m_returnedValues.clear();
		for (TestCondition tc : m_conditions)
		{
			m_returnedValues.add(tc.evaluate(root));
		}
	}
	
	public Verdict getVerdict()
	{
		return new Verdict(m_returnedValues);
	}
}
