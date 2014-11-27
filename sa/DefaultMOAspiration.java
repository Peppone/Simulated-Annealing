package sa;

public class DefaultMOAspiration implements AspirationCriterion {

	@Override
	public double aspirationCriterion(SimulatedAnnealing sa) {
		return 0.5;
	}
	

}
