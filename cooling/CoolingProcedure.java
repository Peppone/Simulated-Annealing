package cooling;

import sa.SimulatedAnnealing;


public interface CoolingProcedure {
	public double coolDown(SimulatedAnnealing sa);
	public boolean isEnd(SimulatedAnnealing sa);
}
