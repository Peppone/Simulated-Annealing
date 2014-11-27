package acceptance;

import sa.SimulatedAnnealing;

public interface AcceptancePolicy {
	double acceptanceProbabilty(SimulatedAnnealing sa);
}
