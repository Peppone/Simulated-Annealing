package aspiration;

import sa.SimulatedAnnealing;


public class DefaultMOAspiration implements AspirationCriterion {

	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) {
		return 0.5;
	}
	

}
