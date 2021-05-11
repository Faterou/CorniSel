package ca.uqac.lif.cornipickle.assertions;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ca.uqac.lif.cornipickle.assertions.BooleanConnective.NaryDisjunctiveVerdict;
import ca.uqac.lif.cornipickle.assertions.Function.InputArgument;
import ca.uqac.lif.cornipickle.assertions.Function.ReturnValue;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.LabeledEdge;
import ca.uqac.lif.petitpoucet.ObjectNode;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.graph.ConcreteTracer;
import ca.uqac.lif.petitpoucet.graph.UnknownNode;

public class BooleanTest
{
	@Test
	public void testOrSecondTrueArgument()
	{
		BooleanOr op = new BooleanOr();
		Value v = op.evaluate(false, true);
		assertTrue(v instanceof NaryDisjunctiveVerdict);
		assertTrue((Boolean) v.get());
		ConcreteTracer t = new ConcreteTracer();
		UnknownNode root = t.getUnknownNode();
		List<TraceabilityNode> leaves = v.query(null, ReturnValue.instance, root, t);
		List<LabeledEdge> children1 = root.getChildren();
		assertEquals(1, children1.size());
		TraceabilityNode ch1 = children1.get(0).getNode();
		assertTrue(ch1 instanceof ObjectNode);
		Designator d1 = ((ObjectNode) ch1).getDesignatedObject().getDesignator();
		assertTrue(d1 instanceof InputArgument);
		assertEquals(1, ((InputArgument) d1).getIndex());
	}
}
