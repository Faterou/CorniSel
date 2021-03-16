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

import ca.uqac.lif.petitpoucet.ComposedDesignator;
import ca.uqac.lif.petitpoucet.Designator;
import ca.uqac.lif.petitpoucet.TraceabilityNode;
import ca.uqac.lif.petitpoucet.TraceabilityQuery;
import ca.uqac.lif.petitpoucet.Tracer;
import ca.uqac.lif.petitpoucet.LabeledEdge.Quality;
import ca.uqac.lif.petitpoucet.graph.ConstantElaboration;

public class ConstantValue implements Value
{
	protected Object m_value;

	public ConstantValue(Object o)
	{
		super();
		m_value = o;
	}

	@Override
	public List<TraceabilityNode> query(TraceabilityQuery q, Designator d, TraceabilityNode root,
			Tracer factory)
	{
		List<TraceabilityNode> leaves = new ArrayList<TraceabilityNode>(1);
		Designator new_d = ComposedDesignator.create(new ConstantDesignator(), d);
		TraceabilityNode n = factory.getObjectNode(new_d, m_value);
		n.setShortElaboration(new ConstantElaboration(m_value));
		root.addChild(n, Quality.EXACT);
		leaves.add(n);
		return leaves;
	}

	@Override
	public Object get()
	{
		return m_value;
	}

	@Override
	public String toString()
	{
		return m_value.toString();
	}

	protected class ConstantDesignator implements Designator
	{
		public ConstantDesignator()
		{
			super();
		}

		@Override
		public boolean appliesTo(Object o)
		{
			return false;
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

		@Override
		public String toString()
		{
			return "Value";
		}
	}
}
