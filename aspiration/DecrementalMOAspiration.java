package aspiration;

import sa.SimulatedAnnealing;

public final class DecrementalMOAspiration implements AspirationCriterion 
{
	int limit,counter;
	
	public DecrementalMOAspiration()
	{
		limit=10;
		counter=0;
	}

	public DecrementalMOAspiration(int lim)
	{
		limit=lim;
		counter=0;
	}
	
	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) 
	{
		++counter;
		if(counter%limit==0)
		{
			counter=0;
			return 1;
		}
		else return ((double)counter)/((double)limit);
	}
}
