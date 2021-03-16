import org.junit.Ignore;
import org.junit.Test;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.Value;
import ca.uqac.lif.cornipickle.assertions.BooleanAnd;
import ca.uqac.lif.cornipickle.assertions.Enumerate.NthItem;
import ca.uqac.lif.cornipickle.assertions.Function.ReturnValue;
import ca.uqac.lif.petitpoucet.Elaboration;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.graph.ConcreteTracer;
import ca.uqac.lif.petitpoucet.graph.UnknownNode;

import static ca.uqac.lif.cornipickle.assertions.Logic.And;
import static ca.uqac.lif.cornipickle.assertions.Logic.Or;

import java.util.List;

public class ElaborationTest
{
	@Test
	@Ignore
	public void test1()
	{
		//Function f = And("$x", "$y"); //Or("$y", "$z"));
		Function f = new BooleanAnd();
		Value v = f.evaluate(true, false, false);
		ConcreteTracer t = new ConcreteTracer();
		UnknownNode root = t.getUnknownNode();
		v.query(TraceabilityQuery.CausalityQuery.instance, ReturnValue.instance, root, t);
		Elaboration e_short = root.getShort();
		Elaboration e_long = root.getLong();
	}
	
	@Test
	public void test2()
	{
		Function f = And("$x", "$y", Or("$x", "$z"));
		Value v = f.evaluate(false, false, true);
		ConcreteTracer t = new ConcreteTracer();
		UnknownNode root = t.getUnknownNode();
		v.query(TraceabilityQuery.CausalityQuery.instance, ReturnValue.instance, root, t);
		Elaboration e_short = root.getShort();
		Elaboration e_long = root.getLong();
		e_long.toString();
	}
}
