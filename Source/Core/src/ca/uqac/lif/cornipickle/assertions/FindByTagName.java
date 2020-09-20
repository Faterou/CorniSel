/*
    Cornipickle, validation of layout bugs in web applications
    Copyright (C) 2015-2020 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cornipickle.assertions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;

public class FindByTagName extends Enumerate
{
	protected String m_tagName;

	public FindByTagName(String tag_name)
	{
		super();
		m_tagName = tag_name;
	}

	@Override
	public Value evaluate(Object... arguments)
	{
		if (arguments.length != 1)
		{
			throw new InvalidArgumentNumberException(this, 1, arguments.length);
		}
		Value v = Value.lift(arguments[0]);
		if (!(v.get() instanceof WebElement))
		{
			throw new InvalidArgumentTypeException(this);
		}
		WebElement root = (WebElement) v.get();
		List<Value> val_list = new ArrayList<Value>();
		find(m_tagName, root, root, new Path(), val_list);
		List<Value> out_list = new ArrayList<Value>(val_list.size());
		for (int i = 0; i < val_list.size(); i++)
		{
			out_list.add(new EnumeratedValue(i, val_list));
		}
		return new AtomicFunctionReturnValue(out_list, v);
	}

	@Override
	protected Value compute(Value... values)
	{
		// Not needed
		return null;
	}

	protected static void find(String tag, WebElement root, WebElement e, Path p,
			List<Value> out_list)
	{
		if (e.getTagName().compareTo(tag) == 0)
		{
			out_list.add(new PathValue(p, root, e));
		}
		List<WebElement> children = e.findElements(By.xpath("./*"));
		for (int i = 0; i < children.size(); i++)
		{
			WebElement child = children.get(i);
			if (child instanceof WebElement)
			{
				Path new_p = p.append(e.getTagName(), i);
				find(tag, root, (WebElement) child, new_p, out_list);
			}
		}
	}

	protected static class PathValue implements Value
	{
		protected Path m_path;

		protected Value m_value;

		protected Value m_root;

		public PathValue(Path p, Object root, Object value)
		{
			super();
			m_value = Value.lift(value);
			m_root = Value.lift(root);
			m_path = p;
		}

		@Override
		public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
				Tracer factory)
		{
			List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>();
			Designator new_d = new ComposedDesignator(d.tail(), m_path);
			TraceabilityNode n = factory.getObjectNode(new_d, m_root);
			leaves.addAll(m_root.query(q, new_d, n, factory));
			root.addChild(n, Quality.EXACT);
			return leaves;
		}

		@Override
		public Object get()
		{
			return m_value.get();
		}

		@Override
		public String toString()
		{
			return m_value.toString();
		}
	}
}
