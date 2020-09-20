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
