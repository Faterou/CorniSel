import org.junit.Ignore;
import org.junit.Test;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.Value;
import ca.uqac.lif.cornipickle.assertions.BooleanAnd;
import ca.uqac.lif.cornipickle.assertions.Enumerate;
import ca.uqac.lif.cornipickle.assertions.Enumerate.NthItem;
import ca.uqac.lif.cornipickle.assertions.Function.ReturnValue;
import ca.uqac.lif.petitpoucet.Elaboration;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.graph.ConcreteTracer;
import ca.uqac.lif.petitpoucet.graph.UnknownNode;

import static ca.uqac.lif.cornipickle.assertions.Logic.ForAll;
import static ca.uqac.lif.cornipickle.assertions.Logic.And;
import static ca.uqac.lif.cornipickle.assertions.Logic.Or;
import static ca.uqac.lif.cornipickle.assertions.Numbers.IsGreaterThan;

import java.util.ArrayList;
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
	@Ignore
	public void test2()
	{
		//Function f = And("$x", "$y", Or("$x", "$z"));
		Function f = And("$x", "$y");
		Value v = f.evaluate(true, true, true);
		ConcreteTracer t = new ConcreteTracer();
		UnknownNode root = t.getUnknownNode();
		v.query(TraceabilityQuery.CausalityQuery.instance, ReturnValue.instance, root, t);
		Elaboration e_short = root.getShort();
		Elaboration e_long = root.getLong();
		e_long.toString();
	}
	
	@Test
	public void testForAll1()
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(1);
		list.add(8);
		Function f = ForAll("$x", new Enumerate(), IsGreaterThan("$x", 5));
		Value v = f.evaluate(list);
		ConcreteTracer t = new ConcreteTracer();
		UnknownNode root = t.getUnknownNode();
		v.query(TraceabilityQuery.CausalityQuery.instance, ReturnValue.instance, root, t);
		Elaboration e_short = root.getShort();
		Elaboration e_long = root.getLong();
		e_long.toString();
	}
}
