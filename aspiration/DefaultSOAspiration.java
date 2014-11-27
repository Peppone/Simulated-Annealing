package aspiration;

import sa.SimulatedAnnealing;


public class DefaultSOAspiration implements AspirationCriterion {

	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) 
	{
		return 0.5;
	}

}
