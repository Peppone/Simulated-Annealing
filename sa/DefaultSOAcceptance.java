package sa;

public class DefaultSOAcceptance implements AcceptancePolicy {

	@Override
	public double acceptanceProbabilty(SimulatedAnnealing sa) {
		SimulatedAnnealingSO so= (SimulatedAnnealingSO)sa;
		double temp=sa.getTemperature();
		return Math.exp(((Double)(so.getActualSolution().fitness()[0])-(Double)so.getNeighbour().fitness()[0])/temp);
	}

}
