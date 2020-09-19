package ca.uqac.lif.cornipickle.assertions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import ca.uqac.lif.petitpoucet.Queryable;
import ca.uqac.lif.petitpoucet.graph.ConcreteTraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.graph.ConcreteTracer;
import ca.uqac.lif.petitpoucet.graph.render.TraceabilityNodeDotRenderer;

public class AssertionExplainer implements Explainer
{
	public static TraceabilityNode explain(Value v)
	{
		return explain(v, true);
	}
	
	public static TraceabilityNode explain(Value v, boolean simplify)
	{
		ConcreteTracer tracer = new ConcreteTracer();
		tracer.setSimplify(simplify);
		return tracer.getTree(TraceabilityQuery.CausalityQuery.instance, Function.ReturnValue.instance, v);
	}
	
	public static void explainAndDraw(TestResult v, boolean flatten, String filename)
	{
		for (Verdict verdict : v.getVerdicts())
		{
			if (!verdict.getResult())
			{
				// We explain the first violation
				explainAndDraw(verdict.getValue(), flatten, filename);
				break;
			}
		}
	}

	public static void explainAndDraw(Value v, boolean flatten, String filename)
	{
		TraceabilityNode root = explain(v, true);
		TraceabilityNodeDotRenderer renderer = new TraceabilityNodeDotRenderer();
		renderer.setFlatten(flatten);
		String dot_contents = renderer.render((ConcreteTraceabilityNode) root);
		try (FileWriter writer = new FileWriter(filename);
				BufferedWriter bw = new BufferedWriter(writer))
		{
			bw.write(dot_contents);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public TraceabilityNode explain(Queryable q)
	{
		if (!(q instanceof Value))
		{
			return null;
		}
		ConcreteTracer tracer = new ConcreteTracer();
		return tracer.getTree(TraceabilityQuery.CausalityQuery.instance, Function.ReturnValue.instance, q);
	}
}
