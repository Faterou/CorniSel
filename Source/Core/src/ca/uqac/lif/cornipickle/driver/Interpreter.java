package ca.uqac.lif.cornipickle.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.TestCondition;
import ca.uqac.lif.cornipickle.assertions.Value;
import ca.uqac.lif.cornipickle.assertions.Verdict;
import ca.uqac.lif.cornipickle.assertions.TestResult;

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
	
	public TestResult getVerdict()
	{
		List<Verdict> verdicts = new ArrayList<Verdict>(m_conditions.size());
		for (int i = 0; i < m_conditions.size(); i++)
		{
			verdicts.add(new Verdict(m_returnedValues.get(i), m_conditions.get(i)));
		}
		return new TestResult(verdicts);
	}
}
