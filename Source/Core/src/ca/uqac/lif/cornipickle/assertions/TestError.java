package ca.uqac.lif.cornipickle.assertions;

import java.util.List;

public class TestError extends AssertionError
{
	protected TestResult m_result;
	
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;
	
	public TestError(TestResult v)
	{
		super();
		m_result = v;
	}
	
	@Override
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		List<Verdict> verdicts = m_result.getVerdicts();
		int num_failures = 0;
		for (int i = 0; i < verdicts.size(); i++)
		{
			Verdict v = verdicts.get(i);
			if (!v.getResult())
			{
				if (num_failures > 0)
				{
					out.append("\n");
				}
				num_failures++;
				out.append("Failed " + v.getCondition().getName()).append(", as witnessed by ").append(v.getWitness());
			}
		}
		return num_failures + " condition(s) failed\n" + out.toString();
	}

}
