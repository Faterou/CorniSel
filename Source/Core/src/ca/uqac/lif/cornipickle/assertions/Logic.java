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

public class Logic
{
	public static final Function And(Object... operands)
	{
		return new ComposedFunction(new BooleanAnd(operands.length), operands);
	}

	public static final Function Or(Object... operands)
	{
		return new ComposedFunction(new BooleanOr(operands.length), operands);
	}

	public static final Function Implies(Object op1, Object op2)
	{
		return new ComposedFunction(new BooleanOr(), new ComposedFunction(new BooleanNot(), op1), op2);
	}

	public static final Function Not(Object operand)
	{
		return new ComposedFunction(new BooleanNot(), operand);
	}

	public static final Function ForAll(String variable, Function domain, Function phi)
	{
		return new UniversalQuantifier(variable, domain, phi);
	}

	public static final Function ForAll(String variable, Function phi)
	{
		return new UniversalQuantifier(variable, new Enumerate(), phi);
	}

	public static final Function Exists(String variable, Function domain, Function phi)
	{
		return new ExistentialQuantifier(variable, domain, phi);
	}

	public static final Function Exists(String variable, Function phi)
	{
		return new ExistentialQuantifier(variable, new Enumerate(), phi);
	}
}
