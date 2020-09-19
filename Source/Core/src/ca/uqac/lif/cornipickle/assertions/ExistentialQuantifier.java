package ca.uqac.lif.cornipickle.assertions;

import java.util.List;

public class ExistentialQuantifier extends Quantifier
{
	public ExistentialQuantifier(String variable, Function domain, Function phi) 
	{
		super(variable, domain, phi);
	}
	
	public ExistentialQuantifier(int index, Function domain, Function phi) 
	{
		super(index, domain, phi);
	}

	@Override
	protected Value getQuantifierValue(List<VerdictValue> false_verdicts, List<VerdictValue> true_verdicts)
	{
		if (!true_verdicts.isEmpty())
		{
			return new QuantifierDisjunctiveVerdict(true, true_verdicts);
		}
		return new QuantifierConjunctiveVerdict(false, false_verdicts);
	}
	
	@Override
	public String toString()
	{
		return "Exists";
	}

	@Override
	public Function set(String variable, Object value)
	{
		return new ExistentialQuantifier(m_variable, m_domain.set(variable, value), m_phi.set(variable, value));
	}
}
