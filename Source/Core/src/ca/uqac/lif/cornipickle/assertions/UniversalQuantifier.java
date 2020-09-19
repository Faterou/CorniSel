package ca.uqac.lif.cornipickle.assertions;

import java.util.List;

public class UniversalQuantifier extends Quantifier
{
	public UniversalQuantifier(String variable, Function domain, Function phi) 
	{
		super(variable, domain, phi);
	}
	
	public UniversalQuantifier(int index, Function domain, Function phi) 
	{
		super(index, domain, phi);
	}

	@Override
	protected Value getQuantifierValue(List<VerdictValue> false_verdicts, List<VerdictValue> true_verdicts)
	{
		if (false_verdicts.isEmpty())
		{
			return new QuantifierConjunctiveVerdict(true, true_verdicts);
		}
		return new QuantifierDisjunctiveVerdict(false, false_verdicts);
	}
	
	@Override
	public String toString()
	{
		return "ForAll";
	}
	
	@Override
	public Function set(String variable, Object value)
	{
		return new UniversalQuantifier(m_variable, m_domain.set(variable, value), m_phi.set(variable, value));
	}
}
