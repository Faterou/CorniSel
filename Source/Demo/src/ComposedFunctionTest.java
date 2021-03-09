import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ca.uqac.lif.cornipickle.assertions.Addition;
import ca.uqac.lif.cornipickle.assertions.ComposedFunction;
import ca.uqac.lif.cornipickle.assertions.ComposedFunction.ComposedFunctionValue;
import ca.uqac.lif.cornipickle.assertions.Value;
import ca.uqac.lif.cornipickle.assertions.Function.ReturnValue;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.graph.ConcreteObjectNode;
import ca.uqac.lif.petitpoucet.graph.ConcreteTracer;

public class ComposedFunctionTest
{
	@Test
	public void test1()
	{
		ComposedFunction cf = new ComposedFunction(new Addition(2), "$x", 2).set("$x", 3); 
		Value r = cf.evaluate(10);
		assertTrue(r instanceof ComposedFunctionValue);
		ConcreteTracer t = new ConcreteTracer();
		TraceabilityNode root = t.getUnknownNode();
		List<TraceabilityNode> leaves = r.query(TraceabilityQuery.CausalityQuery.instance, ReturnValue.instance, root, t);
		assertEquals(2, leaves.size());
		ConcreteObjectNode leaf1 = (ConcreteObjectNode) leaves.get(0);
		ConcreteObjectNode leaf2 = (ConcreteObjectNode) leaves.get(1);
		assertEquals(0, leaf1.getChildren().size());
		assertEquals(0, leaf2.getChildren().size());
	}
}
