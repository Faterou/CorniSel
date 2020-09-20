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

public class Numbers
{
	public static final Function IsEven()
	{
		return new Even();
	}

	public static final Function IsEven(Object op)
	{
		return new ComposedFunction(new Even(), op);
	}

	public static final Function IsOdd()
	{
		return new Odd();
	}

	public static final Function IsOdd(Object op)
	{
		return new ComposedFunction(new Odd(), op);
	}

	public static final Function IsGreaterThan()
	{
		return new GreaterThan();
	}

	public static final Function IsGreaterThan(Object op1, Object op2)
	{
		return new ComposedFunction(new GreaterThan(), op1, op2);
	}

	public static final Function IsGreaterOrEqual()
	{
		return new GreaterOrEqual();
	}

	public static final Function IsGreaterOrEqual(Object op1, Object op2)
	{
		return new ComposedFunction(new GreaterOrEqual(), op1, op2);
	}
}
