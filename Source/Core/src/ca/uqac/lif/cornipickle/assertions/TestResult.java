package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

public class TestResult
{
	protected List<Verdict> m_verdicts;
	
	public TestResult(List<Verdict> verdicts)
	{
		super();
		m_verdicts = new ArrayList<Verdict>(verdicts.size());
		m_verdicts.addAll(verdicts);
	}
	
	public List<Verdict> getVerdicts()
	{
		return m_verdicts;
	}
	
	public Boolean getResult()
	{
		for (Verdict v : m_verdicts)
		{
			if (!v.getResult())
			{
				return false;
			}
		}
		return true;
	}
	
	public void assertVerdict() throws TestError
	{
		if (!getResult())
		{
			throw new TestError(this);
		}
	}
	
}
