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

public class Addition extends AtomicFunction
{
	public Addition(int arity)
	{
		super(arity);
	}
	
	@Override
	protected Object get(Object ... arguments) 
	{
		float sum = 0;
		for (int i = 0; i < m_arity; i++)
		{
			Object o = arguments[i];
			if (!(o instanceof Number))
			{
				throw new InvalidArgumentTypeException(this);
			}
			Number n = (Number) o;
			sum += n.floatValue();
		}
		return sum;
	}
	
	@Override
	public String toString()
	{
		return "Addition";
	}
}
