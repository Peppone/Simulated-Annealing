package aspiration;

import sa.SimulatedAnnealing;

public final class DecrementalMOAspiration implements AspirationCriterion 
{
	int limit;
	
	public DecrementalMOAspiration()
	{
		limit=10;
	}

	public DecrementalMOAspiration(int lim)
	{
		limit=lim;
	}
	
	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) 
	{
		return ((double)sa.getSkippedIterationNumber())/((double)limit);
	}
}
