import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.cornipickle.assertions.AtomicFunction.AtomicFunctionReturnValue;
import ca.uqac.lif.cornipickle.assertions.Enumerate;
import ca.uqac.lif.cornipickle.assertions.Enumerate.NthItem;
import ca.uqac.lif.cornipickle.assertions.Value;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.graph.ConcreteTracer;
import ca.uqac.lif.petitpoucet.graph.UnknownNode;

public class EnumerateTest 
{
	@Test
	public void test1()
	{
		Enumerate f = new Enumerate();
		List<String> list = new ArrayList<String>(3);
		list.add("a");
		list.add("b");
		list.add("c");
		Value v = f.evaluate(list);
		assertTrue(v instanceof AtomicFunctionReturnValue);
		ConcreteTracer t = new ConcreteTracer();
		UnknownNode root = t.getUnknownNode();
		List<TraceabilityNode> leaves = v.query(TraceabilityQuery.CausalityQuery.instance, new NthItem(0), root, t);
		assertEquals(1, leaves.size());
	}
}
