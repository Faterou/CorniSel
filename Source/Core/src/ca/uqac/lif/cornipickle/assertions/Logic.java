package ca.uqac.lif.cornipickle.assertions;

public class Logic
{
	public static final Function And(Object ... operands)
	{
		return new ComposedFunction(new BooleanAnd(operands.length), operands);
	}
	
	public static final Function Or(Object ... operands)
	{
		return new ComposedFunction(new BooleanOr(operands.length), operands);
	}
	
	public static final Function Implies(Object op1, Object op2)
	{
		return new ComposedFunction(new BooleanOr(),
				new ComposedFunction(new BooleanNot(), op1),
				op2);
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
