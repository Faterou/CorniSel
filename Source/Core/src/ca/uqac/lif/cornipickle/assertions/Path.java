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
