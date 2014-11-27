package sa;

public class DefaultSOAspiration implements AspirationCriterion {

	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) 
	{
		return 0.5;
	}

}
