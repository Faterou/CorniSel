package ca.uqac.lif.cornipickle.assertions;

import ca.uqac.lif.petitpoucet.Queryable;
import ca.uqac.lif.petitpoucet.TraceabilityNode;

public interface Explainer
{
	public TraceabilityNode explain(Queryable q);
}
