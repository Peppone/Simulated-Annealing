package sa;

public class DefaultSOAcceptance implements AcceptancePolicy {

	@Override
	public double acceptanceProbabilty(SimulatedAnnealing sa) {
		//SimulatedAnnealingSO so= (SimulatedAnnealingSO)sa;
		double temp=sa.getTemperature();
		int iteration=sa.iteration;
		return Math.exp(((Double)(sa.getActualSolution().fitness()[0])-(Double)sa.getNeighbour().fitness()[0])/temp);
	}

}
