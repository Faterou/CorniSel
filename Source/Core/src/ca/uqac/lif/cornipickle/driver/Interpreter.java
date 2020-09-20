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
