package acceptance;

import sa.SimulatedAnnealing;
import sa.SimulatedAnnealingMO;
import solution.Solution;

public class DefaultMOAcceptance implements AcceptancePolicy{

	@Override
	public double acceptanceProbabilty(SimulatedAnnealing sa) {
		SimulatedAnnealingMO mo=(SimulatedAnnealingMO) sa;
		Solution s1 = mo.getNeighbour();
		Solution s2 = mo.getActualSolution();
		int objectiveNumber=s1.fitness().length;
		double distance=0;
		for(int i=0;i<objectiveNumber;++i){
			double difference=((Double)(s1.fitness()[i])-(Double)s2.fitness()[1]);
			distance+=difference*difference;
		
		}
		return Math.exp(-Math.sqrt(distance)/mo.getTemperature());
	}

}
