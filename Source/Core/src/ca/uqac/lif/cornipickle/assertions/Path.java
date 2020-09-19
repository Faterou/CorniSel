package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import ca.uqac.lif.petitpoucet.Designator;

public class Path implements Designator
{
	protected List<PathElement> m_elements;
	
	public Path()
	{
		super();
		m_elements = new ArrayList<PathElement>();
	}
	
	public Path(Path p)
	{
		super();
		m_elements = new ArrayList<PathElement>(p.m_elements.size());
		m_elements.addAll(p.m_elements);
	}
	
	public Path append(String tag_name, int index)
	{
		Path new_p = new Path(this);
		new_p.m_elements.add(new PathElement(tag_name, index));
		return new_p;
	}
	
	@Override
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < m_elements.size(); i++)
		{
			out.append("/").append(m_elements.get(i));
		}
		return out.toString();
	}
	
	public static class PathElement
	{
		protected String m_tagName;
		
		protected int m_index;
		
		public PathElement(String tag_name, int index)
		{
			super();
			m_tagName = tag_name;
			m_index = index;
		}
		
		@Override
		public String toString()
		{
			return m_tagName + "[" + m_index + "]";
		}
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
