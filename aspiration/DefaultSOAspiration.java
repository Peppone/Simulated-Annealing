package aspiration;

import sa.SimulatedAnnealing;

public class DefaultSOAspiration implements AspirationCriterion
{
	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) 
	{
		double diff=(Double)sa.getActualSolution().fitness()[0]-(Double)sa.getNeighbour().fitness()[0];
		diff= diff>0?-diff:diff;
		return Math.exp( diff/sa.getTemperature());
	}
}
