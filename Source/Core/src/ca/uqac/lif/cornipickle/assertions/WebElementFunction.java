package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.w3c.dom.Element;

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;

public abstract class WebElementFunction extends AtomicFunction
{
	protected String m_attributeName;
	
	public WebElementFunction(String attribute)
	{
		super(1);
		m_attributeName = attribute;
	}
	
	@Override
	protected Value compute(Value ... values)
	{
		WebElement we = (WebElement) values[0].get();
		Object val = get(we);
		return new ElementAttributeValue(values[0], val);
	}
	
	protected class ElementAttribute implements Designator
	{
		@Override
		public String toString()
		{
			return m_attributeName;
		}

		@Override
		public boolean appliesTo(Object o) 
		{
			return o instanceof Element;
		}

		@Override
		public Designator peek() 
		{
			return this;
		}

		@Override
		public Designator tail() 
		{
			return null;
		}
	}

	protected class ElementAttributeValue implements Value
	{
		protected Value m_value;
		
		protected Value m_input;
		
		public ElementAttributeValue(Object input, Object v)
		{
			super();
			m_input = Value.lift(input);
			m_value = Value.lift(v);
		}
		
		@Override
		public Object get()
		{
			return m_value.get();
		}
		
		@Override
		public String toString()
		{
			return m_value.get().toString();
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root, Tracer factory) 
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			Designator new_d = new ComposedDesignator(new ElementAttribute(), d);
			TraceabilityNode n = factory.getObjectNode(new_d, m_input);
			List<TraceabilityNode> sub_leaves = m_input.query(q, new_d, n, factory);
			leaves.addAll(sub_leaves);
			root.addChild(n, Quality.EXACT);
			return leaves;
		}
	}

}
