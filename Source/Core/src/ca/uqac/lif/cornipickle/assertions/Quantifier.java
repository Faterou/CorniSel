package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;

public abstract class Quantifier implements Function
{
	protected int m_index;
	
	protected String m_variable;
	
	protected Function m_domain;
	
	protected Function m_phi;
	
	public Quantifier(int index, Function domain, Function phi)
	{
		super();
		m_index = index;
		m_domain = domain;
		m_phi = phi;
	}
	
	public Quantifier(String variable, Function domain, Function phi)
	{
		super();
		m_variable = variable;
		m_domain = domain;
		m_phi = phi;
	}
	
	@Override
	public int getArity()
	{
		return 1;
	}

	@Override
	public Value evaluate(Object... arguments)
	{
		if (arguments.length != 1)
		{
			throw new InvalidArgumentNumberException(this, 1, arguments.length);
		}
		List<VerdictValue> true_verdicts = new ArrayList<VerdictValue>();
		List<VerdictValue> false_verdicts = new ArrayList<VerdictValue>();
		Value v_dom = m_domain.evaluate(arguments);
		Object o_dom = v_dom.get();
		if (!(o_dom instanceof List))
		{
			throw new FunctionException(this, "Domain expression does not return a list");
		}
		List<?> domain = (List<?>) o_dom;
		for (int i = 0; i < domain.size(); i++)
		{
			Value x = Value.lift(domain.get(i));
			Function cf = m_phi.set(m_variable, x);//new CurriedFunction(m_phi, m_index, x);
			Value ret_val = cf.evaluate(arguments);
			Object o_b = ret_val.get();
			if (!(o_b instanceof Boolean))
			{
				throw new InvalidArgumentTypeException(this);
			}
			boolean b = (Boolean) o_b;
			if (b)
			{
				true_verdicts.add(new VerdictValue(x, ret_val));
			}
			else
			{
				false_verdicts.add(new VerdictValue(x, ret_val));
			}
		}
		return getQuantifierValue(false_verdicts, true_verdicts);
	}
	
	protected abstract Value getQuantifierValue(List<VerdictValue> false_verdicts, List<VerdictValue> true_verdicts);
	
	protected static class VerdictValue
	{
		public Value verdict;
		
		public Value value;
		
		public VerdictValue(Value value, Value verdict)
		{
			super();
			this.value = value;
			this.verdict = verdict;
		}
	}
	
	protected static abstract class QuantifierVerdict implements Value
	{
		protected List<VerdictValue> m_verdicts;
		
		protected boolean m_value;
		
		protected QuantifierVerdict(boolean value, List<VerdictValue> verdicts)
		{
			super();
			m_value = value;
			m_verdicts = verdicts;
		}
		
		@Override
		public Boolean get()
		{
			return m_value;
		}
		
		@Override
		public String toString()
		{
			return Boolean.toString(m_value);
		}
	}
	
	protected class QuantifierDisjunctiveVerdict extends QuantifierVerdict
	{
		public QuantifierDisjunctiveVerdict(boolean value, List<VerdictValue> verdicts)
		{
			super(value, verdicts);
		}
		
		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getOrNode();
			for (VerdictValue vv : m_verdicts)
			{
				Value v = vv.verdict;
				Tracer sub_factory = factory.getSubTracer(Quantifier.this);
				List<TraceabilityNode> sub_leaves = v.query(q, Function.ReturnValue.instance, n, sub_factory);
				leaves.addAll(sub_leaves);
			}
			TraceabilityNode tn = factory.getObjectNode(Function.ReturnValue.instance, Quantifier.this);
			if (m_verdicts.size() == 1)
			{
				tn.addChild(n.getChildren().get(0).getNode(), Quality.EXACT);
			}
			else
			{
				tn.addChild(n, Quality.EXACT);
			}
			root.addChild(tn, Quality.EXACT);
			return leaves;
		}
	}
	
	protected class QuantifierConjunctiveVerdict extends QuantifierVerdict
	{
		public QuantifierConjunctiveVerdict(boolean value, List<VerdictValue> verdicts)
		{
			super(value, verdicts);
		}
		
		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			TraceabilityNode n = factory.getAndNode();
			for (VerdictValue vv : m_verdicts)
			{
				Value v = vv.verdict;
				Tracer sub_factory = factory.getSubTracer(Quantifier.this);
				TraceabilityNode and = sub_factory.getAndNode();
				leaves.addAll(v.query(q, Function.ReturnValue.instance, and, sub_factory));
				n.addChild(and, Quality.EXACT);
			}
			TraceabilityNode tn = factory.getObjectNode(Function.ReturnValue.instance, Quantifier.this);
			if (m_verdicts.size() == 1)
			{
				tn.addChild(n.getChildren().get(0).getNode(), Quality.EXACT);
			}
			else
			{
				tn.addChild(n, Quality.EXACT);
			}
			root.addChild(tn, Quality.EXACT);
			return leaves;
		}
	}
	
}
