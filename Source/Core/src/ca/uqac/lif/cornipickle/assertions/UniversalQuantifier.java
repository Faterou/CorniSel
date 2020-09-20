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
