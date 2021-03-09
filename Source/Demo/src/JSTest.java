import static org.junit.Assert.*;

import org.junit.Test;

import ca.uqac.lif.cornipickle.assertions.Addition;
import ca.uqac.lif.cornipickle.assertions.ComposedFunction;
import ca.uqac.lif.cornipickle.assertions.ComposedFunction.ComposedFunctionValue;
import ca.uqac.lif.cornipickle.assertions.Value;

public class JSTest {

	@Test
	public void test1()
	{
		ComposedFunction cf = new ComposedFunction(new Addition(2), "@0", 2);
		assertEquals(1, cf.getArity());
		Value r = cf.evaluate(3);
		assertTrue(r instanceof ComposedFunctionValue);
		Object v = ((ComposedFunctionValue) r).get();
		assertEquals((float) v, 5, 0.01f);
	}
	
	@Test
	public void test2()
	{
		ComposedFunction cf = new ComposedFunction(new Addition(2), "$x", 2);
		assertEquals(1, cf.getArity());
		Value r = cf.evaluate(3);
		assertTrue(r instanceof ComposedFunctionValue);
		Object v = ((ComposedFunctionValue) r).get();
		assertEquals((float) v, 5, 0.01f);
	}

}
